package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.Adapter.SeatAdapters.Seater2x2Adapter;
import in.binplus.travel.Adapter.SeatAdapters.Seater3x2Adapter;
import in.binplus.travel.Adapter.SeatAdapters.SemiSleeper2x2Adapter;
import in.binplus.travel.Adapter.SeatAdapters.SemiSleeper3x2Adapter;
import in.binplus.travel.Adapter.SeatAdapters.SleeperAdapter;
import in.binplus.travel.Adapter.SeatAdapters.SleeperSeaterAdapter;
import in.binplus.travel.Adapter.SeatAdapters.SleeperSeaterUpperAdapter;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.ToastMsg;

import static in.binplus.travel.Config.BaseURL.URL_VEHICLE_DETAILS_WID;

public class SelectSeatActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    TextView title ;
    ImageView img_bus;
    Dialog dialog;
    public static TextView txt_vehicle_name, tv_seats, tv_upr_seats;
    int birth_type = 0;
    ArrayList<String> upperSeatList=new ArrayList<>();
    ArrayList<String> lowerSeatList=new ArrayList<>();
    ArrayList<String> sleeperSeaterUpperSeatList=new ArrayList<>();
    ArrayList<String> sleeperSeaterLowerSeatList=new ArrayList<>();
    ArrayList<String> remainSeats=new ArrayList<>();
    ImageView img_info;
    Module module;
    String availabe_seats="";
    double seat_fare = 0;
    Button btn_lower, btn_upper;
    Activity activity = SelectSeatActivity.this;
    ProgressDialog loadingBar;
    RecyclerView rec_lower_seats, rec_upper_seats;
    Seater3x2Adapter seater3x2Adapter;
    Seater2x2Adapter seater2x2Adapter;
    SemiSleeper2x2Adapter semiSleeper2x2Adapter;
    SemiSleeper3x2Adapter semiSleeper3x2Adapter;
    SleeperAdapter sleeperAdapter;
    SleeperAdapter sleeperUpperAdapter;
    SleeperSeaterAdapter sleeperSeaterAdapter;
    SleeperSeaterUpperAdapter sleeperSeaterUpperAdapter;
    public static ArrayList<String> seat_list = new ArrayList<>();
    ArrayList<String> receiver_seat_list = new ArrayList<>();
    Button btn_ok;
    public static String bus_id = "";
    String layout = "", sitting_type = "";
    RelativeLayout rel_birth, rel_lower_seats, rel_upper_sheats;
    public static ArrayList<StopsModel> selected_seat_list;

    public static String id ,vehicle_type,vehicle_name ,total_seats,destination,source,end_time,start_time,bus_no,bus_desc,agency_name,date,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seat);
        initViews();


       bus_id=getIntent().getStringExtra( "id" );
       vehicle_type = getIntent().getStringExtra( "vehicle_type" );
       vehicle_name= getIntent().getStringExtra( "vehicle_name" );
       total_seats= getIntent().getStringExtra( "total_seats" );
       destination=getIntent().getStringExtra( "station_to");
       source= getIntent().getStringExtra( "station_from");
       end_time= getIntent().getStringExtra( "end_time");
       start_time=getIntent().getStringExtra( "start_time" );
       bus_no=getIntent().getStringExtra( "bus_no");
       price =getIntent().getStringExtra( "price" );
       bus_desc=getIntent().getStringExtra( "bus_desc");
       agency_name= getIntent().getStringExtra( "agency_name" );
       date= getIntent().getStringExtra( "date");
       getBusData(bus_id);

       txt_vehicle_name.setText( vehicle_name  );
