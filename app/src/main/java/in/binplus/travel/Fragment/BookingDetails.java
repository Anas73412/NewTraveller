package in.binplus.travel.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.Adapter.PassengerListAdapter;
import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.Model.BookingDetailsModel;
import in.binplus.travel.Model.MyBookingModel;
import in.binplus.travel.Model.PassengerDetailsModel;
import in.binplus.travel.R;
import in.binplus.travel.util.ConnectivityReceiver;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingDetails extends Fragment {

    RecyclerView recycler_passenger ;
    TextView text_to ,txt_from,txt_date,txt_total,txt_busname,txt_busno ,passengers ,txt_status
            ,cancel_booking ,txt_tot_seats ,txt_pass_name ,txt_pass_mobile;
    Dialog dialog ;
    TextView btn_no ,btn_yes ;
    EditText et_remark ;
    String user_id ;
    Session_management session_management ;
    ImageView img_up , img_down ;
    ArrayList<AddPassengerToSeatModel>passenger_list ;
    PassengerListAdapter passengerListAdapter ;
    Module module ;
    ProgressDialog loadingBar ;
    RelativeLayout rel_passenger ;
    LinearLayout rel_status ;

    String booking_id ,vehicle_id ,status ,payment ,total_money ,vehicle_type,start_from ,end_to ,booking_date,start_date,end_date,
            vehicle_category,vehicle_name,vehicle_number,name ,adhar_id,address ,mobile ,total_seats ,drop_location ,board_location;



    public BookingDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_booking_details, container, false );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Booking Details");
        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cancel_order_layout);
        dialog.setCanceledOnTouchOutside(false);
        module = new Module( getActivity() );

        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage("loading" );

        txt_from = view.findViewById( R.id.txt_from );
        text_to=view.findViewById( R.id.txt_to );
        txt_date=view.findViewById( R.id.txt_date );
        txt_total=view.findViewById( R.id.txt_price );
        txt_busname=view.findViewById( R.id.vehicle_name);
        txt_busno = view.findViewById( R.id.vehicle_no );
        cancel_booking = view.findViewById( R.id.cancel_booking );
        passengers = view.findViewById( R.id.passenger_detail );
        txt_tot_seats = view.findViewById( R.id.txt_tot_seats );
        txt_pass_name = view.findViewById( R.id.booking_name );
        txt_pass_mobile =view.findViewById( R.id.txt_mobile );
        txt_status = view.findViewById( R.id.txt_status );
        recycler_passenger = view.findViewById( R.id.recycler_pass_details );
        rel_status= view.findViewById( R.id.rel_status );
        session_management = new Session_management( getActivity() );
        user_id = session_management.getUserDetails().get( KEY_ID );
        img_up = view.findViewById( R.id.up );
        img_down = view.findViewById( R.id.down );
        rel_passenger =view.findViewById(R.id.rel_passenger_detail);


        booking_id = getArguments().getString( "booking_id" );
        booking_date=getArguments().getString( "booking_date" );
        start_from =getArguments().getString( "start_from" );
        end_to =getArguments().getString( "end_to" );
        start_date=getArguments().getString( "journey_startdate" );
        end_date = getArguments().getString( "journey_enddate" );
        total_money=getArguments().getString( "total_money" );
        vehicle_id =getArguments().getString( "vehicles_id" );
        payment = getArguments().getString( "payment_type" );
        name = getArguments().getString("b_name");
        address = getArguments().getString( "address" );
        adhar_id = getArguments().getString( "adhar_no" );
        mobile = getArguments().getString( "mobile" );
        vehicle_number = getArguments().getString("vehicle_no");
        total_seats = getArguments().getString( "total_seats" );
        drop_location=getArguments().getString( "drop_location");
        board_location = getArguments().getString( "board_location" );
       status = getArguments().getString( "status" );
        int sts = Integer.parseInt( status );
        if (sts==0)
        {
            txt_status.setText( "Pending" );
            txt_status.setTextColor( Color.WHITE );
            rel_status.setBackgroundTintList( ColorStateList.valueOf(getActivity().getResources().getColor( R.color.green )) );

        }
        else if (sts ==1)
        {
            txt_status.setText( "Confirmed" );
            txt_status.setTextColor( Color.WHITE);

            rel_status.setBackgroundTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.yelow )) );
        }
        else if (sts==2)
        {
            txt_status.setText( "Cancelled" );
            txt_status.setTextColor( Color.WHITE );
            cancel_booking.setVisibility( View.GONE );
            rel_status.setBackgroundTintList( ColorStateList.valueOf( getActivity().getResources().getColor( R.color.color_cancel ) ) );
        }



        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(start_from +"-"+end_to);
    //    Toast.makeText( getActivity(),"booking_id" +booking_id,Toast.LENGTH_LONG ).show();

        passenger_list = new ArrayList<>(  );


         getBookingDetails();

        txt_from.setText( "Booking Id : #"+booking_id);
        text_to.setText( end_to );
        txt_date.setText( "Date : "+booking_date );
        txt_total.setText(getActivity().getResources().getString(R.string.currency)+""+ total_money );
        txt_busname.setText("Board At:"+board_location );
        txt_busno.setText( "Drop At:" +drop_location );
        txt_pass_mobile.setText( mobile );
        txt_pass_name.setText( name );
        txt_tot_seats.setText( total_seats );



