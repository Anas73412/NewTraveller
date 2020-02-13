package in.binplus.travel.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.Adapter.PassengerListAdapter;
import in.binplus.travel.Adapter.RecentSearchAdapter;
import in.binplus.travel.Adapter.SelectedStopsAdapter;
import in.binplus.travel.Adapter.StopsListAdapter;
import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.R;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;
import in.binplus.travel.util.ToastMsg;

import static in.binplus.travel.Config.Constants.KEY_ID;
import static in.binplus.travel.Config.Constants.TOPIC_GLOBAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarBookingDetailsFragments extends Fragment implements View.OnClickListener{
   RecyclerView recycler_stops ;
    TextView text_to ,txt_from,txt_date,txt_note,txt_total,txt_busname,txt_busno ,passengers ,txt_status,
            cancel_booking,txt_adhaarid ,txt_tot_seats ,txt_pass_name ,txt_pass_mobile;
    Dialog dialog ;
    TextView btn_no ,btn_yes ;
    JSONArray arr_st=null;
    EditText et_remark  ;
    String user_id,route ;
    Session_management session_management ;
    ArrayList<StopsModel> stop_list ;

    Module module ;
    RecentSearchAdapter cityAdapter ;
  //  StopsListAdapter stopsListAdapter;
    ProgressDialog loadingBar ;
    LinearLayout linear_note ,rel_status  ;

    String status, enquiry_id ,vehicle_id ,journey_date ,total_money ,vehicle_type,start_from ,end_to ,
            enquiry_date,start_date,end_date,vehicle_name,vehicle_number,name ,adhar_id,mobile,note ;



    public CarBookingDetailsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view =  inflater.inflate( R.layout.fragment_car_booking_details_fragments, container, false );

          initViews(view);

          txt_pass_name.setText( name );
          txt_pass_mobile.setText( mobile );
          txt_tot_seats.setText( journey_date );
          txt_adhaarid.setText( adhar_id );
          txt_from.setText("#"+enquiry_id );
          txt_date.setText( enquiry_date );
          if (note.equals( "" ))
          {
              linear_note.setVisibility( View.GONE );
          }
          else
          {
              linear_note.setVisibility( View.VISIBLE );
              txt_note.setText( note );
          }

        stop_list = new ArrayList<>(  );
          try
          {
               arr_st=new JSONArray( String.valueOf( route ) );

               for(int i=0; i<arr_st.length();i++)
               {
                   JSONObject object=arr_st.getJSONObject(i);
                   StopsModel stopsModel=new StopsModel();
                   stopsModel.setStop_name(object.getString("stop"));
                   stopsModel.setStop_desc("stop");

                   stop_list.add(stopsModel);
               }

//               stopsListAdapter=new StopsListAdapter(stop_list,getActivity());
              recycler_stops.setLayoutManager( new LinearLayoutManager( getActivity(),LinearLayoutManager.VERTICAL,false ) );
              cityAdapter = new RecentSearchAdapter(stop_list,getActivity());
//             recycler_stops.setScrollContainer(false);
             recycler_stops.setNestedScrollingEnabled( false );
               recycler_stops.setAdapter(cityAdapter);
             cityAdapter.notifyDataSetChanged();



           //    Toast.makeText( getActivity(),"uii"+arr_st.length(),Toast.LENGTH_LONG ).show();
          }
          catch (Exception ex)
          {
              ex.printStackTrace();
              Toast.makeText( getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG ).show();
          }

     return view ;
    }

    private void initViews(View view) {
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
        txt_note = view.findViewById( R.id.txt_note );
        txt_adhaarid = view.findViewById( R.id.txt_adhaar_id );
        txt_busname=view.findViewById( R.id.vehicle_name);
        txt_busno = view.findViewById( R.id.vehicle_no );
        linear_note = view.findViewById( R.id.linear_note );
        cancel_booking = view.findViewById( R.id.cancel_booking );
        passengers = view.findViewById( R.id.passenger_detail );
        txt_tot_seats = view.findViewById( R.id.txt_tot_seats );
        txt_pass_name = view.findViewById( R.id.booking_name );
        txt_pass_mobile =view.findViewById( R.id.txt_mobile );
        recycler_stops = view.findViewById( R.id.recycler_pass_details );
        rel_status = view.findViewById( R.id.rel_status );
        btn_no = dialog.findViewById( R.id.btn_no );
        btn_yes = dialog.findViewById( R.id.btn_yes );
        et_remark = dialog.findViewById( R.id.et_remark );
        txt_status = view.findViewById( R.id.txt_status );

        session_management = new Session_management( getActivity() );
        user_id = session_management.getUserDetails().get( KEY_ID );


        enquiry_id = getArguments().getString( "enquiry_id" );
        enquiry_date=getArguments().getString( "enquiry_date" );
        start_from =getArguments().getString( "start_from" );
        end_to =getArguments().getString( "end_to" );
        journey_date=getArguments().getString( "journey_date" );
        total_money=getArguments().getString( "total_money" );
        vehicle_id =getArguments().getString( "vehicle_id" );
        name = getArguments().getString("name");
        adhar_id = getArguments().getString( "adhar_no" );
        mobile = getArguments().getString( "mobile" );
        vehicle_number = getArguments().getString("vehicle_no");
        route = getArguments().getString("route");
        note = getArguments().getString( "note" );
       status = getArguments().getString( "status" );
       getVehicleDetails( vehicle_id );


      Toast.makeText( getActivity(),"vehicle_id" +vehicle_id,Toast.LENGTH_LONG ).show();

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



        btn_no.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
        cancel_booking.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        if(id == R.id.btn_yes)
        {
            String messge=et_remark.getText().toString();
            if(messge.equals("") || messge.isEmpty())
            {
                et_remark.setError(getActivity().getResources().getString(R.string.required_message));
                et_remark.requestFocus();
            }
            else if(messge.length()<=10)
            {
                et_remark.setError(getActivity().getResources().getString(R.string.invalid_message));
                et_remark.requestFocus();
            }
            else
            {
                cancelCarBookingRequest(enquiry_id,messge);
            }

        }
        else if(id == R.id.btn_no)
        {
           dialog.dismiss();
        }
        else if(id == R.id.cancel_booking)
        {
            dialog.show();
        }
    }

    private void cancelCarBookingRequest(String enquiry_id, String message) {

        loadingBar.show();
        final String json_tag="json_cancel";
        HashMap<String,String> map=new HashMap<>();
        map.put("enquiry_id",enquiry_id);
        map.put("message",message);
        CustomVolleyJsonRequest request=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.URL_CANCEL_CAR_BOOKING, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                loadingBar.dismiss();
                try
                {
                    String msg="";
                 boolean status=jsonObject.getBoolean("responce");
                 if(status)
                 {
                     msg=jsonObject.getString("message");
                     dialog.dismiss();
                     new ToastMsg(getActivity()).toastIconSuccess(msg);
                     MyBookingsFragment fm = new MyBookingsFragment();
                     FragmentManager fragmentManager = getFragmentManager();
                     fragmentManager.beginTransaction().replace( R.id.container_frame,fm ).addToBackStack( null ).commit();

                 }
                 else
                 {
                     msg=jsonObject.getString("error");
                     dialog.dismiss();
                     new ToastMsg(getActivity()).toastIconError(msg);
                 }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
           loadingBar.dismiss();
           String msg=module.VolleyErrorMessage(volleyError);
           if(msg.equals("") || msg.isEmpty())
           {
               new ToastMsg(getActivity()).toastIconError(msg);
           }


            }
        });
        AppController.getInstance().addToRequestQueue(request,json_tag);
    }

    public void  getVehicleDetails(String v_id)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "v_id",v_id );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.URL_VEHICLE_DETAILS, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadingBar.dismiss();
                            Boolean status = response.getBoolean( "responce" );
                            if (status)
                            {

                                Log.e( "vehicle_info",response.toString() );
                                JSONArray data = response.getJSONArray( "data" );

                                txt_busname.setText( data.getJSONObject( 0).getString(  "vehicle_name"));
                                txt_busno.setText( data.getJSONObject( 0 ).getString( "registration_no" ) );


//                                Toast.makeText( getActivity(),""+data,Toast.LENGTH_LONG ).show();
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

                    }
                } );
        AppController.getInstance().addToRequestQueue( jsonRequest,"vehicle_details" );
    }
}
