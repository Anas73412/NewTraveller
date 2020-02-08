package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.binplus.travel.Adapter.AvailableBusesAdapter;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.AvailableBusesModel;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.RecyclerTouchListener;
import in.binplus.travel.util.ToastMsg;

public class AllBusActivity extends AppCompatActivity implements View.OnClickListener{
RecyclerView rec_buses;
TextView toolbar_title ;
ImageView back ;
int list_type=0;
List<TextView> filter_list;
    Intent intent ;
LinearLayout lin_no_bus,lin_filter;
String source="",destination="",date="" , type ="";
ProgressDialog loadingBar;
TextView tv_filter_all,tv_filter_ac,tv_filter_nonac,tv_filter_seater,tv_filter_sleeper,tv_filter_semi_sleeper;
LinearLayout linear_all , linear_ac,linear_nonac ,linear_sleeper,linear_seater ,linear_semi_sleeper ;
ImageView img_all ,img_ac,img_non,img_seater ,img_sleeper ,img_semi_sleeper ;
ArrayList<AvailableBusesModel> list;
ArrayList<AvailableBusesModel> ac_list;
ArrayList<AvailableBusesModel> non_ac_list;
ArrayList<AvailableBusesModel> seater_list;
ArrayList<AvailableBusesModel> sleeper_list;
ArrayList<AvailableBusesModel> semi_sleeper_list;
Context ctx=AllBusActivity.this;
AvailableBusesAdapter adapter;
Module module;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_bus);
     filter_list=new ArrayList<>();
        initViews();

