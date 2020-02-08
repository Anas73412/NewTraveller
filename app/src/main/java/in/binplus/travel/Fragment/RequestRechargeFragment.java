package in.binplus.travel.Fragment;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.R;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_ID;
import static in.binplus.travel.Config.Constants.KEY_USER_TYPE;


public class RequestRechargeFragment extends Fragment {
    Button btn_request ;
    EditText et_amount ;
    String user_id ,type;
    Session_management session_management ;
    ProgressDialog loadingBar ;
    Module module ;


    public RequestRechargeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_request_recharge, container, false );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Request Recharge");
        btn_request = view.findViewById( R.id.btnRequest );
        et_amount = view.findViewById( R.id.et_amount );
        session_management = new Session_management( getActivity() );
        user_id = session_management.getUserDetails().get( KEY_ID );
        type =session_management.getUserDetails().get( KEY_USER_TYPE );
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        module = new Module( getActivity() );

        btn_request.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getamt = et_amount.getText().toString().trim();

                if (getamt.isEmpty())
                {
                    et_amount.setError( "Enter Amount" );
                    et_amount.requestFocus();
                }
                else
                {
                   // Toast.makeText(getActivity(),"Request Sent",Toast.LENGTH_LONG).show();
                    requestRecharge( getamt,user_id );
                }
            }
        } );
        return view ;
    }

    public void requestRecharge(String amount ,String user_id)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "request_amount",amount );
        params.put( "user_id",user_id );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_RECHARGE_REQUEST, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadingBar.dismiss();
                            Boolean status = response.getBoolean( "responce" );
                            String msg = response.getString( "data" );
                            if (status)
                            {
                               Toast.makeText( getActivity(),""+msg,Toast.LENGTH_LONG ).show();
                                if (type.equalsIgnoreCase( "f" )) {
                                    HomeFragment homeFragment = new HomeFragment();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace( R.id.container_frame, homeFragment )
                                            .addToBackStack( null )
                                            .commit();
                                }
                                else if (type.equalsIgnoreCase( "r" ))
                                {
                                    RestoHomeFragment homeFragment = new RestoHomeFragment();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace( R.id.container_frame, homeFragment )
                                            .addToBackStack( null )
                                            .commit();
                                }
                            }
                            else
                            {
                                Toast.makeText( getActivity(),""+msg,Toast.LENGTH_LONG ).show();
                            }
                        } catch (JSONException e) {
                            loadingBar.dismiss();
                            e.printStackTrace();
                        }
//                        Toast.makeText( getActivity(),""+response,Toast.LENGTH_LONG ).show();
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
        AppController.getInstance().addToRequestQueue( jsonRequest,"recharge request" );

    }

}
