package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.Adapter.SelectedStopsAdapter;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.RecyclerTouchListener;
import in.binplus.travel.util.Session_management;
import pl.droidsonroids.gif.GifImageView;

import static in.binplus.travel.Config.Constants.KEY_ID;

public class CarBookingActivity extends AppCompatActivity {
    EditText txt_mobile ,txt_address ,txt_name,txt_adhar_id ,txt_message;
    TextView txt_to ,txt_from ,txt_timeto ,txt_timefrom ,txt_busname ,txt_tot_price ;
    Button btnConfirm ;
    ImageView back ;
    TextView title ;
     String formattedDate ;
    String user_id ;
  Session_management session_management ;
    JSONArray passArray ;
    String random ;
    String sub_name ;
    String booking_id ;
    String adhar_id ,u_name,mobile,address,note ;
    String left_wallet_amount ;
    Dialog dialog;
    GifImageView dialog_gif ;
    TextView dialog_txt ;
    Button dialog_btn ;
    Module module ;
    ProgressDialog loadingBar ;
    RecyclerView recycler_stops ;
    ArrayList<StopsModel> stop_list ;
    SelectedStopsAdapter stopsAdapter ;
    public static  ArrayList<StopsModel> selected_stop_list ;



    public static String seat_no ,seat_id ,seat_type ,seat_price ,seat_status ,bus_type,bus_name,bus_seats,bus_desc,bus_image,bus_no,station_to,
            station_from,start_time,end_time,price,duration,agency_name,date,stops ,bus_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_car_booking );

        txt_mobile= findViewById( R.id.et_mobile );
        txt_address=findViewById( R.id.et_address );
        txt_message = findViewById( R.id.et_message );

        txt_to =findViewById( R.id.txt_to );
        txt_from =findViewById( R.id.txt_from );
        txt_timeto = findViewById( R.id.time_to );
        txt_timefrom = findViewById( R.id.time_from );
        txt_busname = findViewById( R.id.txt_vehicle_name );
        txt_adhar_id = findViewById( R.id.et_adhar_id );
        txt_tot_price = findViewById( R.id.txt_tot_price );
        title = findViewById( R.id.title );

        txt_name =findViewById( R.id.et_name );
        back = findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        btnConfirm = findViewById( R.id.btnContinue );
        recycler_stops = findViewById( R.id.recycler_stops );
        recycler_stops.setLayoutManager( new GridLayoutManager( CarBookingActivity.this,2 ) );
       recycler_stops.setNestedScrollingEnabled( false );

        module = new Module( CarBookingActivity.this );
        session_management = new Session_management( CarBookingActivity.this );
        user_id = session_management.getUserDetails().get( KEY_ID );
        stop_list = new ArrayList<>(  );
        selected_stop_list = new ArrayList<>(  );
         passArray =new JSONArray();

        bus_id = getIntent().getStringExtra( "id" );
        bus_name = getIntent().getStringExtra( "vehicle_name" );
        bus_type = getIntent().getStringExtra( "vehicle_type" );
        bus_seats =getIntent().getStringExtra( "total_seats" );
        bus_no =getIntent().getStringExtra( "bus_no" );
        bus_desc=getIntent().getStringExtra( "bus_desc" );
        station_to= getIntent().getStringExtra( "station_to" );
        station_from= getIntent().getStringExtra( "station_from" );
        end_time= getIntent().getStringExtra( "end_time");
        start_time=getIntent().getStringExtra( "start_time" );
        price=getIntent().getStringExtra( "price" );
       // duration=getIntent().getStringExtra( "duration");
        agency_name=getIntent().getStringExtra( "agency_name" );
        date=getIntent().getStringExtra( "date");
        bus_image=getIntent().getStringExtra( "bus_image" );
        stops=getIntent().getStringExtra( "stops");

        txt_busname.setText( bus_name );
        txt_timefrom.setText( start_time );
        txt_from.setText( station_from );
        txt_to.setText( station_to );
        txt_timeto.setText( end_time );
        txt_tot_price.setText(this.getResources().getString( R.string.currency ) +price );

        get_car_points( station_to,station_from );

//        Toast.makeText( CarBookingActivity.this,""+bus_id,Toast.LENGTH_LONG ).show();



        loadingBar = new ProgressDialog( CarBookingActivity.this );



        btnConfirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mobile = txt_mobile.getText().toString();
                address = txt_address.getText().toString();
                adhar_id= txt_adhar_id.getText().toString();
                u_name =txt_name.getText().toString();
                note = txt_message.getText().toString();


                if (u_name.isEmpty())
                {
                    txt_name.requestFocus();
                    txt_name.setError( "Enter Name" );
                }
                else if (mobile.isEmpty())
                {
                    txt_mobile.requestFocus();
                    txt_mobile.setError( "Enter Mobile no" );
                }
                else if (mobile.length()!=10)
                {
                    txt_mobile.requestFocus();
                    txt_mobile.setError( "Enter Valid Mobile No" );
                }