//        rel_passenger.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (img_down.getVisibility()== View.VISIBLE) {
//                    recycler_passenger.setVisibility( View.VISIBLE );
//                    img_up.setVisibility( View.VISIBLE );
//                    img_down.setVisibility( View.GONE );
//                }
//                else if (img_up.getVisibility()==View.VISIBLE)
//                {
//                    recycler_passenger.setVisibility( View.GONE );
//                    img_up.setVisibility( View.GONE );
//                    img_down.setVisibility( View.VISIBLE);
//                }
//
//
//            }
//        } );

        cancel_booking.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                        btn_no=dialog.findViewById(R.id.btn_no);
                        btn_yes=dialog.findViewById(R.id.btn_yes);
                        et_remark=(EditText) dialog.findViewById(R.id.et_remark);
                        dialog.show();

                        btn_yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {




                                String remark=et_remark.getText().toString();
                                if(remark.isEmpty())
                                {
                                    et_remark.setError("Please provide a reason");
                                    et_remark.requestFocus();
                                }
                                else if(remark.length()<10)
                                {
                                    et_remark.setError("Too short");
                                    et_remark.requestFocus();

                                }
                                else
                                {
                                    if (ConnectivityReceiver.isConnected()) {
                                        cancelRequest(booking_id, user_id,remark);
                                        dialog.dismiss();

                                    }

                                }

                            }
                        });

                        btn_no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                            }
                        });

            }
        } );


        return view ;
    }

    public  void getBookingDetails()
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "booking_id",booking_id );
        CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_PASSENGER_DETAILS, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean( "responce" );
                            if (status)
                            {
                                loadingBar.dismiss();
                                JSONArray array = response.getJSONArray( "data" );
                                for (int i = 0 ;i<array.length();i++)
                                {
                                    JSONObject object = array.getJSONObject( i );
                                    AddPassengerToSeatModel model = new AddPassengerToSeatModel();

                                    model.setPassenger_name( String.valueOf( object.get( "name" ) ) );
                                    model.setAge( String.valueOf( object.get( "age" ) ) );
                                    model.setGender( String.valueOf( object.get( "gender" ) ) );
//                                    model.setSeat_no( String.valueOf( object.get( "seat_no" ) ) );
//                                    model.setSeat_price( String.valueOf( object.get( "seat_price" ) ) );
                                    model.setNationality( String.valueOf( object.get( "nationality" ) ) );
//                                    model.setId( String.valueOf( object.get( "id" ) ) );
                                    passenger_list.add( model );

                                }
//                                recycler_passenger.setVisibility( View.VISIBLE );

                                passengerListAdapter = new PassengerListAdapter(passenger_list,getActivity());
                                recycler_passenger.setLayoutManager( new LinearLayoutManager( getActivity(),LinearLayoutManager.VERTICAL,false ) );
                                recycler_passenger.setAdapter( passengerListAdapter );
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

                        String msg=module.VolleyErrorMessage(error);
                        if(!msg.equals(""))
                        {
                            Toast.makeText(getActivity(),""+msg,Toast.LENGTH_LONG).show();
                        }
                    }
                } );
        AppController.getInstance().addToRequestQueue( customVolleyJsonRequest,"Booking Details" );

    }
    public  void  cancelRequest( String booking_id ,String user_id ,String remark)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "id",booking_id );
        params.put( "user_id",user_id );
        params.put( "remark",remark );
        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.CANCEL_BOOKING_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = Boolean.valueOf( response.getString( "responce" ) );
                            String msg = response.getString( "message" );

                                if (status)
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText( getActivity(),""+msg,Toast.LENGTH_LONG ).show();
                                    MyBookingsFragment bookingModel = new MyBookingsFragment(  );
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace( R.id.container_frame, bookingModel )
                                            .addToBackStack( null )
                                            .commit();
                                }
                                else
                                {
                                    loadingBar.dismiss();
                                    Toast.makeText( getActivity(),""+msg,Toast.LENGTH_LONG ).show();
                                }

                        } catch (JSONException e) {
                            loadingBar.dismiss();
                            e.printStackTrace();
                        }
//                        Toast.makeText( getActivity(),"" + response,Toast.LENGTH_LONG ).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingBar.dismiss();
                        String msg=module.VolleyErrorMessage(error);
                        if(!msg.equals(""))
                        {
                            Toast.makeText(getActivity(),""+msg,Toast.LENGTH_LONG).show();
                        }

                    }
                } );
        AppController.getInstance().addToRequestQueue( jsonRequest,"cancel booking" );
    }
}
