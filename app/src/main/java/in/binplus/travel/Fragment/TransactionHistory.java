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
import android.widget.LinearLayout;
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

import in.binplus.travel.Adapter.TransactionHistoryAdapter;
import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.TransactionModel;
import in.binplus.travel.R;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_ID;


public class TransactionHistory extends Fragment {
    RecyclerView recycler_transaction ;
TransactionHistoryAdapter transactionHistoryAdapter ;
ArrayList<TransactionModel> trans_list ;
String user_id ;
Session_management session_management ;
RelativeLayout rel_no_records ;
LinearLayout linear_trns;
ProgressDialog loadingBar ;
Module module ;

    public TransactionHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate( R.layout.fragment_transaction_history, container, false );

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Transaction History");

        session_management = new Session_management(getActivity() );
        user_id = session_management.getUserDetails().get( KEY_ID );
        rel_no_records = view.findViewById( R.id.rel_norecord );
        linear_trns = view.findViewById( R.id.linear_trans );
        loadingBar = new ProgressDialog( getActivity() );

       recycler_transaction = view.findViewById( R.id.recycler_transaction );
        makeTransactionHistoryRequest( user_id );
        trans_list = new ArrayList<>(  );


        return view ;
    }

    public void makeTransactionHistoryRequest (String id)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "user_id",id );
        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_TRANSACTION_HISTORY, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadingBar.dismiss();
                            //Boolean status = response.getBoolean( "responce" );
                            if (response.has( "data" )) {
                                JSONArray array = response.getJSONArray( "data" );
                                for (int i = 0 ;i<array.length();i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    TransactionModel model = new TransactionModel();
                                    model.setId(object.getString( "t_id" ));
                                    model.setAmount( object.getString( "t_amount" ) );
                                    model.setStatus( object.getString( "t_status" ) );
                                    model.setDate( object.getString( "date" ) );
                                    model.setDescription( object.getString( "t_description" ) );
                                    trans_list.add( model );
                                }
                                recycler_transaction.setVisibility( View.VISIBLE );
                                rel_no_records.setVisibility( View.GONE );
                                transactionHistoryAdapter = new TransactionHistoryAdapter( trans_list,getActivity() );
                                recycler_transaction.setLayoutManager( new LinearLayoutManager( getActivity(),LinearLayoutManager.VERTICAL,false ) );
                                recycler_transaction.setAdapter( transactionHistoryAdapter );
                             //   Toast.makeText(getActivity(),"list"+response,Toast.LENGTH_LONG).show();
                            //    Toast.makeText(getActivity(),"list"+trans_list.size(),Toast.LENGTH_LONG).show();


                            }
                            else
                            {
                               rel_no_records.setVisibility( View.VISIBLE );
                               recycler_transaction.setVisibility( View.GONE );
                            }

                        } catch (JSONException e) {
                            loadingBar.dismiss();
                            e.printStackTrace();
                        }


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
                }
        );
        AppController.getInstance().addToRequestQueue( jsonRequest,"Transaction History" );

    }

}