//                else if (address.isEmpty())
//                {
//                    txt_address.setError( "Enter Address" );
//                    txt_address.requestFocus();
//                }
                else if (adhar_id.isEmpty())
                {
                    txt_adhar_id.setError( "Enter Adhaar id" );
                    txt_adhar_id.requestFocus();
                }
                else if (adhar_id.length()!=12)
                {
                    txt_adhar_id.setError( "Enter valid Adhaar id" );
                    txt_adhar_id.requestFocus();
                }

                else
                {
                    if (selected_stop_list.size() > 0) {
                        passArray = new JSONArray();
                        for (int i = 0; i < selected_stop_list.size(); i++) {
                            JSONObject jObjP = new JSONObject();
                            try {

                                jObjP.put( "stop", selected_stop_list.get( i ).getStop_name() );


                                passArray.put( jObjP );

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                    makeEnquiry(passArray);

                }
          //      Toast.makeText( CarBookingActivity.this,""+CarBookingActivity.selected_stop_list.size(),Toast.LENGTH_LONG ).show();

//
            }
        } );




    }
//
    public void makeEnquiry(JSONArray jsonArray)

    {
        loadingBar.show();

        HashMap<String,String> params =new HashMap<>(  );
        params.put( "franchise_id",user_id);
        params.put( "name",u_name );
        params.put( "vehicle_id",bus_id );
        params.put("mobile_no",mobile);
        params.put( "adhaar_no",adhar_id );
        params.put( "from_location",station_from );
        params.put( "to_location",station_to );
        params.put( "note",note );
        params.put( "journey_date",date );
      params.put( "route", String.valueOf( jsonArray ) );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST,BaseURL.MAKE_ENQUIRY,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadingBar.dismiss();
                            Log.e("car_booking",response.toString() );
                            Boolean status = response.getBoolean( "responce" );
//                            Toast.makeText( CarBookingActivity.this, ""+response.toString(),Toast.LENGTH_LONG).show();
                            if (status)
                            {
                                String msg = response.getString( "message" );
                                //    Toast.makeText( BookingConfirmation.this, ""+msg,Toast.LENGTH_LONG).show();
//                                session_management.updateWallet( left_wallet_amount );

                                dialog=new Dialog( CarBookingActivity.this);
                                dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialogue_booking_confirmation);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                                dialog_gif = dialog.findViewById( R.id.dialog_gif );
                                dialog_txt = dialog.findViewById( R.id.text_msg );
                                dialog_btn =dialog.findViewById( R.id.btn_ok );
                                dialog_gif.setBackgroundResource( R.drawable.cargif );

                                dialog_txt.setText( msg );
                                dialog_btn.setOnClickListener( new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent( CarBookingActivity.this,MainActivity.class );
                                        startActivity( intent );
                                        dialog.dismiss();
                                    }
                                } );


                            }
                            else
                            {
                                Toast.makeText( CarBookingActivity.this, ""+response,Toast.LENGTH_LONG).show();
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
                        Toast.makeText( CarBookingActivity.this,error.getMessage(),Toast.LENGTH_LONG ).show();

                    }
                } );

        AppController.getInstance().addToRequestQueue( jsonRequest,"add car Booking" );
    }
   public void get_car_points(String to ,String from)
   {
       HashMap<String,String> params = new HashMap<>(  );
       params.put( "to",to );
       params.put( "from",from );
       stop_list.clear();

       CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_CAR_POINTS, params,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
//                       Toast.makeText(CarBookingActivity.this, ""+response, Toast.LENGTH_SHORT ).show();
                       try {
                           Log.e( "car_points",response.toString() );
                           Boolean status = response.getBoolean( "responce" );
                           if (status)
                           {
                              JSONObject data = (JSONObject) response.get( "data" );

                               JSONArray array_from = data.getJSONArray( "from" );

                               JSONObject from_obj = array_from.getJSONObject( 0 );
                               String from = from_obj.getString( "car_stops" );
                               JSONArray from_arr = new JSONArray( from );
                               for (int i =0 ; i<from_arr.length();i++)
                               {
                                   StopsModel model_from = new StopsModel( );
                                   model_from.setStop_name( (String) from_arr.get( i ) );
                                   stop_list.add( model_from );
                               }
                               JSONArray array_to = data.getJSONArray( "to" );
                               JSONObject to_obj = array_to.getJSONObject( 0 );
                               String to = to_obj.getString( "car_stops" );
                               JSONArray to_arr = new JSONArray(to );
                               for (int i =0 ; i<to_arr.length();i++)
                               {
                                   StopsModel model_to = new StopsModel( );
                                   model_to.setStop_name( (String) from_arr.get( i ) );
                                   stop_list.add( model_to );
                               }

                               Toast.makeText(CarBookingActivity.this, ""+from_arr.length() +"\n"+to_arr.length(), Toast.LENGTH_SHORT ).show();

                               stopsAdapter = new SelectedStopsAdapter( stop_list,CarBookingActivity.this );
                               recycler_stops.setAdapter( stopsAdapter );
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }


                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       String msg=module.VolleyErrorMessage(error);
                       if(!(msg.isEmpty() || msg.equals("")))
                       {
                           Toast.makeText(CarBookingActivity.this,""+msg.toString(),Toast.LENGTH_SHORT).show();
                       }

                   }
               } );
       AppController.getInstance().addToRequestQueue( jsonRequest,"car_points" );

   }

}

