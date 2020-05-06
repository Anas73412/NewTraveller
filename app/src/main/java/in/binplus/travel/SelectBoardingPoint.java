package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import java.util.Iterator;

import in.binplus.travel.Adapter.BoardingPointsAdapter;
import in.binplus.travel.Adapter.DropingPointsAdapter;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.BoardingModel;
import in.binplus.travel.Model.BoardingPointModel;
import in.binplus.travel.Model.BookingDetailsModel;
import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.ToastMsg;

public class SelectBoardingPoint extends AppCompatActivity {
    TextView txt_title ;
    ProgressDialog loadingBar ;
    ImageView back ;
    Module module;
    int flag=0;
    Activity ctx=SelectBoardingPoint.this;
    Button btn_next;
    DropingPointsAdapter drop_adapter;
    BoardingPointsAdapter adapter;
    RecyclerView recycler_boarding , recycler_droping ;
    RelativeLayout rel_board ,rel_drop ;
    String source,destination ,date ,seat_fare ,v_type;
    View view_board ,view_drop ;
    ArrayList<BoardingModel> list;
    ArrayList<BoardingPointModel> board_list ;
    ArrayList<BoardingModel> drop_list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_boarding_point );
        loadingBar = new ProgressDialog( ctx );
        loadingBar.setMessage( "Loading" );
        loadingBar.setCancelable(false);
        rel_board = findViewById( R.id.tab_board );
        rel_drop = findViewById( R.id.tab_drop );
        txt_title = findViewById( R.id.title );
        recycler_boarding = findViewById( R.id.recycler_boarding_point );
        recycler_droping = findViewById( R.id.recycler_dropping_point );
        view_board = findViewById( R.id.view_b );
        view_drop = findViewById( R.id.view_d );
        back = findViewById( R.id.back );
        btn_next = (Button) findViewById( R.id.btn_next );
        module=new Module(ctx);
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );



        source = getIntent().getStringExtra( "source" );
        destination =getIntent().getStringExtra( "destination" );
        date = getIntent().getStringExtra( "date" );
        seat_fare = getIntent().getStringExtra( "seat_fare" );
        v_type = getIntent().getStringExtra( "v_type" );
        board_list = new ArrayList<>(  );
        drop_list = new ArrayList<>(  );
        list=new ArrayList<>();

        txt_title.setText(source +"-"+destination );
        recycler_boarding.setVisibility( View.VISIBLE );
        getBoardPoints();

        rel_board.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view_board.getVisibility() ==View.INVISIBLE)
                {
                    view_board.setVisibility(View.VISIBLE);
                }
                if(view_drop.getVisibility() == View.VISIBLE)
                {
                    view_drop.setVisibility(View.INVISIBLE);

                }
                flag=1;
                recycler_droping.setVisibility( View.GONE);
                recycler_boarding.setVisibility( View.VISIBLE );

//

            }
        } );

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String boardLocation=adapter.getBoardingLocation();
                String dropLoaction=drop_adapter.getDropingLocation();
                if(boardLocation.equals("") || boardLocation.isEmpty())
                {
                   new ToastMsg(ctx).toastIconError("Select Boarding Location");
                   if(flag == 0 || flag == 1)
                   {

                   }
                   else
                   {
                           rel_board.performClick();
                   }
                }
                else if(dropLoaction.equals(""))
                {
                    new ToastMsg(ctx).toastIconError("Select Droping Location");
                    if(flag ==2)
                    {

                    }
                    else
                    {
                        rel_drop.performClick();
                    }
                }
                else
                {
                    Intent  intent = new Intent( SelectBoardingPoint.this,AddPassengerDetails.class);
                intent.putExtra( "seat_fare",String.valueOf( seat_fare ));
                intent.putExtra( "date",String.valueOf( date ));
                intent.putExtra( "source",source );
                intent.putExtra( "destination",destination );
                intent.putExtra( "start_from",SelectSeatActivity.start_time );
                intent.putExtra( "end_to",SelectSeatActivity.end_time );
                intent.putExtra( "v_type",v_type );
                intent.putExtra( "v_id",SelectSeatActivity.bus_id);
                intent.putExtra("board",boardLocation);
                intent.putExtra("drop",dropLoaction);

                startActivity( intent );
                   // new ToastMsg(ctx).toastIconSuccess("b- "+boardLocation+"\n d-  "+dropLoaction);
                }


            }
        });


        rel_drop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view_board.getVisibility() ==View.VISIBLE)
                {
                    view_board.setVisibility(View.INVISIBLE);
                }
                if(view_drop.getVisibility() == View.INVISIBLE)
                {
                    view_drop.setVisibility(View.VISIBLE);

                }
                flag=2;
                recycler_droping.setVisibility( View.VISIBLE);
                recycler_boarding.setVisibility( View.GONE );
            }
        }  );


    }

    public void getBoardPoints ( )
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "to",destination );
        params.put( "from",source );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_DROP_POINTS, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e( "drop points",response.toString() );
                        loadingBar.dismiss();
//                        Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                        try {
                            Boolean status = response.getBoolean( "responce" );
                            if (status)
                            {
                                JSONObject data = (JSONObject) response.get( "data" );

                                // From Boarding Points
                                list.clear();
                                JSONArray array_from = data.getJSONArray( "from" );
                                JSONObject obj_from=array_from.getJSONObject(0);
                                JSONArray from_board_arr=new JSONArray(obj_from.getString("bus_board"));
                                for(int i=0;i<from_board_arr.length();i++)
                                {
                                    JSONObject f_board_obj=from_board_arr.getJSONObject(i);
                                    Iterator iterator=f_board_obj.keys();
                                    String key="";
                                    String value="";
                                    while (iterator.hasNext())
                                    {
                                         key=(String)iterator.next();

                                          value=f_board_obj.getString(key);


                                    }
                                    BoardingModel boardingModel=new BoardingModel();
                                    boardingModel.setLocation(key);
                                    boardingModel.setTime(value);

                                    list.add(boardingModel);
                                }
                               // To Droping Points
                                drop_list.clear();
                                JSONArray array_to = data.getJSONArray( "to" );
                                JSONObject obj_to=array_to.getJSONObject(0);
                                JSONArray to_board_arr=new JSONArray(obj_to.getString("bus_drops"));
                                for(int i=0;i<to_board_arr.length();i++)
                                {
                                    JSONObject t_board_obj=to_board_arr.getJSONObject(i);
                                    Iterator iterator=t_board_obj.keys();
                                    String key="";
                                    String value="";
                                    while (iterator.hasNext())
                                    {
                                        key=(String)iterator.next();

                                        value=t_board_obj.getString(key);


                                    }
                                    BoardingModel boardingModel=new BoardingModel();
                                    boardingModel.setLocation(key);
                                    boardingModel.setTime(value);

                                    drop_list.add(boardingModel);
                                }

                                adapter=new BoardingPointsAdapter(SelectBoardingPoint.this,list);
                                recycler_boarding.setLayoutManager(new LinearLayoutManager(SelectBoardingPoint.this));
                                recycler_boarding.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                 drop_adapter=new DropingPointsAdapter(SelectBoardingPoint.this,drop_list);
                                recycler_droping.setLayoutManager(new LinearLayoutManager(SelectBoardingPoint.this));
                                recycler_droping.setAdapter(drop_adapter);
                                drop_adapter.notifyDataSetChanged();

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
                        if(!(msg.equals("") || msg.isEmpty()))
                        {
                            new ToastMsg(ctx).toastIconError(msg);
                        }

                    }
                } );
        AppController.getInstance().addToRequestQueue(jsonRequest,"drop points" );
    }


}
