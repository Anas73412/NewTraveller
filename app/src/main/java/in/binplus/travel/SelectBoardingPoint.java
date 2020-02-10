package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Model.BoardingPointModel;
import in.binplus.travel.Model.BookingDetailsModel;
import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.util.CustomVolleyJsonRequest;

public class SelectBoardingPoint extends AppCompatActivity {
    TextView txt_title ;
    ImageView back ;
    RecyclerView recycler_boarding , recycler_droping ;
    RelativeLayout rel_board ,rel_drop ;
    String source,destination ,date ,seat_fare ;
    View view_board ,view_drop ;
    ArrayList<BoardingPointModel> board_list ;
    ArrayList<BoardingPointModel> drop_list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_boarding_point );
        rel_board = findViewById( R.id.tab_board );
        rel_drop = findViewById( R.id.tab_drop );
        txt_title = findViewById( R.id.title );
        recycler_boarding = findViewById( R.id.recycler_boarding_point );
        recycler_droping = findViewById( R.id.recycler_dropping_point );
        view_board = findViewById( R.id.view_b );
        view_drop = findViewById( R.id.view_d );
        back = findViewById( R.id.back );

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
        board_list = new ArrayList<>(  );
        drop_list = new ArrayList<>(  );


        txt_title.setText(source +"-"+destination );

        rel_board.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_board.setVisibility(SelectBoardingPoint.this.getResources().getColor( R.color.red_600 ) );
                view_drop.setBackgroundColor(SelectBoardingPoint.this.getResources().getColor( R.color.white ) );
                recycler_droping.setVisibility( View.GONE);
                recycler_boarding.setVisibility( View.VISIBLE );
                getBoardPoints();

//                Intent  intent = new Intent( SelectBoardingPoint.this,AddPassengerDetails.class);
//                intent.putExtra( "seat_fare",String.valueOf( seat_fare ));
//                intent.putExtra( "date",String.valueOf( date ));
//                intent.putExtra( "source",source );
//                intent.putExtra( "destination",destination );
//                startActivity( intent );

            }
        } );

        rel_drop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_board.setVisibility( SelectBoardingPoint.this.getResources().getColor( R.color.white ) );
                view_drop.setVisibility( SelectBoardingPoint.this.getResources().getColor( R.color.red_600 ));
                recycler_droping.setVisibility( View.VISIBLE );
                recycler_boarding.setVisibility( View.GONE );
            }
        }  );


    }

    public void getBoardPoints ( )
    {
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "to",destination );
        params.put( "from",source );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_DROP_POINTS, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e( "drop points",response.toString() );
//                        Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                        try {
                            Boolean status = response.getBoolean( "responce" );
                            if (status)
                            {
                                JSONObject data = (JSONObject) response.get( "data" );

                                JSONArray array_from = data.getJSONArray( "from" );

                                JSONObject from_obj = array_from.getJSONObject( 0 );
                               JSONArray drop_arr = from_obj.getJSONArray( "bus_drops" );
                                String from = from_obj.getString( "bus_drops" );
                                JSONArray to_arr = new JSONArray(from);

//                                for (int i =0 ; i<from_arr.length();i++)
//                                {
//                                    BoardingPointModel model_from = new BoardingPointModel( );
//                                    model_from.setLocation_name( (String) from_arr.get( i ) );
//                                    board_list.add( model_from );
//                                }
                                JSONArray array_to = data.getJSONArray( "to" );
                                JSONObject to_obj = array_to.getJSONObject( 0 );
                                String to = to_obj.getString( "bus_drops" );
                                JSONArray to_arr = new JSONArray(to );
//                                for (int i =0 ; i<to_arr.length();i++)
//                                {
//                                    BoardingPointModel model_to = new BoardingPointModel( );
//                                    model_to.setLocation_name( (String) from_arr.get( i ) );
//                                    drop_list.add( model_to );
//                                }
                                Toast.makeText(SelectBoardingPoint.this,""+array_from +"\n" +array_to,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                } );
        AppController.getInstance().addToRequestQueue(jsonRequest,"drop points" );
    }


}