//
//        date = getIntent().getStringExtra( "date" );
//        source= getIntent().getStringExtra( "source" );
//        destination=getIntent().getStringExtra( "destination" );
//        type = getIntent().getStringExtra("type");




        rec_buses.addOnItemTouchListener(new RecyclerTouchListener(ctx, rec_buses, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                AvailableBusesModel model=null;

                if(list_type==0)
                {
                    model=new AvailableBusesModel();
                    model=list.get(position);
                }
                else if(list_type == 1)
                {
                    model=new AvailableBusesModel();
                    model=ac_list.get(position);
                }else if(list_type == 2)
                {
                    model=new AvailableBusesModel();
                    model=non_ac_list.get(position);
                }else if(list_type == 3)
                {
                    model=new AvailableBusesModel();
                    model=seater_list.get(position);
                }else if(list_type == 3)
                {
                    model=new AvailableBusesModel();
                    model=sleeper_list.get(position);
                }
                else
                {
                    model=new AvailableBusesModel();
                    model=semi_sleeper_list.get(position);
                }

                if (model.getVehicle_type().equalsIgnoreCase( "car" ))
                {
                    intent = new Intent( ctx, PersonalBookingActivity.class );
                   // new ToastMsg(ctx).toastIconSuccess("car"+"\n"+list_type+"\n"+model.getCompany_name());
                }
                else {
                 //   new ToastMsg(ctx).toastIconSuccess("not car"+"\n"+list_type+"\n"+model.getCompany_name());
                   intent = new Intent( ctx, SelectSeatActivity.class );
                   intent.putExtra("id",model.getId());
                    intent.putExtra( "vehicle_type",model.getVehicle_type() );
                intent.putExtra( "vehicle_name",model.getVehicle_name() );
                intent.putExtra( "total_seats",model.getTotal_seats() );
                intent.putExtra( "station_to",destination);
                intent.putExtra( "station_from",source);
                intent.putExtra( "end_time",model.getTo_time());
                intent.putExtra( "start_time",model.getFrom_time() );
                intent.putExtra( "bus_no",model.getRegistration_no() );
                intent.putExtra( "price",model.getSeat_fare() );
//                intent.putExtra( "duration",model.getDuration() );
                intent.putExtra( "bus_desc",model.getVeh_description() );
                intent.putExtra( "agency_name",model.getCompany_name() );
                intent.putExtra( "date",date);
//                intent.putExtra( "bus_image",model.get );
//                intent.putExtra( "stops",model.getStops() );
//                intent.putExtra( "bus_id",model.getBus_id() );

                }
                startActivity(intent);


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingBar = new ProgressDialog( AllBusActivity.this );
        loadingBar.setMessage( "Loading" );
        loadingBar.setCancelable(false);
        module=new Module(ctx);
        back = findViewById( R.id.back );
        toolbar_title = findViewById( R.id.title );
        rec_buses=(RecyclerView)findViewById(R.id.rec_buses);
        lin_no_bus=(LinearLayout)findViewById(R.id.lin_no_bus);
        lin_filter=(LinearLayout)findViewById(R.id.lin_filter);
        tv_filter_all=(TextView) findViewById(R.id.tv_filter_all);
        tv_filter_ac=(TextView) findViewById(R.id.tv_filter_ac);
        tv_filter_nonac=(TextView) findViewById(R.id.tv_filter_nonac);
        tv_filter_seater=(TextView) findViewById(R.id.tv_filter_seater);
        tv_filter_sleeper=(TextView) findViewById(R.id.tv_filter_sleeper);
        tv_filter_semi_sleeper=(TextView) findViewById(R.id.tv_filter_semi_sleeper);
        linear_all = findViewById( R.id.linear_all );
        linear_ac = findViewById( R.id.linear_ac );
        linear_nonac = findViewById( R.id.linear_nonac );
        linear_seater = findViewById( R.id.linear_seater );
        linear_sleeper = findViewById( R.id.linear_sleeper );
        linear_semi_sleeper = findViewById( R.id.linear_semi_sleeper );
        img_ac = findViewById( R.id.img_ac );
        img_non = findViewById( R.id.img_nonac );
        img_all = findViewById( R.id.img_all );
        img_seater = findViewById( R.id.img_seater );
        img_sleeper = findViewById( R.id.img_sleeper );
        img_semi_sleeper = findViewById( R.id.img_semi_sleeper );
        back = findViewById( R.id.back );
        list=new ArrayList<>();
        ac_list=new ArrayList<>();
        non_ac_list=new ArrayList<>();
        seater_list=new ArrayList<>();
        sleeper_list=new ArrayList<>();
        semi_sleeper_list=new ArrayList<>();
        filter_list.add(tv_filter_all);
        filter_list.add(tv_filter_ac);
        filter_list.add(tv_filter_nonac);
        filter_list.add(tv_filter_seater);
        filter_list.add(tv_filter_sleeper);
        filter_list.add(tv_filter_semi_sleeper);
        source=getIntent().getStringExtra("source");
        destination=getIntent().getStringExtra("destination");
        date=getIntent().getStringExtra("date");
        type = getIntent().getStringExtra("type");
      //  getSupportActionBar().setTitle(source +" - "+destination);
          getBusLists(source,destination,type);

          toolbar_title.setText( source +" - "+destination );
          back.setOnClickListener( this);
          if (type.equalsIgnoreCase("car") || type.equalsIgnoreCase("sharing"))
        {
            linear_seater.setVisibility(View.GONE);
            linear_semi_sleeper.setVisibility(View.GONE);
            linear_sleeper.setVisibility(View.GONE);
        }
        else
        {
//            linear_seater.setVisibility(View.VISIBLE);
//            linear_semi_sleeper.setVisibility(View.VISIBLE);
//            linear_sleeper.setVisibility(View.VISIBLE);
        }

         linear_all.setOnClickListener(this);
        linear_ac.setOnClickListener(this);
        linear_nonac.setOnClickListener(this);
        linear_seater.setOnClickListener(this);
        linear_sleeper.setOnClickListener(this);
        linear_semi_sleeper.setOnClickListener(this);


    }

    private void getBusLists(final String source, String destination ,String type) {
        loadingBar.show();

        String json_tag="json_bus_lists";
        HashMap<String,String> map=new HashMap<>();
        map.put("from",source);
        map.put("to",destination);
        map.put("type",type);
        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST,BaseURL.GET_ALL_VEHICLE_LISTS,map ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("vehicles-data",response.toString());
                loadingBar.dismiss();
             try
             {
                 boolean resp=response.getBoolean("status");
                 if(resp)
                 {

                     JSONArray array=response.getJSONArray("data");
                     if(array.length()<=0)
                     {
                         lin_no_bus.setVisibility(View.VISIBLE);
                         rec_buses.setVisibility(View.GONE);
                         lin_filter.setVisibility(View.GONE);

                     }
                     else
                     {
                         if(array.length()==1)
                         {
                             lin_filter.setVisibility(View.GONE);
                         }
                         lin_no_bus.setVisibility(View.GONE);
                         rec_buses.setVisibility(View.VISIBLE);


                         for(int i=0;i<array.length();i++)
                     {
                         AvailableBusesModel model=new AvailableBusesModel();
                         JSONObject object=array.getJSONObject(i);

                         model.setId(object.getString("id"));
//                         model.setOwner_name(object.getString("owner_name"));
//                         model.setCompany_name(object.getString("company_name"));
                         model.setRegistration_no(object.getString("registration_no"));
                         model.setChechis_number(object.getString("chechis_number"));
                         model.setVeh_model(object.getString("veh_model"));
                         model.setVehicle_type(object.getString("vehicle_type"));
                         model.setSitting_type(object.getString("sitting_type"));
                         model.setVehicle_name(object.getString("vehicle_name"));
                         model.setVeh_description(object.getString("veh_description"));
                         model.setStatus(object.getString("status"));
                         model.setV_type(object.getString("v_type"));
                         model.setVehicles_path(object.getString("vehicles_path"));
                     //    model.setVehicles_end(object.getString("vehicles_end"));
                         model.setFrom_time(object.getString("from_time"));
                         model.setTo_time(object.getString("to_time"));
                         model.setTotal_seats(object.getString("total_seats"));
                         model.setSeat_fare(object.getString("seat_fare"));

                         String sitting_type=object.getString("sitting_type");
                         String vehicle_type=object.getString("vehicle_type");

                         if(sitting_type.equalsIgnoreCase("Sleeper"))
                         {
                             sleeper_list.add(model);
                         }
                         else if(sitting_type.equalsIgnoreCase("Seater"))
                         {
                             seater_list.add(model);
                         }
                         else
                         {
                             semi_sleeper_list.add(model);
                         }
                         if(vehicle_type.equalsIgnoreCase("A/C"))
                         {
                             ac_list.add(model);
                         }
                         else
                         {
                             non_ac_list.add(model);
                         }
//                         tv_filter_all.setVisibility(View.VISIBLE);
//                         if(!(sleeper_list.size()<=0))
//                         {
//                             tv_filter_sleeper.setVisibility(View.VISIBLE);
//                         }
//                         if(!(seater_list.size()<=0))
//                         {
//                             tv_filter_seater.setVisibility(View.VISIBLE);
//                         }
//                         if(!(non_ac_list.size()<=0))
//                         {
//                             tv_filter_nonac.setVisibility(View.VISIBLE);
//                         }
//                         if(!(ac_list.size()<=0))
//                         {
//                             tv_filter_ac.setVisibility(View.VISIBLE);
//                         }
//                         if(!(semi_sleeper_list.size()<=0))
//                         {
//                             tv_filter_semi_sleeper.setVisibility(View.VISIBLE);
//                         }
                         list.add(model);

                     }
                     adapter=new AvailableBusesAdapter(list,AllBusActivity.this);

                     rec_buses.setLayoutManager(new LinearLayoutManager(ctx));
                     rec_buses.setAdapter(adapter);
                     adapter.notifyDataSetChanged();
                     }
                 }
                 else
                 {
                  new ToastMsg(ctx).toastIconError(response.getString("message"));
                 }
             }
             catch (Exception ex)
             {
                 ex.printStackTrace();
                 new ToastMsg(ctx).toastIconError(ex.getMessage());

             }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                String msg=module.VolleyErrorMessage(error);
                if(!(msg.isEmpty() || msg.equals("")))
                {
                    new ToastMsg(ctx).toastIconError(msg);
                    //Toast.makeText(getActivity(),""+msg.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);

    }


    @Override
    public void onClick(View v) {

        int id=v.getId();
        if(id == R.id. linear_all)
        {
           img_all.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.orange ) ) );
           img_semi_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
           img_seater.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
           img_ac.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
           img_non.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
           img_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
           tv_filter_all .setTextColor( AllBusActivity.this.getResources().getColor( R.color.orange ) );
           tv_filter_ac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
           tv_filter_nonac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
           tv_filter_sleeper .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
           tv_filter_seater .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
           tv_filter_semi_sleeper.setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            list_type=0;
//            setFilerDrawable(tv_filter_all);

            if (list.size()==0)
            {
                lin_no_bus.setVisibility( View.VISIBLE );
                rec_buses.setVisibility( View.GONE );
            }
            else {
                lin_no_bus.setVisibility( View.GONE );
                rec_buses.setVisibility( View.VISIBLE);
                adapter = new AvailableBusesAdapter( list, AllBusActivity.this );

                rec_buses.setLayoutManager( new LinearLayoutManager( ctx ) );
                rec_buses.setAdapter( adapter );
                adapter.notifyDataSetChanged();
            }
        }
        else if(id == R.id. linear_ac)
        {
            img_all.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) ) );
            img_semi_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_seater.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_ac.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.orange ) )  );
            img_non.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            tv_filter_all .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white) );
            tv_filter_ac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.orange ) );
            tv_filter_nonac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_sleeper .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_seater .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_semi_sleeper.setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            list_type=1;
