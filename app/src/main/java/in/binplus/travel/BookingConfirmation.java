package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import in.binplus.travel.Adapter.AddPassengerToSeatAdapter;
import in.binplus.travel.Adapter.PassengerListAdapter;
import in.binplus.travel.Adapter.Seat_PassengerDeatilsAdapter;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Fragment.HomeFragment;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.Model.PassengerDetailsModel;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;
import pl.droidsonroids.gif.GifImageView;

import static in.binplus.travel.Config.Constants.KEY_ID;
import static in.binplus.travel.Config.Constants.KEY_WALLET_Ammount;

public class BookingConfirmation extends AppCompatActivity {
    TextView txt_to ,txt_from ,txt_timeto ,txt_timefrom ,txt_name,txt_mobile ,txt_busname ,txt_tot_amount ,txt_address ,txt_adhar, txt_booking_id;
    RecyclerView recycler_passenger ;
    ImageView back ;
    Seat_PassengerDeatilsAdapter seat_passengerDeatilsAdapter;
    PassengerListAdapter passengerListAdapter ;
    String random ;
    String sub_name ;
    String booking_id ;
    String currentDate ;
    Session_management session_management ;
    String user_id ,wallet_amount ;
   Double total_amount =0.0 ;
   Button btnConfirm ;
   Module module ;
   String board_location="",drop_location="";
   String bus_id ,bus_name ,startfrom ,endto,timefrom,timeto;
   int total_seats ;
    JSONArray passArray ;
    String formattedDate ;

    String adhar_id ,u_name,mobile,address,j_date ;
    String left_wallet_amount,seat_fare ;
    Dialog dialog;
    GifImageView dialog_gif ;
    TextView dialog_txt ;
    Button dialog_btn ;
    ArrayList<AddPassengerToSeatModel> passengerList  = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking_confirmation );

        txt_mobile = findViewById( R.id.txt_mobile );
        txt_address = findViewById( R.id.txt_address );
        txt_to = findViewById( R.id.txt_to );
        txt_from = findViewById( R.id.txt_from );
        txt_timeto = findViewById( R.id.time_to );
        txt_timefrom = findViewById( R.id.time_from );
        txt_busname = findViewById( R.id.txt_vehicle_name );
        txt_name = findViewById( R.id.txt_name );
        txt_tot_amount = findViewById( R.id.txt_tot_amount );
        txt_adhar = findViewById( R.id.txt_adhar_id );
        txt_booking_id = findViewById( R.id.txt_booking_id );
        recycler_passenger = findViewById( R.id.recycler_pass_details );
        btnConfirm = findViewById( R.id.btnConfirm );
        back = findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );



        seat_fare =getIntent().getStringExtra( "seat_fare" );
        Double fare = Double.parseDouble( seat_fare );
        Double tot_seats = Double.valueOf( AddPassengerDetails.p_list.size() );
        total_amount =fare*tot_seats ;

        bus_id = SelectSeatActivity.bus_id;
        bus_name = SelectSeatActivity.vehicle_name;
        startfrom =SelectSeatActivity.source;
        endto =SelectSeatActivity.destination;
        timefrom=SelectSeatActivity.start_time;
        timeto= SelectSeatActivity.end_time;
        total_seats = AddPassengerDetails.p_list.size();

         adhar_id = getIntent().getStringExtra( "adhar_id" );
        mobile = getIntent().getStringExtra( "mobile" );
        j_date = getIntent().getStringExtra( "date" );
        u_name = getIntent().getStringExtra( "p_name" ) ;
        board_location = getIntent().getStringExtra( "board" ) ;
        drop_location = getIntent().getStringExtra( "drop" ) ;

        module = new Module( BookingConfirmation.this );


        txt_mobile.setText( mobile );
//      txt_address.setText( address );
        txt_adhar.setText( adhar_id );
        txt_name.setText( u_name );
        txt_to.setText( SelectSeatActivity.destination);
        txt_from.setText( SelectSeatActivity.source);
        txt_timefrom.setText( SelectSeatActivity.start_time );
        txt_timeto.setText(SelectSeatActivity.end_time );
        txt_busname.setText(SelectSeatActivity.vehicle_name );
        session_management = new Session_management( BookingConfirmation.this );
        user_id = session_management.getUserDetails().get( KEY_ID);
        wallet_amount= session_management.getUserDetails().get( KEY_WALLET_Ammount );
        recycler_passenger.setLayoutManager( new LinearLayoutManager( BookingConfirmation.this, LinearLayoutManager.VERTICAL, false ) );
//     seat_passengerDeatilsAdapter = new Seat_PassengerDeatilsAdapter( BookingConfirmation.this, AddPassengerDetails.p_list);
     passengerListAdapter = new PassengerListAdapter(  AddPassengerDetails.p_list,BookingConfirmation.this);
        recycler_passenger.setAdapter( passengerListAdapter );
