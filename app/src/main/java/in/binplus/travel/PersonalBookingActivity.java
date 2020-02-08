package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

import static in.binplus.travel.BookingConfirmation.getRandomKey;

public class PersonalBookingActivity extends AppCompatActivity {
    EditText txt_mobile ,txt_address ,txt_name,txt_adhar_id;
    TextView txt_to ,txt_from ,txt_timeto ,txt_timefrom ,txt_busname ,txt_tot_price ;
    Button btnConfirm ;
    ImageView back ;
  String formattedDate ;
  String user_id ;
  Session_management session_management ;
    JSONArray passArray ;
    String random ;
    String sub_name ;
    String booking_id ;
    String adhar_id ,u_name,mobile,address ;
    String left_wallet_amount ;
    Dialog dialog;
    GifImageView dialog_gif ;
    TextView dialog_txt ;
    Button dialog_btn ;


    public static String seat_no ,seat_id ,seat_type ,seat_price ,seat_status ,bus_type,bus_name,bus_seats,bus_desc,bus_image,bus_no,station_to,
            station_from,start_time,end_time,price,duration,agency_name,date,stops ,bus_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_personal_booking );

        txt_mobile= findViewById( R.id.et_mobile );
        txt_address=findViewById( R.id.et_address );
        txt_to =findViewById( R.id.txt_to );
        txt_from =findViewById( R.id.txt_from );
        txt_timeto = findViewById( R.id.time_to );
        txt_timefrom = findViewById( R.id.time_from );
        txt_busname = findViewById( R.id.txt_vehicle_name );
        txt_adhar_id = findViewById( R.id.et_adhar_id );
        txt_tot_price = findViewById( R.id.txt_tot_price );

        txt_name =findViewById( R.id.et_name );
        back = findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        btnConfirm = findViewById( R.id.btnContinue );

        session_management = new Session_management( PersonalBookingActivity.this );
         passArray =new JSONArray();


        bus_id = getIntent().getStringExtra( "bus_id" );
        bus_name = getIntent().getStringExtra( "name" );
        bus_type = getIntent().getStringExtra( "bus_type" );
        bus_seats =getIntent().getStringExtra( "seats" );
        bus_no =getIntent().getStringExtra( "bus_no" );
        bus_desc=getIntent().getStringExtra( "bus_desc" );
        station_to= getIntent().getStringExtra( "station_to" );
        station_from= getIntent().getStringExtra( "station_from" );
        end_time= getIntent().getStringExtra( "end_time");
        start_time=getIntent().getStringExtra( "start_time" );
        price=getIntent().getStringExtra( "price" );
        duration=getIntent().getStringExtra( "duration");
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

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat( "DDMMYY" );
        formattedDate = df.format( c.getTime() );



        btnConfirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mobile = txt_mobile.getText().toString();
                address = txt_address.getText().toString();
                adhar_id= txt_adhar_id.getText().toString();
                u_name =txt_name.getText().toString();


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
                else if (address.isEmpty())
                {
                    txt_address.setError( "Enter Address" );
                    txt_address.requestFocus();
                }
                else if (adhar_id.isEmpty())
                {
                    txt_adhar_id.setError( "Enter Adhar id" );
                    txt_adhar_id.requestFocus();
                }
                else if (adhar_id.length()<12)
                {
                    txt_adhar_id.setError( "Enter valid Adhar id" );
                    txt_adhar_id.requestFocus();
                }

                else
                {
                    random = getRandomKey( 3 );
                    sub_name = u_name.substring( 0, 3 );
                    booking_id = random + sub_name + formattedDate;
                    JSONObject jObjP = new JSONObject();
                    try {
                        //  jObjP.put( "seat_id", EnterPassengerDetails.passenger_list.get( i ).getSeat_id() );
                        jObjP.put( "seat_no","" );
                        jObjP.put( "seat_price",price );
                        jObjP.put( "name",u_name );
                        jObjP.put( "age",  "");
                        jObjP.put( "nationality","" );
                        jObjP.put( "gender", "");
                        jObjP.put( "booking_id", booking_id );

                        passArray.put( jObjP );

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                    makeBooking(passArray);
//                    Toast.makeText( PersonalBookingActivity.this,"Passenger Details added",Toast.LENGTH_LONG ).show();
//                    Intent intent = new Intent( PersonalBookingActivity.this,BookingConfirmation.class );
//                    startActivity( intent );
                }
            }
        } );




    }

    public void makeBooking(JSONArray jsonArray)
    {
        HashMap<String,String> params =new HashMap<>(  );
        params.put( "user_id",user_id );
        params.put( "vehicles_id",bus_id );
        params.put( "total_seats" , "" );
        params.put( "journey_startdate",formattedDate );
        params.put("journey_enddate",formattedDate);
        params.put( "start_from",station_from);
        params.put( "end_to",station_to);
        params.put( "booking_id",booking_id );
        params.put( "mobile",mobile );
        params.put( "address",address );
        params.put( "total_money",price );
        params.put( "adhar_no",adhar_id );
        params.put( "b_name",u_name );

        params.put( "passenger_list", String.valueOf( jsonArray ) );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.MAKE_BOOKING, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean( "responce" );
                            Toast.makeText( PersonalBookingActivity.this, ""+response,Toast.LENGTH_LONG).show();
                            if (status)
                            {
                                String msg = response.getString( "data" );
                                //    Toast.makeText( BookingConfirmation.this, ""+msg,Toast.LENGTH_LONG).show();
                                session_management.updateWallet( left_wallet_amount );
//
                                dialog=new Dialog(PersonalBookingActivity.this);
                                dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.dialogue_booking_confirmation);
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.show();
                                dialog_gif = dialog.findViewById( R.id.dialog_gif );
                                dialog_txt = dialog.findViewById( R.id.text_msg );
                                dialog_btn =dialog.findViewById( R.id.btn_ok );

                                dialog_txt.setText( "Your booking is CONFIRMED  with Booking Id :"+booking_id  );
                                dialog_btn.setOnClickListener( new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent( PersonalBookingActivity.this,MainActivity.class );
                                        startActivity( intent );
                                        dialog.dismiss();
                                    }
                                } );


                            }
                            else
                            {
//                                Toast.makeText( BookingConfirmation.this, ""+response,Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( PersonalBookingActivity.this,error.getMessage(),Toast.LENGTH_LONG ).show();

                    }
                } );

        AppController.getInstance().addToRequestQueue( jsonRequest,"add Booking" );
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
