package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.Adapter.RechargeRequestAdapter;
import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.REchargeHistoryModel;
import in.binplus.travel.R;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechargeHistoryFragment extends Fragment {
    RecyclerView recycler_recharge ;
    RelativeLayout rel_no_records ;
    String user_id ;
    Session_management session_management ;
    RechargeRequestAdapter rechargeRequestAdapter ;
    ArrayList<REchargeHistoryModel> recharge_list ;
    ProgressDialog loadingBar ;
    Module module ;



    public RechargeHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View view =inflater.inflate( R.layout.fragment_recharge_history, container, false );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Recharge History");
    recycler_recharge = view.findViewById( R.id.recycler_recharge_request );
    recycler_recharge.setLayoutManager( new LinearLayoutManager( getActivity(),LinearLayoutManager.VERTICAL,false ) );
    session_management = new Session_management( getActivity() );
    user_id = session_management.getUserDetails().get( KEY_ID );
    rel_no_records = view.findViewById( R.id.rel_norecord );
    recharge_list = new ArrayList<>(  );
    loadingBar = new ProgressDialog( getActivity() );
    loadingBar.setMessage( "loading" );
    module = new Module( getActivity() );

    getRechargeHistory( user_id );
    return view ;
    }

    public void getRechargeHistory(String user_id)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>( );
        params.put( "user_id",user_id );

        CustomVolleyJsonRequest customVolleyJsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_RECHARGE_HISTORY, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if (response.has( "data" )) {
                                loadingBar.dismiss();
                                JSONArray array = response.getJSONArray( "data" );
                           // JSONObject object = response.getJSONObject( "data" );


                            for (int i = 0 ; i<array.length();i++) {
                                JSONObject object = array.getJSONObject( i );
                                REchargeHistoryModel model = new REchargeHistoryModel(  );
                                model.setId( object.getString( "id" ) );
                                model.setCurrent_amount( object.getString( "current_amount" ) );
                                model.setRequest_amount( object.getString( "request_amount" ) );
                                model.setStatus( object.getString( "status" ) );
                                model.setDate( object.getString( "date" ) );
                                model.setDesc( object.getString( "note" ) );
                                model.setUser_id( object.getString( "user_id" ) );
                                recharge_list.add( model );
                            }
                            rel_no_records.setVisibility( View.GONE );
                            recycler_recharge.setVisibility( View.VISIBLE );
                            rechargeRequestAdapter = new RechargeRequestAdapter( recharge_list,getActivity() );
                            recycler_recharge.setLayoutManager( new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false ) );
                            recycler_recharge.setAdapter( rechargeRequestAdapter );
                            }



                        } catch (JSONException e) {
                            loadingBar.dismiss();
                            e.printStackTrace();
                        }


//                        Toast.makeText(getActivity(),""+response,Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingBar.dismiss();

                        String msg=module.VolleyErrorMessage(error);
                        if(!msg.equals(""))
                        {
                            Toast.makeText(getActivity(),""+msg,Toast.LENGTH_LONG).show();
                        }
                    }
                } );
        AppController.getInstance().addToRequestQueue( customVolleyJsonRequest,"recharge history" );
    }

}