//       title.setText(source +" - " +destination  );


    }

    private void initViews() {
        back = findViewById(R.id.back);
//        title = findViewById( R.id.title );
//        img_bus = findViewById(R.id.img_buses);
        txt_vehicle_name = findViewById(R.id.bus_detail);
        tv_seats = findViewById(R.id.tv_seats);
        img_info = findViewById(R.id.img_info);
        tv_upr_seats = findViewById(R.id.tv_upr_seats);
        loadingBar = new ProgressDialog(activity);
        loadingBar.setMessage("Loading...");
        loadingBar.setCanceledOnTouchOutside(false);
        module = new Module(activity);
        btn_lower = (Button) findViewById(R.id.btn_lower);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_upper = (Button) findViewById(R.id.btn_upper);
        bus_id = getIntent().getStringExtra("bus_id");
        rec_lower_seats = (RecyclerView) findViewById(R.id.rec_lower_seats);
        rec_upper_seats = (RecyclerView) findViewById(R.id.rec_upper_seats);
        rel_birth = (RelativeLayout) findViewById(R.id.rel_birth);
        rel_lower_seats = (RelativeLayout) findViewById(R.id.rel_lower_seats);
        rel_upper_sheats = (RelativeLayout) findViewById(R.id.rel_upper_sheats);

        getSupportActionBar();

        btn_lower.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_upper.setOnClickListener(this);
        back.setOnClickListener(this);
        img_info.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        seat_list.clear();
    }


    private void getBusData(String bus_id) {

        loadingBar.show();
        final String json_tag = "json_bus_details_id_tag";
        HashMap<String, String> params = new HashMap<>();
        params.put("vehicle_id", bus_id);
        params.put("date", date);

        CustomVolleyJsonRequest request = new CustomVolleyJsonRequest(Request.Method.POST, URL_VEHICLE_DETAILS_WID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingBar.dismiss();
                try {
                    Log.e("details-data", response.toString());
                    JSONArray array = response.getJSONArray("data");
                    JSONObject object = array.getJSONObject(0);
                    layout = object.getString("layout");
                    availabe_seats=String.valueOf( response.getInt("seats") );

                       JSONArray jsonArray=response.getJSONArray("seat_no" );
                       if(jsonArray.length()<=0)
                       {

                       }
                       else {
                           for (int i = 0; i < jsonArray.length(); i++) {
                               JSONObject object1 = jsonArray.getJSONObject( i );
                               remainSeats.add( object1.getString( "seat_no" ) );

                           }
                       }

                    sitting_type = object.getString("sitting_type");
                    int total_seats = Integer.parseInt(object.getString("total_seats"));
                    seat_fare = Double.parseDouble(object.getString("seat_fare"));

                    if (sitting_type.equalsIgnoreCase("Seater")) {
                        if (layout.equalsIgnoreCase("2X2")) {
                            int row = total_seats / 4;
                            seater2x2Adapter = new Seater2x2Adapter(activity, row,remainSeats);
                            rec_lower_seats.setLayoutManager(new LinearLayoutManager(activity));
                            rec_lower_seats.setAdapter(seater2x2Adapter);
                            seater2x2Adapter.notifyDataSetChanged();
                        } else if (layout.equalsIgnoreCase("3X2")) {
                            int row = total_seats / 5;
                            seater3x2Adapter = new Seater3x2Adapter(activity, row,remainSeats);
                            rec_lower_seats.setLayoutManager(new LinearLayoutManager(activity));
                            rec_lower_seats.setAdapter(seater3x2Adapter);
                            seater3x2Adapter.notifyDataSetChanged();
                        }
                    } else if (sitting_type.equalsIgnoreCase("Semi Sleeper")) {
                        if (layout.equalsIgnoreCase("2X2")) {
                            int row = total_seats / 4;
                            semiSleeper2x2Adapter = new SemiSleeper2x2Adapter(activity, row,remainSeats);
                            rec_lower_seats.setLayoutManager(new LinearLayoutManager(activity));
                            rec_lower_seats.setAdapter(semiSleeper2x2Adapter);
                            semiSleeper2x2Adapter.notifyDataSetChanged();
                        } else if (layout.equalsIgnoreCase("3X2")) {
                            int row = total_seats / 5;
                            semiSleeper3x2Adapter = new SemiSleeper3x2Adapter(activity, remainSeats,row);
                            rec_lower_seats.setLayoutManager(new LinearLayoutManager(activity));
                            rec_lower_seats.setAdapter(semiSleeper3x2Adapter);
                            semiSleeper3x2Adapter.notifyDataSetChanged();
                        }
                    } else if (sitting_type.equalsIgnoreCase("Sleeper")) {
                        if (rel_birth.getVisibility() == View.GONE) {
                            birth_type = 1;
                            rel_birth.setVisibility(View.VISIBLE);
                            btn_lower.setBackgroundColor(getResources().getColor(R.color.color_cancel));
                            btn_lower.setTextColor(getResources().getColor(R.color.white));
                        }

                        int row = total_seats / 3;
                        sleeperAdapter = new SleeperAdapter(activity, row, "L",remainSeats);
                        sleeperUpperAdapter = new SleeperAdapter(activity, row, "U",remainSeats);
                        rec_lower_seats.setLayoutManager(new LinearLayoutManager(activity));
                        rec_upper_seats.setLayoutManager(new LinearLayoutManager(activity));
                        rec_lower_seats.setAdapter(sleeperAdapter);
                        rec_upper_seats.setAdapter(sleeperUpperAdapter);
                        sleeperUpperAdapter.notifyDataSetChanged();
                        sleeperAdapter.notifyDataSetChanged();
                    }
                    else if(sitting_type.equalsIgnoreCase("Sleeper+Seater"))
                    {
                        if (rel_birth.getVisibility() == View.GONE) {
                            birth_type = 1;
                            rel_birth.setVisibility(View.VISIBLE);
                            btn_lower.setBackgroundColor(getResources().getColor(R.color.color_cancel));
                            btn_lower.setTextColor(getResources().getColor(R.color.white));
                        }

                        int row = total_seats / 3;
                        sleeperSeaterAdapter = new SleeperSeaterAdapter(activity, row, "L",remainSeats);
                        sleeperSeaterUpperAdapter = new SleeperSeaterUpperAdapter(activity, row, remainSeats);
                        rec_lower_seats.setLayoutManager(new LinearLayoutManager(activity));
                        rec_upper_seats.setLayoutManager(new LinearLayoutManager(activity));
                        rec_lower_seats.setAdapter(sleeperSeaterAdapter);
                        rec_upper_seats.setAdapter(sleeperSeaterUpperAdapter);
                        sleeperSeaterAdapter.notifyDataSetChanged();
                        sleeperSeaterUpperAdapter.notifyDataSetChanged();

                    }


                    // new ToastMsg(getActivity()).toastIconSuccess("done");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    new ToastMsg(activity).toastIconError(ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                String msg = module.VolleyErrorMessage(error);
                if (!(msg.isEmpty() || msg.equals(""))) {
                    new ToastMsg(activity).toastIconError(msg);
                    //Toast.makeText(getActivity(),""+msg.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        AppController.getInstance().addToRequestQueue(request, json_tag);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_lower) {
            if (birth_type != 1) {
                btn_lower.setBackgroundColor(getResources().getColor(R.color.color_cancel));
                btn_lower.setTextColor(getResources().getColor(R.color.white));
                btn_upper.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_upper.setTextColor(getResources().getColor(R.color.dark_black));

                birth_type = 1;
                if (rel_upper_sheats.getVisibility() == View.VISIBLE) {
                    rel_upper_sheats.setVisibility(View.GONE);
                    rel_lower_seats.setVisibility(View.VISIBLE);
                }

            }

        } else if (id == R.id.btn_upper) {
            if (birth_type != 2) {
                birth_type = 2;
                btn_upper.setBackgroundColor(getResources().getColor(R.color.color_cancel));
                btn_upper.setTextColor(getResources().getColor(R.color.white));
                btn_lower.setBackgroundColor(getResources().getColor(R.color.gray));
                btn_lower.setTextColor(getResources().getColor(R.color.dark_black));

                if (rel_lower_seats.getVisibility() == View.VISIBLE) {
                    rel_lower_seats.setVisibility(View.GONE);
                    rel_upper_sheats.setVisibility(View.VISIBLE);
                }
            }
        } else if (id == R.id.btn_ok) {

                if (sitting_type.equalsIgnoreCase("Seater")) {
                    if (layout.equalsIgnoreCase("2X2")) {
                        seat_list = seater2x2Adapter.getSeatList();

                    } else if (layout.equalsIgnoreCase("3X2")) {
                        seat_list = seater3x2Adapter.getSeatList();

                    }
                } else if (sitting_type.equalsIgnoreCase("Semi Sleeper")) {
                    if (layout.equalsIgnoreCase("2X2")) {
                        seat_list = semiSleeper2x2Adapter.getSeatList();

                    } else if (layout.equalsIgnoreCase("3X2")) {
                        seat_list = semiSleeper3x2Adapter.getSeatList();

                    }
                } else if (sitting_type.equalsIgnoreCase("Sleeper")) {
                    seat_list.clear();

                   upperSeatList = sleeperUpperAdapter.getSeatList();
                    lowerSeatList = sleeperAdapter.getSeatList();

                    seat_list.addAll(lowerSeatList);
                    seat_list.addAll(upperSeatList);

                }else if (sitting_type.equalsIgnoreCase("Sleeper+Seater")) {
                    seat_list.clear();

                   sleeperSeaterLowerSeatList = sleeperSeaterAdapter.getSeatList();
                    sleeperSeaterUpperSeatList = sleeperSeaterUpperAdapter.getSeatList();

                    seat_list.addAll(sleeperSeaterLowerSeatList);
                    seat_list.addAll(sleeperSeaterUpperSeatList);

//                }


                }

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < seat_list.size(); i++) {
                    builder = builder.append(seat_list.get(i).toString() + ",");
                }
            if(seat_list.size()<=0)
            {
                new ToastMsg(activity).toastIconError("First Select any seat for booking");
            }
            else {
                if(sitting_type.equalsIgnoreCase("Sleeper"))
                {
                   if(upperSeatList.size()<=0 && lowerSeatList.size()<=0)
                   {
                       new ToastMsg(activity).toastIconError("First Select any seat for booking");
                   }
                   else
                   {
//                       Toast.makeText(SelectSeatActivity.this,"fare : "+seat_fare,Toast.LENGTH_LONG).show();
//                       Intent intent = new Intent(activity, AddPassengerDetails.class);
                       Intent intent = new Intent(activity, SelectBoardingPoint.class);
                       intent.putExtra( "seat_fare",String.valueOf( seat_fare ));
                       intent.putExtra( "date",String.valueOf( date ));
                       intent.putExtra( "source",source );
                       intent.putExtra( "destination",destination );
                       startActivity(intent);

                   }
                }
                else {
//                    Toast.makeText(SelectSeatActivity.this,"fare : "+seat_fare,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity,SelectBoardingPoint.class);
                    intent.putExtra( "seat_fare",String.valueOf( seat_fare) );
                    intent.putExtra( "date",String.valueOf( date) );
                    intent.putExtra( "source",source );
                    intent.putExtra( "destination",destination );
                    startActivity(intent);
                }

                // new ToastMsg(activity).toastIconSuccess(String.valueOf(builder.toString()));
            }
        } else if (id == R.id.back) {
            finish();
        }
        else if( id == R.id.img_info)
        {
            createInfoDialog();
        }
    }

    private void createInfoDialog() {

        dialog=new Dialog(SelectSeatActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_seat_info);
        dialog.show();

        ImageView seat_close=(ImageView)dialog.findViewById(R.id.seat_close);
        seat_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        // unregister reciver
        unregisterReceiver(mCart);
    }

    @Override
    public void onResume() {
        super.onResume();
        // register reciver
        registerReceiver(mCart, new IntentFilter("Seats"));
    }

    private BroadcastReceiver mCart = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            ArrayList<String> type = (ArrayList<String>) intent.getSerializableExtra("type");

            if (type.size() <= 0) {

                if(sitting_type.equalsIgnoreCase("Sleeper"))
                {
                    if(upperSeatList.size()<=0)
                    {
                        tv_upr_seats.setVisibility(View.GONE);
                    }
                    else
                    {
                        tv_upr_seats.setVisibility(View.VISIBLE);
                    }
                     if(lowerSeatList.size()<=0)
                    {
                        tv_seats.setVisibility(View.GONE);
                    }
                    else
                    {
                        tv_seats.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    if(sitting_type.equalsIgnoreCase("Sleeper")) {
                        if (upperSeatList.size() <= 0) {
                            tv_upr_seats.setVisibility(View.GONE);
                        } else {
                            tv_upr_seats.setVisibility(View.VISIBLE);
                        }
                        if (lowerSeatList.size() <= 0) {
                            tv_seats.setVisibility(View.GONE);
                        } else {
                            tv_seats.setVisibility(View.VISIBLE);
                        }
                    }
                    else {

                        tv_upr_seats.setVisibility(View.GONE);
                        tv_seats.setVisibility(View.GONE);
                    }
                    }

            } else {

                tv_seats.setVisibility(View.VISIBLE);
                tv_upr_seats.setVisibility(View.VISIBLE);
                updateData(type);
            }
        }
    };

    private void updateData(ArrayList<String> type) {
        receiver_seat_list.clear();
        receiver_seat_list.addAll(type);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < receiver_seat_list.size(); i++) {
                builder = builder.append(receiver_seat_list.get(i).toString() + ",");
            }

            if (birth_type == 2) {
                if (sitting_type.equalsIgnoreCase("Sleeper")) {
                    tv_upr_seats.setVisibility(View.VISIBLE);
                    tv_upr_seats.setText("Upper Seats :" + builder);
                }

            } else {
                if (sitting_type.equalsIgnoreCase("Sleeper")) {
                    tv_seats.setVisibility(View.VISIBLE);
                    tv_seats.setText("Lower Seats :" + builder);
                } else {
                    tv_seats.setVisibility(View.VISIBLE);
                    tv_seats.setText("Seats :" + builder);
                }
            }



    }
}



