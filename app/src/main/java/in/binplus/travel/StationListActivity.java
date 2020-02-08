package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.binplus.travel.Adapter.CityListAdapter;
import in.binplus.travel.Adapter.TopCityAdapter;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Model.CityListModel;
import in.binplus.travel.util.CustomVolleyJsonArrayRequest;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.RecyclerTouchListener;

public class StationListActivity extends AppCompatActivity {
    RecyclerView recycler_citylist , recycler_top , recycler_recent ;

    RelativeLayout rel_search ;
    ImageView img_search  ,back;
    AutoCompleteTextView txt_search ;
    SearchView searchView ;
    EditText et_search ;
    TopCityAdapter topCityAdapter ;
    CityListAdapter cityListAdapter ;
    ArrayList<CityListModel> topcity_list ;
    ArrayList<CityListModel> citylist ;
    ArrayList<CityListModel> recent_list ;
    ArrayList<CityListModel> filterdNames ;
    int flag = 0;
    String getcityname ;
String type ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_station_list );
        recycler_recent = findViewById( R.id.recycler_recent_cities );
        recycler_citylist =findViewById( R.id.recycler_citylist );
        recycler_top = findViewById( R.id.recycler_top_cities );
        back = findViewById(R.id.back);
//        linear_search = findViewById( R.id.linear_citylist );
//        linear_recent = findViewById( R.id.linear_recent);
//        linear_location =findViewById( R.id.linear_location );
//        searchView = findViewById( R.id.search );
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rel_search = findViewById( R.id.rel_search );
        type = getIntent().getStringExtra( "type" );
        et_search = findViewById( R.id.et_search );
        getCityList();
        topcity_list = new ArrayList<>();
//        topcity_list.add( new CityListModel("Mumbai","24107"));
//        topcity_list.add( new CityListModel( "Delhi" ,"22222" ));
//        topcity_list.add( new CityListModel( "Bangalore" ,"22222"));
//        topcity_list.add( new CityListModel( "Pune" ,"22222" ));
//        topcity_list.add( new CityListModel( "Nasik" ,"22222"));
//
        citylist = new ArrayList<>(  );
//        citylist.add( new CityListModel( "Mumbai","00000" ) );
//        citylist.add( new CityListModel( "Calcutta","00000" ) );
//        citylist.add( new CityListModel( "Delhi","00000" ) );
//        citylist.add( new CityListModel( "Bangalore","00000" ) );
//        citylist.add( new CityListModel( "Jhansi","00000" ) );
//        citylist.add( new CityListModel( "Kanpur","00000" ) );
//        citylist.add( new CityListModel( "Ahemdabad","00000" ) );
//        citylist.add( new CityListModel( "Pune","00000" ) );
//        citylist.add( new CityListModel( "Nasik","00000" ) );
//        citylist.add( new CityListModel( "Mysore","00000" ) );
//        citylist.add( new CityListModel( "Agra","00000" ) );
//        citylist.add( new CityListModel( "Ghaziabad","00000" ) );
//        citylist.add( new CityListModel( "Hyderabad","00000" ) );
//        citylist.add( new CityListModel( "Jaipur","00000" ) );
//        citylist.add( new CityListModel( "Udaipur","00000" ) );
//        citylist.add( new CityListModel( "Lucknow","00000" ) );



//        GridLayoutManager gridtop = new GridLayoutManager( StationListActivity.this,2);
//        topCityAdapter = new TopCityAdapter(topcity_list,StationListActivity.this);
//        recycler_top.setLayoutManager( gridtop );
//        recycler_top.setAdapter( topCityAdapter );

//      recycler_top.addOnItemTouchListener(new RecyclerTouchListener(StationListActivity.this,recycler_top, new RecyclerTouchListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                String getcityname = topcity_list.get(position).getTitle();
//
//                if (type.equalsIgnoreCase( "to" ))
//                {
//                    BookingActivity.txt_to.setText( getcityname );
//                    finish();
//                }
//               else  if (type.equalsIgnoreCase( "from" ))
//                {
//                    BookingActivity.txt_from.setText( getcityname );
//                    finish();
//                }

//                Bundle args = new Bundle();
//                Fragment fm = new Product_fragment();
//                args.putString("cat_id", getid);
//                args.putString("cat_title", getcat_title);
//                fm.setArguments(args);
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                        .addToBackStack(null).commit();

//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));
        recycler_citylist.addOnItemTouchListener(new RecyclerTouchListener(StationListActivity.this,recycler_citylist, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (flag ==0) {
                 getcityname = citylist.get( position ).getTitle();
                // Toast.makeText( StationListActivity.this,"citylist",Toast.LENGTH_LONG ).show();
                }
                else if(flag ==1)
                {
                    getcityname = filterdNames.get( position ).getTitle();
//                    Toast.makeText( StationListActivity.this,"filterednames",Toast.LENGTH_LONG ).show();
                }

                if (type.equalsIgnoreCase( "to" ))
                {
                    BookingActivity.txt_to.setText( getcityname );
                    finish();
                }
                if (type.equalsIgnoreCase( "from" ))
                {
                    BookingActivity.txt_from.setText( getcityname );
                    finish();
                }
                }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        et_search.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (citylist.contains( charSequence )) {
//                    //  cityListAdapter.getFilter().filter( charSequence );
//                    cityListAdapter.filter( String.valueOf( charSequence ) );
//                } else {
//                    Toast.makeText( StationListActivity.this, "No Match found", Toast.LENGTH_LONG ).show();
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                    cityListAdapter.getFilter().filter( editable.toString() );
                filter( editable.toString() );
                flag = 1;




            }
        });
    }

    public void filter(String text) {
        //new array list that will hold the filtered data
        filterdNames = new ArrayList<>();

        //looping through existing elements
        for (CityListModel s : citylist) {
            //if the existing elements contains the search input
            if (s.getTitle().contains(text)) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        cityListAdapter.filterList(filterdNames);
    }

    public void getCityList()
    {
        HashMap<String,String> params = new HashMap<>(  );
        CustomVolleyJsonRequest jsonArrayRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_STATION_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {

                            JSONArray jsonArray = response.getJSONArray( "data" );

                            for (int i =0 ;i<jsonArray.length();i++)
                            {
                                JSONObject object=jsonArray.getJSONObject(i);
                                CityListModel citymodel = new CityListModel(  );
                                citymodel.setCity_code( object.getString( "city_code" ) );
                                citymodel.setTitle( object.getString( "title" ) );
                                citymodel.setDescription( object.getString( "description" ) );
                                citylist.add( citymodel );
                            }
                            cityListAdapter = new CityListAdapter( citylist,StationListActivity.this );
                            recycler_citylist.setLayoutManager( new LinearLayoutManager( StationListActivity.this ) );
                            recycler_citylist.setAdapter(cityListAdapter );

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                } );
        AppController.getInstance().addToRequestQueue( jsonArrayRequest,"Citylist" );

    }
}
