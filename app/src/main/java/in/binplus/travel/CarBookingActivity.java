package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.util.CustomVolleyJsonRequest;
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
    ProgressDialog loadingBar ;



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

        session_management = new Session_management( CarBookingActivity.this );
        user_id = session_management.getUserDetails().get( KEY_ID );
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

//        Toast.makeText( CarBookingActivity.this,""+bus_id,Toast.LENGTH_LONG ).show();

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat( "DDMMYY" );
        formattedDate = df.format( c.getTime() );

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
                else if (address.isEmpty())
                {
                    txt_address.setError( "Enter Address" );
                    txt_address.requestFocus();
                }
                else if (adhar_id.isEmpty())
                {
                    txt_adhar_id.setError( "Enter Adhaar id" );
                    txt_adhar_id.requestFocus();
                }
                else if (adhar_id.length()<12)
                {
                    txt_adhar_id.setError( "Enter valid Adhaar id" );
                    txt_adhar_id.requestFocus();
                }

                else
                {


                    makeEnquiry();
//
                }
//                makeEnquiry();

            }
        } );




    }
//
//    public void makeEnquiry(JSONArray jsonArray)
    public void makeEnquiry()
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

      //  params.put( "passenger_list", String.valueOf( jsonArray ) );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST,BaseURL.MAKE_ENQUIRY,params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadingBar.dismiss();
                            Log.e("car_booking",response.toString() );
                            Boolean status = response.getBoolean( "responce" );
                            Toast.makeText( CarBookingActivity.this, ""+response.toString(),Toast.LENGTH_LONG).show();
                            if (status)
                            {
                                String msg = response.getString( "message" );
                                //    Toast.makeText( BookingConfirmation.this, ""+msg,Toast.LENGTH_LONG).show();
                                session_management.updateWallet( left_wallet_amount );
//
                                dialog=new Dialog( CarBookingActivity.this);
                                dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialogue_booking_confirmation);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                                dialog_gif = dialog.findViewById( R.id.dialog_gif );
                                dialog_txt = dialog.findViewById( R.id.text_msg );
                                dialog_btn =dialog.findViewById( R.id.btn_ok );

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
    public static String getRandomKey(int i)
    {
        final String characters="0123456789";
        StringBuilder stringBuilder=new StringBuilder();
        while (i>0)
        {
            Random ran=new Random();
            stringBuilder.append(characters.charAt(ran.nextInt(characters.length())));
            i--;
        }
        return stringBuilder.toString();
    }

//    public Double getTotalAmount()
//    {
//        for (int i=0 ;i<EnterPassengerDetails.passenger_list.size();i++)
//        {
//            Double seat_price = Double.valueOf( EnterPassengerDetails.passenger_list.get( i ).getSeat_price() );
//            total_amount = total_amount+seat_price;
//        }
//        return total_amount ;
//    }

}