//            setFilerDrawable(tv_filter_ac);

            if (ac_list.size()==0)
            {
                lin_no_bus.setVisibility( View.VISIBLE );
                rec_buses.setVisibility( View.GONE );
            }
            else {
                lin_no_bus.setVisibility( View.GONE );
                rec_buses.setVisibility( View.VISIBLE);
                adapter = new AvailableBusesAdapter( ac_list, AllBusActivity.this );
                rec_buses.setLayoutManager( new LinearLayoutManager( ctx ) );
                rec_buses.setAdapter( adapter );
                adapter.notifyDataSetChanged();
            }
        }else if(id == R.id. linear_nonac)
        {
            img_all.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) ) );
            img_semi_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_seater.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_ac.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_non.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.orange ) )  );
            img_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            tv_filter_all .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white) );
            tv_filter_ac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_nonac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.orange ) );
            tv_filter_sleeper .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_seater .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_semi_sleeper.setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            list_type=2;
//            setFilerDrawable(tv_filter_nonac);
            if (non_ac_list.size()==0)
            {
                lin_no_bus.setVisibility( View.VISIBLE );
                rec_buses.setVisibility( View.GONE );
            }
            else {
                lin_no_bus.setVisibility( View.GONE );
                rec_buses.setVisibility( View.VISIBLE);
                adapter = new AvailableBusesAdapter( non_ac_list, AllBusActivity.this );
                rec_buses.setLayoutManager( new LinearLayoutManager( ctx ) );
                rec_buses.setAdapter( adapter );
                adapter.notifyDataSetChanged();
            }
        }else if(id == R.id. linear_seater)
        {
            img_all.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) ) );
            img_semi_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_seater.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.orange ) )  );
            img_ac.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_non.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            tv_filter_all .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white) );
            tv_filter_ac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_nonac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_sleeper .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_seater .setTextColor( AllBusActivity.this.getResources().getColor( R.color.orange ) );
            tv_filter_semi_sleeper.setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );

            list_type=3;