//        Toast.makeText(this, ""+AddPassengerDetails.p_list.size(), Toast.LENGTH_SHORT).show();
        Calendar c = Calendar.getInstance();
        Date date = new Date(  );

        SimpleDateFormat df = new SimpleDateFormat( "ddMMyyhhmm" );
        SimpleDateFormat dateFormat = new SimpleDateFormat( "ddMMyyhhmm" );
        currentDate = dateFormat.format( date );

        formattedDate = df.format( date );


        random = getRandomKey( 3 );
//        sub_name = u_name.substring( 0, 3 );
        booking_id = random + user_id + formattedDate;
        txt_booking_id.setText( "Booking Id: " + booking_id );

        getTotalAmount();
        txt_tot_amount.setText(this.getResources().getString( R.string.currency )+ String.valueOf( total_amount ) );
        Double wallet_Amt = Double.parseDouble( wallet_amount );
        Double left_amt = wallet_Amt-total_amount;
        left_wallet_amount =String.valueOf( left_amt );

        //  Toast.makeText(BookingConfirmation.this,"booking id " +formattedDate,Toast.LENGTH_LONG).show();
        btnConfirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(BookingConfirmation.this);

                builder.setMessage("Do you want to confirm this booking ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (AddPassengerDetails.p_list.size() > 0)
                                {
                                    passArray = new JSONArray();
                                    for (int i = 0; i < AddPassengerDetails.p_list.size(); i++)
                                    {
                                        JSONObject jObjP = new JSONObject();
                                        try {
                                            //  jObjP.put( "seat_id", EnterPassengerDetails.passenger_list.get( i ).getSeat_id() );
                                            jObjP.put( "seat_no", AddPassengerDetails.p_list.get( i ).getSeat_no() );
                                            jObjP.put( "seat_price", seat_fare );
                                            jObjP.put( "name",AddPassengerDetails.p_list.get( i ).getPassenger_name() );
                                            jObjP.put( "age",AddPassengerDetails.p_list.get( i ).getAge());
                                            jObjP.put( "nationality",AddPassengerDetails.p_list.get( i).getNationality() );
                                            jObjP.put( "gender", AddPassengerDetails.p_list.get( i).getGender() );
                                            jObjP.put( "vehicle_id",bus_id );
                                            jObjP.put( "journey_startdate",j_date );

                                            jObjP.put( "booking_id", booking_id );

                                            passArray.put( jObjP );

                                        } catch (JSONException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                    //     Toast.makeText(BookingConfirmation.this,"json Array " +passArray,Toast.LENGTH_LONG).show();
                                    makeBooking( passArray );
//                    Toast.makeText(BookingConfirmation.this,"seat fare" +formattedDate,Toast.LENGTH_LONG).show();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        } );

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
    public Double getTotalAmount()
    {

        for (int i=0 ;i<AddPassengerDetails.p_list.size();i++)
        {
           Double seat_price = 0.0;
            total_amount = total_amount+seat_price;
        }
        return total_amount ;
    }

    public void makeBooking( JSONArray passArray)
    {
        HashMap<String,String> params =new HashMap<>(  );
        params.put( "user_id",user_id );
        params.put( "vehicles_id",bus_id );
        params.put( "total_seats" , String.valueOf( total_seats ) );
        params.put( "journey_startdate",j_date );
        params.put( "start_from",startfrom );
        params.put( "end_to",endto );
        params.put( "booking_id",booking_id );
        params.put( "mobile",mobile );
        params.put( "address","" );
        params.put( "total_money", String.valueOf( total_amount ) );
        params.put( "adhar_no",adhar_id );
        params.put( "b_name",u_name );
        params.put("board",board_location);
        params.put("drop",drop_location);
        params.put( "passenger_list", String.valueOf( passArray ) );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.MAKE_BOOKING, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean( "responce" );
                            if (status)
                            {
                                String msg = response.getString( "data" );
                            //    Toast.makeText( BookingConfirmation.this, ""+msg,Toast.LENGTH_LONG).show();
                                session_management.updateWallet( left_wallet_amount );
//
                                dialog=new Dialog(BookingConfirmation.this);
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
                                        Intent intent = new Intent( BookingConfirmation.this,MainActivity.class );
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
                        String msg=module.VolleyErrorMessage(error);
                        if(!(msg.isEmpty() || msg.equals("")))
                        {
                            Toast.makeText(BookingConfirmation.this,""+msg.toString(),Toast.LENGTH_SHORT).show();
                        }
                //        Toast.makeText( BookingConfirmation.this,error.getMessage(),Toast.LENGTH_LONG ).show();

                    }
                } );

        AppController.getInstance().addToRequestQueue( jsonRequest,"add Booking" );
    }

}
