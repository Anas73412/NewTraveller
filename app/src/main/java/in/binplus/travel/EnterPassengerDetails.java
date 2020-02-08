package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.binplus.travel.Adapter.AddPassengerToSeatAdapter;
import in.binplus.travel.Adapter.SelectedSeatAdapter;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.Model.SeatModel;
import in.binplus.travel.util.RecyclerTouchListener;

public class EnterPassengerDetails extends AppCompatActivity {
    EditText txt_mobile ,txt_address ,txt_name,txt_adhar_id;
    TextView txt_to ,txt_from ,txt_timeto ,txt_timefrom ,txt_busname ;
    RecyclerView recyclerView ;
    RecyclerView recycler_passenger ;

    SelectedSeatAdapter selectedSeatAdapter ;
    AddPassengerToSeatAdapter addPassengerToSeatAdapter ;
    public static Dialog dialog;
    public static EditText d_txt_name ,d_txt_age ,d_txt_nationality ;
    public  static TextView submit_detail ;
    public static RadioButton d_radio_male ,d_radio_female ;
    public  static RadioGroup d_radio_group ;
    public static ImageView img_close ;
    public static  ArrayList<AddPassengerToSeatModel> passenger_list;
   public static String seat_no ,seat_id ,seat_type ,seat_price ,seat_status ,bus_type,bus_name,bus_seats,bus_desc,bus_image,bus_no,station_to,
            station_from,start_time,end_time,price,duration,agency_name,date,stops ,bus_id;
    Button btnContinue ;
    ImageView back ;
    String getname ,getage , getnationality;
    String  getgender = "";
    LinearLayout linear_seat ,linear_passenger ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_enter_passenger_details );


        txt_mobile= findViewById( R.id.et_mobile );
        txt_address=findViewById( R.id.et_address );
        txt_to =findViewById( R.id.txt_to );
        txt_from =findViewById( R.id.txt_from );
        txt_timeto = findViewById( R.id.time_to );
        txt_timefrom = findViewById( R.id.time_from );
        txt_busname = findViewById( R.id.txt_vehicle_name );
        txt_adhar_id = findViewById( R.id.et_adhar_id );
        txt_name =findViewById( R.id.et_name );

        recyclerView = findViewById( R.id.recycler_seat_details);
        recycler_passenger = findViewById( R.id.recycler_pass_details );
        recycler_passenger.setNestedScrollingEnabled( false );
        linear_passenger =findViewById( R.id.linear_passenger );
        linear_seat =findViewById( R.id.linear_seats );
        back = findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        btnContinue = findViewById( R.id.btnContinue );





        passenger_list = new ArrayList<>(  );
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

        selectedSeatAdapter =new SelectedSeatAdapter( SelectSeatActivity.selected_seat_list,this );
        recyclerView.setLayoutManager( new LinearLayoutManager( this,LinearLayoutManager.HORIZONTAL,false ) );
        recyclerView.setAdapter( selectedSeatAdapter );
     //   Toast.makeText( EnterPassengerDetails.this ,""+seatList,Toast.LENGTH_LONG ).show();
        recyclerView.addOnItemTouchListener( new RecyclerTouchListener( this, recyclerView, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                seat_no = SelectSeatActivity.selected_seat_list.get( position ).getSeat_no();
                seat_type = SelectSeatActivity.selected_seat_list.get( position ).getSeat_type();
                seat_id =SelectSeatActivity.selected_seat_list.get( position ).getSeat_id();
                seat_price=SelectSeatActivity.selected_seat_list.get( position ).getSeat_price();
                seat_status=SelectSeatActivity.selected_seat_list.get( position ).getSeat_status();
                bus_type = SelectSeatActivity.selected_seat_list.get( position ).getBus_type();
                dialog=new Dialog(EnterPassengerDetails.this);
                dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_passenger_detail);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                d_txt_name = dialog.findViewById( R.id.et_name );
                d_txt_age = dialog.findViewById( R.id.et_age );
                d_txt_nationality=dialog.findViewById( R.id.et_nationality );
                d_radio_female=dialog.findViewById( R.id.female );
                d_radio_male=dialog.findViewById( R.id.male );
                d_radio_group =dialog.findViewById( R.id.radio_group );
                submit_detail= dialog.findViewById( R.id.submitDetail );
                img_close = dialog.findViewById( R.id.close );

                submit_detail.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       getname = d_txt_name.getText().toString();
                        getage = d_txt_age.getText().toString();
                        getnationality = d_txt_nationality.getText().toString();



                        if (d_radio_female.isChecked()) {
                                    getgender = "female";
                                } else if (d_radio_male.isChecked()) {
                                    getgender = "male";
                                }
                        else
                        {
                            getgender = "";
                        }
                        if (getname.isEmpty())
                        {
                            d_txt_name.setError( "Enter name" );
                            d_txt_name.requestFocus();
                        }
                        else if (getage.isEmpty())
                        {
                            d_txt_age.setError( "Enter Age" );
                            d_txt_age.requestFocus();
                        }
                        else if (getage.length()>2)
                        {
                            d_txt_age.setError( "Enter valid age" );
                            d_txt_age.requestFocus();

                        }
                        else if (getage.equals( "" ))
                        {
                            Toast.makeText( EnterPassengerDetails.this,"Select Gender",Toast.LENGTH_LONG ).show();
                            d_radio_group.requestFocus();
                        }

                        else {

                           // passenger_list.add( new AddPassengerToSeatModel(getname,getage,getgender,getnationality ) );
                           linear_passenger.setVisibility( View.VISIBLE );
                            recycler_passenger.setLayoutManager( new LinearLayoutManager(EnterPassengerDetails.this,LinearLayoutManager.VERTICAL,false ) );
                            addPassengerToSeatAdapter = new AddPassengerToSeatAdapter( EnterPassengerDetails.this,passenger_list );
                            recycler_passenger.setAdapter(addPassengerToSeatAdapter );
                              SelectSeatActivity.selected_seat_list.remove( position );


                                dialog.dismiss();
                            }
//                        Toast.makeText( EnterPassengerDetails.this,""+getgender,Toast.LENGTH_LONG ).show();
                        }



                } );
                img_close.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                } );


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        } ) );




        btnContinue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getmobile = txt_mobile.getText().toString();
                String getadd = txt_address.getText().toString();
                String getA_id= txt_adhar_id.getText().toString();
                String getname =txt_name.getText().toString();

                if (getname.isEmpty())
                {
                    txt_name.requestFocus();
                    txt_name.setError( "Enter Name" );
                }
               else if (getmobile.isEmpty())
                {
                    txt_mobile.requestFocus();
                    txt_mobile.setError( "Enter Mobile no" );
                }
                else if (getadd.isEmpty())
                {
                    txt_address.setError( "Enter Address" );
                    txt_address.requestFocus();
                }
                else if (getA_id.isEmpty())
                {
                    txt_adhar_id.setError( "Enter Adhar id" );
                    txt_adhar_id.requestFocus();
                }
                else if (getA_id.length()<12)
                {
                    txt_adhar_id.setError( "Enter valid Adhar id" );
                    txt_adhar_id.requestFocus();
                }
                else if (passenger_list.size()==0)
                {
                    Toast.makeText( EnterPassengerDetails.this,"Enter Passenger Details to Continue" ,Toast.LENGTH_LONG ).show();
                }
                else
                {
                    Toast.makeText( EnterPassengerDetails.this,"Passenger Details added",Toast.LENGTH_LONG ).show();
                    Intent intent = new Intent( EnterPassengerDetails.this,BookingConfirmation.class );
                    intent.putExtra( "mobile",getmobile );
                    intent.putExtra( "address",getadd );
                    intent.putExtra( "adhar_id",getA_id );
                    intent.putExtra( "u_name",getname );


                    startActivity( intent );
                }

            }
        } );

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!(passenger_list.isEmpty()))
        {
            recycler_passenger.setVisibility( View.VISIBLE );
            recycler_passenger.setLayoutManager( new LinearLayoutManager(EnterPassengerDetails.this,LinearLayoutManager.VERTICAL,false ) );
            addPassengerToSeatAdapter = new AddPassengerToSeatAdapter( EnterPassengerDetails.this,passenger_list );
            recycler_passenger.setAdapter(addPassengerToSeatAdapter );
        }
        else
        {
            linear_passenger.setVisibility( View.GONE );
        }

        if (SelectSeatActivity.selected_seat_list.isEmpty())
        {
            linear_seat.setVisibility( View.GONE );
           // recyclerView.setVisibility( View.GONE );
        }

    }


}
