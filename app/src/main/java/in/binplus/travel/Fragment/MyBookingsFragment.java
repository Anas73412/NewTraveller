package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.Adapter.MyBookingAdapter;
import in.binplus.travel.AppController;
import in.binplus.travel.BookingActivity;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Model.BookingDetailsModel;
import in.binplus.travel.Model.MyBookingModel;
import in.binplus.travel.R;
import in.binplus.travel.StationListActivity;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.RecyclerTouchListener;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookingsFragment extends Fragment {
    RecyclerView recyclerView ;
    String user_id;
    Session_management session_management ;
    ArrayList<BookingDetailsModel> bookingList;
    ArrayList<BookingDetailsModel> todays_bookingList;
    RelativeLayout rel_norecord ;
    MyBookingAdapter bookingadapter ;
    TabLayout tabLayout ;
    ProgressDialog loadingBar ;

    public MyBookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Bookings");
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_bookings, container, false );
        recyclerView = view.findViewById( R.id.recycler_booking );
        rel_norecord = view.findViewById( R.id.rel_norecord );
        session_management = new Session_management( getActivity() );
        user_id= session_management.getUserDetails().get( KEY_ID );
        tabLayout = view.findViewById( R.id.tablayout );


        bookingList = new ArrayList<>(  );
        todays_bookingList = new ArrayList<>(  );
        loadingBar= new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );




//        getTodaysBookings( user_id );

      getMyBookings( user_id );
//        tabLayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//               String lable = (String) tab.getText();
//                Toast.makeText( getActivity(), ""+lable, Toast.LENGTH_SHORT ).show();
//
//                if (lable.equals( "todays" ))
//                {
//                    if (bookingList.isEmpty())
//                    {
//                        rel_norecord.setVisibility( View.VISIBLE );
//                        recyclerView.setVisibility( View.GONE );
//                    }
//                    getTodaysBookings( user_id );
//                }
//                else
//                {
//                    if (todays_bookingList.isEmpty())
//                    {
//                        rel_norecord.setVisibility( View.VISIBLE );
//                        recyclerView.setVisibility( View.GONE );
//                    }
//                   getMyBookings( user_id );
//                }
//            }

//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        } );

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener( getActivity(),recyclerView, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                   Bundle args = new Bundle(  );
                   args.putString( "booking_id",bookingList.get( position ).getBooking_id());
                   args.putString( "vehicle_id",bookingList.get( position ).getVehicle_id() );
                   args.putString( "status",bookingList.get( position ).getStatus() );
                   args.putString( "payment_type",bookingList.get( position ).getPayment_type() );
                   args.putString( "total_money",bookingList.get( position ).getTotal_money() );
                   args.putString( "vehicle_type",bookingList.get( position ).getVehicle_type() );
                   args.putString( "start_from",bookingList.get( position ).getStart_from() );
                   args.putString( "end_to",bookingList.get( position ).getEnd_to() );
                   args.putString( "booking_date",bookingList.get( position ).getBooking_date() );
                   args.putString( "journey_startdate",bookingList.get( position ).getJourney_startdate() );
                   args.putString( "journey_enddate",bookingList.get( position ).getJourney_enddate() );
                   args.putString( "vehicle_category" ,bookingList.get( position ).getVehicle_category() );
                   args.putString( "vehicle_no" ,bookingList.get( position ).getVehicle_no() );
//                   args.putString( "vehicle_name",bookingList.get( position).getVehicle_name() );
                   args.putString( "user_id",bookingList.get( position ).getUser_id() );
                   args.putString( "name",bookingList.get( position ).getB_name() );
                   args.putString( "adhar_no",bookingList.get( position ).getAdhar_no() );
                   args.putString( "mobile",bookingList.get( position ).getMobile() );
                   args.putString( "address",bookingList.get( position ).getAddress() );
                   args.putString( "total_seats",bookingList.get( position ).getTot_seats() );

//                Toast.makeText( getActivity(),"booking_id:"+bookingList.get( position ).getBooking_id(),Toast.LENGTH_LONG ).show();

                   BookingDetails details = new BookingDetails();
                details.setArguments( args );
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,details ).addToBackStack( null ).commit();

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view ;
    }

    @Override
    public void onStart() {
        super.onStart();
//       getTodaysBookings( user_id );

    }

    public  void getMyBookings(String user_id)
    {
       // Toast.makeText( getActivity(),""+user_id,Toast.LENGTH_LONG ).show();
        loadingBar.show();
        bookingList.clear();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "user_id",user_id);



        CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_BOOKING_DETAILS, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean( "responce" );
                          Toast.makeText( getActivity(),""+response,Toast.LENGTH_LONG ).show();


                            if (status) {
                                loadingBar.dismiss();
                                JSONArray array = response.getJSONArray( "data" );

                                for (int i = 0; i < array.length(); i++)
                                {
                                    JSONObject object=array.getJSONObject(i);
                                    BookingDetailsModel model = new BookingDetailsModel();
                                    model.setStart_from( object.getString( "start_from" ) );
                                    model.setEnd_to( object.getString( "end_to" ) );
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
                                    bookingList.add(model );
                                }
                                rel_norecord.setVisibility( View.GONE );
                                recyclerView.setVisibility( View.VISIBLE );
                                bookingadapter = new MyBookingAdapter(bookingList,getActivity());
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager( linearLayoutManager );
                                recyclerView.setAdapter( bookingadapter );

                            }
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


}
