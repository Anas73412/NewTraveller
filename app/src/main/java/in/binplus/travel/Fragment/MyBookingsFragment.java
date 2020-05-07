package in.binplus.travel.Fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import in.binplus.travel.Adapter.CarBookingHistoryAdapter;
import in.binplus.travel.Adapter.MyBookingAdapter;
import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Model.BookingDetailsModel;
import in.binplus.travel.Model.CarEnquiryHistroyModel;
import in.binplus.travel.R;
import in.binplus.travel.networkconnectivity.NetworkConnection;
import in.binplus.travel.networkconnectivity.NoInternetConnection;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.LoadingBar;
import in.binplus.travel.util.RecyclerTouchListener;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookingsFragment extends Fragment implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView ;
    String user_id;
    RelativeLayout filter;
    Session_management session_management ;
    ArrayList<BookingDetailsModel> bookingList;
    ArrayList<BookingDetailsModel> sharingList;
    ArrayList<CarEnquiryHistroyModel> car_bookinglist;
    RelativeLayout rel_norecord ;
    MyBookingAdapter bookingadapter ;
    CarBookingHistoryAdapter carBookingHistoryAdapter ;
    TabLayout tabLayout ;
   LoadingBar loadingBar ;
    ArrayList<String> stop_list ;

   int mMonth ,mYear,mDay ;
    String b_date="" ,booking_type = "bus";
    LinearLayout linear_car , linear_bus ,linear_share, rel_filter ;
    ImageView img_car ,img_bus ,img_filter ,img_share;


    public MyBookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Bookings");
        View view = inflater.inflate( R.layout.fragment_my_bookings, container, false );

        initViews(view);