//            setFilerDrawable(tv_filter_seater);
            if (seater_list.size()==0)
            {
                lin_no_bus.setVisibility( View.VISIBLE );
                rec_buses.setVisibility( View.GONE );
            }
            else {
                lin_no_bus.setVisibility( View.GONE );
                rec_buses.setVisibility( View.VISIBLE);
                adapter = new AvailableBusesAdapter( seater_list, AllBusActivity.this );

                rec_buses.setLayoutManager( new LinearLayoutManager( ctx ) );
                rec_buses.setAdapter( adapter );
                adapter.notifyDataSetChanged();
            }
        }else if(id == R.id. linear_sleeper)
        {
            img_all.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) ) );
            img_semi_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_seater.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_ac.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_non.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.orange ) )  );
            tv_filter_all .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_ac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_nonac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_sleeper .setTextColor( AllBusActivity.this.getResources().getColor( R.color.orange ) );
            tv_filter_seater .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_semi_sleeper.setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            list_type=4;
//            setFilerDrawable(tv_filter_sleeper);
            if (sleeper_list.size()==0)
            {
                lin_no_bus.setVisibility( View.VISIBLE );
                rec_buses.setVisibility( View.GONE );
            }
            else {
                lin_no_bus.setVisibility( View.GONE );
                rec_buses.setVisibility( View.VISIBLE);
                adapter = new AvailableBusesAdapter( sleeper_list, AllBusActivity.this );

                rec_buses.setLayoutManager( new LinearLayoutManager( ctx ) );
                rec_buses.setAdapter( adapter );
                adapter.notifyDataSetChanged();
            }
        }else if(id == R.id. linear_semi_sleeper)
        {
            img_all.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white) ) );
            img_semi_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.orange ) )  );
            img_seater.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_ac.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_non.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            img_sleeper.setImageTintList( ColorStateList.valueOf( AllBusActivity.this.getResources().getColor( R.color.white ) )  );
            tv_filter_all .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_ac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_nonac .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_sleeper .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_seater .setTextColor( AllBusActivity.this.getResources().getColor( R.color.white ) );
            tv_filter_semi_sleeper.setTextColor( AllBusActivity.this.getResources().getColor( R.color.orange ) );
            list_type=5;
//            setFilerDrawable(tv_filter_semi_sleeper);

            if (semi_sleeper_list.size()==0)
            {
                lin_no_bus.setVisibility( View.VISIBLE );
                rec_buses.setVisibility( View.GONE );
            }
            else {
                lin_no_bus.setVisibility( View.GONE );
                rec_buses.setVisibility( View.VISIBLE);
                adapter = new AvailableBusesAdapter( semi_sleeper_list, AllBusActivity.this );

                rec_buses.setLayoutManager( new LinearLayoutManager( ctx ) );
                rec_buses.setAdapter( adapter );
                adapter.notifyDataSetChanged();
            }
        }


        else if (id == R.id.back)
        {
            finish();
        }

    }

//    public void setFilerDrawable(TextView view)
//    {
//         for(int i=0; i<filter_list.size();i++)
//         {
//             if(filter_list.get(i).getId() == view.getId())
//             {
//                 view.setBackgroundResource(R.drawable.selected_card_layout);
//                 view.setTextColor(getResources().getColor(R.color.white));
//             }
//             else
//             {
//                 filter_list.get(i).setBackgroundResource(R.drawable.border_card_layout);
//                 filter_list.get(i).setTextColor(getResources().getColor(R.color.colorPrimary));
//             }
//         }
//    }
}
