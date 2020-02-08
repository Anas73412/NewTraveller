package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import in.binplus.travel.Adapter.AvailableBusesAdapter;
import in.binplus.travel.Model.AvailableBusesModel;
import in.binplus.travel.util.RecyclerTouchListener;
import pl.droidsonroids.gif.GifImageView;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linear_from , linear_to , linear_date ;
    public static TextView txt_from , txt_to ,txt_date ;
    RadioButton radio_car,radio_bus, radio_sharing;
    public String vehicle_type ="" ;
    public ImageView back ;
    Button btn_search ;
    private int mYear, mMonth, mDay;
    RelativeLayout rel_swap ;
    RecyclerView recycler_bus ;
    RelativeLayout rel_search ;
    Intent intent ;
    ProgressDialog loadingBar ;
    String j_date="";
    GifImageView gifImageView ;
    public  static   ArrayList<AvailableBusesModel>buslist ;
//    AvailableBusesAdapter busadpter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking );

        rel_search = findViewById( R.id.search_layout );

        linear_from = findViewById( R.id.linear_from );
        linear_date = findViewById( R.id.linear_date );
        linear_to = findViewById( R.id.linear_to );
        txt_date = findViewById( R.id.txt_date );
        txt_from =findViewById( R.id.txt_from );
        txt_to = findViewById( R.id.txt_to );
        radio_bus = findViewById(R.id.radio_bus);
        radio_sharing=findViewById(R.id.radio_sharing);
        radio_car =findViewById(R.id.radio_car);
        btn_search = findViewById( R.id.btnSearch );
        rel_swap = findViewById( R.id.rel_swap );
        back = findViewById( R.id.back );
        gifImageView = findViewById( R.id.gif_img );
        recycler_bus = findViewById( R.id.recycler_bus );
//        recycler_bus.setNestedScrollingEnabled( false );

//        buslist = new ArrayList<>(  );


//        buslist.add( new AvailableBusesModel( "10:00","14:00","4 hr","800","Xyz Travels","Non-Ac Seater","24","","Mumbai","pune","11-1-2020","1","bussname1","mh 02 aj 0000","","1") );
//        buslist.add( new AvailableBusesModel( "13:00", "17:30","4 hr 30 min","1235","ABC Travels"," AC Sleeper","36","","Mumbai","pune","11-1-2020","1 break stop","busname2" ,"up 93 aj 8383","","3"));
//        buslist.add( new AvailableBusesModel( "15:00", "19:30","4 hr 30 min","1350","aabb Travels","Car","36","","jhansi","kanpur","12-1-2020","1 break stop","bussname3" ,"xy 11 aa 5555","","4") );
//        buslist.add( new AvailableBusesModel( "13:00", "18:30","5 hr 30 min","950","ccc Travels","Car","36","","jhansi","kanpur","12-1-2020","3","busname4","zz 22 rj 3333","","2" ) );


        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
        loadingBar = new ProgressDialog(this );
        loadingBar.setMessage( "loading" );

        btn_search.setOnClickListener( this );
        linear_from.setOnClickListener( this );
        linear_to.setOnClickListener( this );
        linear_date.setOnClickListener( this );
        rel_swap.setOnClickListener( this );

//        recycler_bus.addOnItemTouchListener( new RecyclerTouchListener( BookingActivity.this, recycler_bus, new RecyclerTouchListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                AvailableBusesModel model = buslist.get( position );
//                if (buslist.get( position ).getBusType().equalsIgnoreCase( "car" ))
//                {
//                    intent = new Intent( BookingActivity.this, PersonalBookingActivity.class );
//                }
//                else {
//
//                   intent = new Intent( BookingActivity.this, SelectSeatActivity.class );
//                }
//
//                intent.putExtra( "bus_type",model.getBusType() );
//                intent.putExtra( "bus_name",model.getBusName() );
//                intent.putExtra( "seats",model.getTotalSeats() );
//                intent.putExtra( "station_to",model.getTo_station() );
//                intent.putExtra( "station_from",model.getFrom_station() );
//                intent.putExtra( "end_time",model.getEndTime());
//                intent.putExtra( "start_time",model.getStartTime() );
//                intent.putExtra( "bus_no",model.getBus_number() );
//                intent.putExtra( "price",model.getTicketFare() );
//                intent.putExtra( "duration",model.getDuration() );
//                intent.putExtra( "bus_desc",model.getBusDesc() );
//                intent.putExtra( "agency_name",model.getAgencyName() );
//                intent.putExtra( "date",model.getDate());
//                intent.putExtra( "bus_image",model.getBus_img() );
//                intent.putExtra( "stops",model.getStops() );
//                intent.putExtra( "bus_id",model.getBus_id() );
//
//                startActivity( intent );
//
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        } ) {
//        } );
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnSearch)
        {
            String getfrom = txt_from.getText().toString();
            String getto = txt_to.getText().toString();
            String date = txt_date.getText().toString();
            if (radio_bus.isChecked())
            {
                vehicle_type = "bus";

            }
            else if (radio_car.isChecked())
            {
                vehicle_type ="car";
            }
            else if (radio_sharing.isChecked())
            {
                vehicle_type ="sharing";
            }
            else
            {
                vehicle_type="";
            }

            if (getfrom.isEmpty() || getfrom.equalsIgnoreCase( "from" ))
            {
                txt_from.setHint( "Enter Source Station" );
                txt_from.setHintTextColor( Color.RED );
                txt_from.requestFocus();
            }
            else if (getto.isEmpty() || getto.equalsIgnoreCase( "to" ))
            {
                txt_to.setHint( "Enter Destination Station" );
                txt_to.setHintTextColor( Color.RED );
                txt_to.requestFocus();
            }
            else if (date.isEmpty() || date.equalsIgnoreCase( "date" ))
            {
                txt_date.setHint( "Select Journey Date" );
                txt_date.setHintTextColor( Color.RED );
                txt_date.requestFocus();
            }
            else if (vehicle_type.equals(""))
            {
                Toast.makeText(BookingActivity.this, "Select type of Vehicle", Toast.LENGTH_SHORT).show();
            }
            else
            {

                Intent intent=new Intent(BookingActivity.this,AllBusActivity.class);
                intent.putExtra("source",getfrom);
                intent.putExtra("destination",getto);
                intent.putExtra("date",j_date);
                intent.putExtra("type",vehicle_type);
                startActivity(intent);
             //   makeSearchRequest( getfrom,getto,date);
                //makeBusesRequest();
            }



        }
        else  if (id == R.id.linear_to)
        {
            Bundle args = new Bundle(  );

            Intent intent = new Intent( BookingActivity.this,StationListActivity.class);
            intent.putExtra("type","to"  );
            startActivity( intent );

        }
        else if (id == R.id.linear_from)
        {
//            Bundle args = new Bundle(  );
//            args.putString("type","from"  );
            Intent intent = new Intent( BookingActivity.this,StationListActivity.class);
            intent.putExtra("type","from"  );
            startActivity( intent );
//
        }
        else if (id == R.id.linear_date)
        {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txt_date.setText(dayOfMonth + "-" + getMonthTwoDigit(String.valueOf(monthOfYear + 1)) + "-" + year);
                            j_date=year + "-" + getMonthTwoDigit(String.valueOf( monthOfYear + 1 )) + "-" + getMonthTwoDigit(String.valueOf( dayOfMonth));

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()+1000);
            datePickerDialog.show();

        }
        else if (id == R.id.rel_swap)
        {
            String getfrom = txt_from.getText().toString();
            String getto = txt_to.getText().toString();

            txt_from.setText( getto );
            txt_to.setText( getfrom );
        }

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
    }