//       getMyBookings( user_id );

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener( getActivity(),recyclerView, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                   Bundle args = new Bundle(  );

                   if (booking_type.equals( "bus" )) {
                       args.putString( "booking_id", bookingList.get( position ).getBooking_id() );
                       args.putString( "id", bookingList.get( position ).getId() );
                       args.putString( "vehicle_id", bookingList.get( position ).getVehicle_id() );
                       args.putString( "status", bookingList.get( position ).getStatus() );
                       args.putString( "payment_type", bookingList.get( position ).getPayment_type() );
                       args.putString( "total_money", bookingList.get( position ).getTotal_money() );
                       args.putString( "vehicle_type",booking_type );
                       args.putString( "start_from", bookingList.get( position ).getStart_from() );
                       args.putString( "end_to", bookingList.get( position ).getEnd_to() );
                       args.putString( "booking_date", bookingList.get( position ).getBooking_date() );
                       args.putString( "journey_startdate", bookingList.get( position ).getJourney_startdate() );
                       args.putString( "vehicle_category", bookingList.get( position ).getVehicle_category() );
                       args.putString( "vehicle_no", bookingList.get( position ).getVehicle_no() );
                       args.putString( "user_id", bookingList.get( position ).getUser_id() );
                       args.putString( "b_name", bookingList.get( position ).getB_name() );
                       args.putString( "adhar_no", bookingList.get( position ).getAdhar_no() );
                       args.putString( "mobile", bookingList.get( position ).getMobile() );
                       args.putString( "address", bookingList.get( position ).getAddress() );
                       args.putString( "total_seats", bookingList.get( position ).getTot_seats() );
                       args.putString( "board_location", bookingList.get( position ).getBoard_location() );
                       args.putString( "drop_location", bookingList.get( position ).getDrop_location() );

//                Toast.makeText( getActivity(),"booking_id:"+bookingList.get( position ).getBooking_id(),Toast.LENGTH_LONG ).show();
                       BookingDetails details = new BookingDetails();
                       details.setArguments( args );
                       FragmentManager fragmentManager = getFragmentManager();
                       fragmentManager.beginTransaction().replace( R.id.container_frame, details ).addToBackStack( null ).commit();
                   }
              else if (booking_type.equals( "share" )) {
                    args.putString( "booking_id", sharingList.get( position ).getBooking_id() );
                    args.putString( "id", sharingList.get( position ).getId() );
                    args.putString( "vehicle_id", sharingList.get( position ).getVehicle_id() );
                    args.putString( "status", sharingList.get( position ).getStatus() );
                    args.putString( "payment_type", sharingList.get( position ).getPayment_type() );
                    args.putString( "total_money", sharingList.get( position ).getTotal_money() );
                    args.putString( "vehicle_type",booking_type );
                    args.putString( "start_from", sharingList.get( position ).getStart_from() );
                    args.putString( "end_to",sharingList.get( position ).getEnd_to() );
                    args.putString( "booking_date", sharingList.get( position ).getBooking_date() );
                    args.putString( "journey_startdate", sharingList.get( position ).getJourney_startdate() );
                    args.putString( "vehicle_category",sharingList.get( position ).getVehicle_category() );
                    args.putString( "vehicle_no",sharingList.get( position ).getVehicle_no() );
                    args.putString( "user_id", sharingList.get( position ).getUser_id() );
                    args.putString( "b_name", sharingList.get( position ).getB_name() );
                    args.putString( "adhar_no", sharingList.get( position ).getAdhar_no() );
                    args.putString( "mobile",sharingList.get( position ).getMobile() );
                    args.putString( "email", sharingList.get( position ).getEmail());
                    args.putString( "total_seats", sharingList.get( position ).getTot_seats() );
                    args.putString( "board_location", sharingList.get( position ).getBoard_location() );
                    args.putString( "drop_location", sharingList.get( position ).getDrop_location() );

//                Toast.makeText( getActivity(),"booking_id:"+bookingList.get( position ).getBooking_id(),Toast.LENGTH_LONG ).show();
                    BookingDetails details = new BookingDetails();
                    details.setArguments( args );
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace( R.id.container_frame, details ).addToBackStack( null ).commit();
                }
                   else if (booking_type.equals( "car" ))
                   {
                       args.putString( "enquiry_id",car_bookinglist.get( position ).getEnquiry_id() );
                       args.putString( "vehicle_id",car_bookinglist.get( position ).getVehicle_id() );
                       args.putString( "vehicle_type",booking_type );
//                     args.putString( "total_money",car_bookinglist.get( position ).get() );
                       args.putString( "note",car_bookinglist.get( position ).getNote());
                       args.putString( "start_from",car_bookinglist.get( position ).getFrom_location());
                       args.putString( "end_to",car_bookinglist.get( position ).getTo_locations() );
                       args.putString( "enquiry_date", car_bookinglist.get( position ).getEnquiry_date());
                       args.putString( "journey_date", car_bookinglist.get( position ).getJourney_date() );
                       args.putString( "user_id", car_bookinglist.get( position ).getF_id() );
                       args.putString( "name", car_bookinglist.get( position ).getName() );
                       args.putString( "adhar_no",car_bookinglist.get( position ).getAdhaar_no() );
                       args.putString( "mobile",car_bookinglist.get( position ).getMobile_no() );
                       args.putString( "route",car_bookinglist.get( position ).getStop_list() );
                       args.putString( "status",car_bookinglist.get( position ).getStatus() );


//                Toast.makeText( getActivity(),"booking_id:"+bookingList.get( position ).getVehicle_id(),Toast.LENGTH_LONG ).show();
                       CarBookingDetailsFragments carBookingDetailsFragments = new CarBookingDetailsFragments();
                       carBookingDetailsFragments.setArguments( args );
                       FragmentManager fragmentManager = getFragmentManager();
                       fragmentManager.beginTransaction().replace( R.id.container_frame,carBookingDetailsFragments ).addToBackStack(null).commit();
                   }

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view ;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById( R.id.recycler_booking );
        rel_norecord = view.findViewById( R.id.rel_norecord );
        session_management = new Session_management( getActivity() );
        user_id= session_management.getUserDetails().get( KEY_ID );
        tabLayout = view.findViewById( R.id.tablayout );
        rel_filter = view.findViewById( R.id.rel_filter );
        filter = view.findViewById( R.id.filter );
        linear_bus = view.findViewById( R.id.linear_bus );
        linear_car = view.findViewById( R.id.linear_car );
        linear_share = view.findViewById( R.id.linear_share );
        img_bus = view.findViewById( R.id.img_bus );
        img_car = view.findViewById( R.id.img_car );
        img_share = view.findViewById( R.id.img_share );
        img_filter = view.findViewById( R.id.img_filter );

        recyclerView.setNestedScrollingEnabled( false );

        loadingBar= new LoadingBar( getActivity() );


        linear_bus.setOnClickListener(this);
        linear_car.setOnClickListener(this);
        linear_share.setOnClickListener(this);
        filter.setOnClickListener(this);
        img_bus.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.yelow ) ) );


    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkConnection.connectionChecking(getActivity()))
        {
            getMyBookings(user_id);
        }
        else
        {
            Intent intent = new Intent(getActivity(), NoInternetConnection.class);
            startActivity(intent);
        }

    }



    @Override
    public void onResume() {
        super.onResume();

        if (NetworkConnection.connectionChecking(getActivity()))
        {
            getMyBookings(user_id);
        }
        else
        {
            Intent intent = new Intent(getActivity(), NoInternetConnection.class);
            startActivity(intent);
        }
    }

    public  void getMyBookings(String user_id)
    {
       // Toast.makeText( getActivity(),""+user_id,Toast.LENGTH_LONG ).show();
        loadingBar.show();

        bookingList = new ArrayList<>(  );
        sharingList = new ArrayList<>( );
        car_bookinglist = new ArrayList<>(  );
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "user_id",user_id);



        CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_BOOKING_DETAILS, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean( "responce" );
                            //Toast.makeText( getActivity(),""+response,Toast.LENGTH_LONG ).show();
                            Log.e("res",response.toString());

                            if (status) {
                                loadingBar.dismiss();
                          //    JSONArray data_array = (JSONArray) response.get( "data" );
                                JSONObject data = (JSONObject) response.get( "data" );
                                    JSONArray bus_array = data.getJSONArray( "bus" );
                                    Log.e("bus-data",bus_array.toString());

                                    for (int i = 0; i < bus_array.length(); i++) {
                                        JSONObject object = bus_array.getJSONObject( i );
                                        BookingDetailsModel model = new BookingDetailsModel();
                                        model.setStart_from( object.getString( "start_from" ) );
                                        model.setEnd_to( object.getString( "end_to" ) );
                                        model.setId(object.getString("id"));
                                        model.setBooking_date( object.getString( "booking_date" ) );
                                        model.setBooking_id( object.getString( "booking_id" ) );
                                        model.setStatus( object.getString( "status" ) );
                                        model.setJourney_startdate( object.getString( "journey_startdate" ) );
//                                    model.setJourney_enddate( object.getString( "journey_enddate" ) );
                                        model.setVehicle_category( object.getString( "vehicle_categories" ) );
//                                    model.setVehicle_name( object.getString( "vehicle_name" ) );
                                        model.setVehicle_no( object.getString( "vehicle_no" ) );
                                        model.setVehicle_type( object.getString( "vehicle_type" ) );
                                        model.setPayment_type( object.getString( "payment_type" ) );
                                        model.setTotal_money( object.getString( "total_money" ) );
                                        model.setB_name( object.getString( "b_name" ) );
                                        model.setAdhar_no( object.getString( "adhar_no" ) );
                                        model.setMobile( object.getString( "mobile" ) );
                                        model.setAddress( object.getString( "address" ) );
                                        model.setTot_seats( object.getString( "total_seats" ) );
                                        model.setBoard_location( object.getString( "board_location" ) );
                                        model.setDrop_location( object.getString( "drop_location" ) );
                                        bookingList.add( model );
                                    }

                                    rel_norecord.setVisibility( View.GONE );
                                    recyclerView.setVisibility( View.VISIBLE );
                                    bookingadapter = new MyBookingAdapter( bookingList, getActivity() );
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity() );
                                    recyclerView.setLayoutManager( linearLayoutManager );
                                    recyclerView.setAdapter( bookingadapter );

                                JSONArray share_array = data.getJSONArray( "sharing" );
                                Log.e("share-data",share_array.toString());

                                for (int i = 0; i < share_array.length(); i++) {
                                    JSONObject object = share_array.getJSONObject( i );
                                    BookingDetailsModel model = new BookingDetailsModel();
                                    model.setStart_from( object.getString( "start_from" ) );
                                    model.setEnd_to( object.getString( "end_to" ) );
                                    model.setBooking_date( object.getString( "booking_date" ) );
                                    model.setBooking_id( object.getString( "booking_id" ) );
                                    model.setId(object.getString("id"));
                                    model.setStatus( object.getString( "status" ) );
                                    model.setJourney_startdate( object.getString( "j_date" ) );
//                                    model.setJourney_enddate( object.getString( "journey_enddate" ) );
//                                    model.setVehicle_category( object.getString( "vehicle_categories" ) );
//                                    model.setVehicle_name( object.getString( "vehicle_name" ) );
//                                    model.setVehicle_no( object.getString( "vehicle_no" ) );
//                                    model.setVehicle_type( object.getString( "vehicle_type" ) );
                                    model.setPayment_type( object.getString( "payment_type" ) );
                                    model.setTotal_money( object.getString( "total_money" ) );
                                    model.setB_name( object.getString( "b_name" ) );
                                    model.setAdhar_no( object.getString( "adhaar" ) );
                                    model.setMobile( object.getString( "mobile" ) );
                                    model.setEmail( object.getString( "email" ) );
                                    model.setTot_seats( object.getString( "total_seats" ) );

                                    sharingList.add( model );
                                }
                                    JSONArray car_array = data.getJSONArray( "car" );
                                Log.e("car-data",car_array.toString());
                                    for (int i = 0 ; i <car_array.length();i++)
                                    {
                                        JSONObject object = car_array.getJSONObject( i );
                                        CarEnquiryHistroyModel model = new CarEnquiryHistroyModel(  );
                                        model.setEnquiry_id( object.getString( "enquiry_id" ) );
                                        model.setF_id( object.getString( "franchise_id" ) );
                                        model.setVehicle_id(object.getString( "vehicle_id" ));
                                        model.setFrom_location(object.getString( "from_location" ));
                                        model.setTo_locations(object.getString( "to_location" ));
                                        model.setJourney_date(object.getString( "journey_date" ));
                                        model.setEnquiry_date(object.getString( "enquiry_date" ));
                                        model.setName( object.getString( "name" ) );
                                        model.setMobile_no( object.getString( "mobile_no" ) );
                                        model.setAdhaar_no( object.getString( "adhaar_no" ) );
                                        model.setNote( object.getString( "note" ));
                                        model.setStatus( object.getString( "status" ) );
                                        String rts=object.getString( "route" );
                                        model.setStop_list( rts );
                                        car_bookinglist.add( model );
//                                        Toast.makeText( getActivity(),"vehicle_id:"+object.getString( "vehicle_id" ),Toast.LENGTH_LONG ).show();

                                    }


                                }

//                            }
                            else
                            {
                                loadingBar.dismiss();
                                rel_norecord.setVisibility( View.VISIBLE );
                                recyclerView.setVisibility( View.GONE);
                              //  Toast.makeText( getActivity(),""+response,Toast.LENGTH_LONG ).show();
                            }


                        } catch (JSONException e) {
                            loadingBar.dismiss();
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingBar.dismiss();
                        Toast.makeText( getActivity(),""+error.getMessage(),Toast.LENGTH_LONG ).show();

                    }
                } );

        AppController.getInstance().addToRequestQueue( customVolleyJsonRequest,"Bookings" );

    }

    public String getMonthTwoDigit(String m)
    {
        String s="";
        if(m.length()!=2)
        {
            s="0"+m;
        }
        else {
            s=m;
        }
        return s;
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        if(id == R.id.linear_bus)
        {
            booking_type ="bus";
            img_bus.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.yelow ) ) );
            img_car.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white) ) );
            img_share.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white) ) );
            img_filter.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white) ) );
//              Toast.makeText( getActivity(),"type:" +booking_type,Toast.LENGTH_LONG ).show();
            rel_norecord.setVisibility( View.GONE );
            recyclerView.setVisibility( View.VISIBLE );
            bookingadapter = new MyBookingAdapter( bookingList, getActivity() );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity() );
            recyclerView.setLayoutManager( linearLayoutManager );
            recyclerView.setAdapter( bookingadapter );


        }
        else if(id == R.id.linear_share)
        {
            booking_type ="share";
            img_share.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.yelow ) ) );
            img_car.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white) ) );
            img_bus.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white) ) );
            img_filter.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white) ) );

            rel_norecord.setVisibility( View.GONE );
            recyclerView.setVisibility( View.VISIBLE );
            bookingadapter = new MyBookingAdapter( sharingList, getActivity() );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity() );
            recyclerView.setLayoutManager( linearLayoutManager );
            recyclerView.setAdapter( bookingadapter );


        }
        else if(id == R.id.linear_car)
        {
            booking_type = "car";
            img_car.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.yelow ) ) );
            img_bus.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white ) ) );
            img_share.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white ) ) );
            img_filter.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white ) ) );

            rel_norecord.setVisibility( View.GONE );
            recyclerView.setVisibility( View.VISIBLE );
            carBookingHistoryAdapter = new CarBookingHistoryAdapter( car_bookinglist,getActivity() );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getActivity() );
            recyclerView.setLayoutManager( linearLayoutManager );
            recyclerView.setAdapter( carBookingHistoryAdapter );

        }
        else if(id == R.id.filter)
        {
//            img_filter.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.yelow )) );
//            img_car.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white ) ) );
//            img_bus.setImageTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.white ) ) );
            PopupMenu popup = new PopupMenu(getActivity(),v);
            popup.setOnMenuItemClickListener(this);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.main, popup.getMenu());
            popup.show();

        }
        Toast.makeText( getActivity(),"type:" +booking_type,Toast.LENGTH_LONG ).show();
    }

    private void getFilterList(String booking_type, String b_date ,String filter_type) {


            if (booking_type.equals("bus")) {
                ArrayList<BookingDetailsModel> filterdBusList = new ArrayList<>();
                for (BookingDetailsModel model : bookingList) {
                    String j_dt = model.getJourney_startdate();
                   switch (filter_type){
                       case "date":
                           if (model.getJourney_startdate().equals(b_date))
                           {
                              filterdBusList.add(model);
                           }
                           else {

                             }
                           break;
                       case "pending" :
                           if (model.getStatus().equals("0")) {
                                        filterdBusList.add(model);
                                  }
                             else {

                                 }
                             break;
                       case "confirm" :
                           if (model.getStatus().equals("1")) {
                               filterdBusList.add(model);
                           }
                           else {

                           }
                           break;
                       case "cancel" :
                           if (model.getStatus().equals("2")) {
                               filterdBusList.add(model);
                           }
                           else {

                           }
                   }
                }

                if (filterdBusList.size() <= 0) {
                    rel_norecord.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else {
                    rel_norecord.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    bookingadapter = new MyBookingAdapter(filterdBusList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(bookingadapter);
                    bookingadapter.notifyDataSetChanged();
                }


            }
          else if (booking_type.equals("share")) {
                ArrayList<BookingDetailsModel> filterdBusList = new ArrayList<>();
                for (BookingDetailsModel model : sharingList) {
                    String j_dt = model.getJourney_startdate();
                    switch (filter_type){
                        case "date":
                            if (model.getJourney_startdate().equals(b_date))
                            {
                                filterdBusList.add(model);
                            }
                            else {

                            }
                            break;
                        case "pending" :
                            if (model.getStatus().equals("0")) {
                                filterdBusList.add(model);
                            }
                            else {

                            }
                            break;
                        case "confirm" :
                            if (model.getStatus().equals("1")) {
                                filterdBusList.add(model);
                            }
                            else {

                            }
                            break;
                        case "cancel" :
                            if (model.getStatus().equals("2")) {
                                filterdBusList.add(model);
                            }
                            else {

                            }
                    }
                }

                if (filterdBusList.size() <= 0) {
                    rel_norecord.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else {
                    rel_norecord.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    bookingadapter = new MyBookingAdapter(filterdBusList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(bookingadapter);
                    bookingadapter.notifyDataSetChanged();
                }

            }
            else if (booking_type.equalsIgnoreCase("car")) {
                ArrayList<CarEnquiryHistroyModel> filterdBusList = new ArrayList<>();
                for (CarEnquiryHistroyModel model : car_bookinglist) {
                    String j_dt = model.getJourney_date();
                    switch (filter_type){
                        case "date":
                            if (model.getJourney_date().equals(b_date))
                            {
                                filterdBusList.add(model);
                            }
                            else {

                            }
                            break;
                        case "pending" :
                            if (model.getStatus().equals("0")) {
                                filterdBusList.add(model);
                            }
                            else {

                            }
                            break;
                        case "confirm" :
                            if (model.getStatus().equals("1")) {
                                filterdBusList.add(model);
                            }
                            else {

                            }
                            break;
                        case "cancel" :
                            if (model.getStatus().equals("2")) {
                                filterdBusList.add(model);
                            }
                            else {

                            }
                    }
                }

                //Toast.makeText(getActivity(),""+filterdBusList.size(),Toast.LENGTH_LONG).show();

                if (filterdBusList.size() <= 0) {
                    rel_norecord.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);

                } else {
                    rel_norecord.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    carBookingHistoryAdapter = new CarBookingHistoryAdapter(filterdBusList, getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(carBookingHistoryAdapter);
                    carBookingHistoryAdapter.notifyDataSetChanged();
                }
            }
            else {
                Toast.makeText(getActivity(), "else ", Toast.LENGTH_LONG).show();
            }


    }


    public void dateDialog()
    {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        b_date=year + "-" + getMonthTwoDigit(String.valueOf( monthOfYear + 1 )) + "-" + getMonthTwoDigit(String.valueOf( dayOfMonth));

                        getFilterList(booking_type,b_date,"date");


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filter_date)
        {
            dateDialog();
        }
        else if (id == R.id.filter_complete)
        { getFilterList(booking_type,b_date,"confirm");}
        else if (id== R.id.filter_cancel)
        { getFilterList(booking_type,b_date,"cancel");}
        else if (id == R.id.filter_pending)
        { getFilterList(booking_type,b_date,"pending");
        }

        return false;
    }


}
