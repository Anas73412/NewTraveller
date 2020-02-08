package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import static in.binplus.travel.Config.Constants.KEY_PASSWORD;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {
    EditText et_oldpass ,et_newpass ,et_conpass ;
    String oldpass ;
    Session_management session_management ;
    Button btn_update ;
    String user_id ;
    ProgressDialog loadingBar ;
    Module module ;


    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_change_password, container, false );
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Update Password");

        et_oldpass = view.findViewById( R.id.old_pass );
        et_conpass = view.findViewById( R.id.confrm_new_pass );
        et_newpass = view.findViewById( R.id.new_pass );
        btn_update = view.findViewById( R.id.btnUpdate );
        session_management = new Session_management( getActivity() );
        oldpass = session_management.getUserDetails().get( KEY_PASSWORD );
        user_id = session_management.getUserDetails().get( KEY_ID );

        module = new Module( getActivity() );



            btn_update.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String getold = et_oldpass.getText().toString();
                    String getnew = et_newpass.getText().toString();
                    String getcpass = et_conpass.getText().toString();

                    if (getold.isEmpty())
                    {
                        et_oldpass.setError( "Enter Current Password" );
                        et_oldpass.requestFocus();
                    }
                    else if (getold.length()<6)
                    {
                        et_oldpass.setError( "Password should be minimum 6 characters");
                        et_oldpass.requestFocus();
                    }
                    else if (getnew.isEmpty())
                    {
                        et_newpass.setError( "Enter New Password" );
                        et_newpass.requestFocus();
                    }
                    else if (getnew.length()<6)
                    {
                        et_newpass.setError( "Password should be minimum 6 characters");
                        et_newpass.requestFocus();
                    }

                    else if(getcpass.isEmpty())
                    {
                        et_conpass.setError( "Re-enter password" );
                        et_conpass.requestFocus();
                    }
                    else if (getcpass.length()<6)
                    {
                        et_conpass.setError( "Password should be minimum 6 characters");
                        et_conpass.requestFocus();
                    }
//                    else if (!(getnew.equals( getcpass )))
//                    {
//                        et_conpass.setError( "Confirm password doesnot match" );
//                        et_conpass.requestFocus();
//                    }
                    else
                    {
                        if ( getnew.equals( getcpass ))
                        {
                            makeChangePasswordRequest( getold,getnew );
                        }
                        else
                        {
                            et_conpass.setError( "Confirm password does not match" );
                           et_conpass.requestFocus();
                        }
//                        if (getold.equals( oldpass ))
//                        {
//
//                        }
//                        else
//                        {
//                            et_oldpass.setError( "Enter correct password" );
//                            et_oldpass.requestFocus();
//                        }
                    }
                }
            } );
        return view;
    }

    public void makeChangePasswordRequest(String oldpass, String newpass)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "current_password",oldpass );
        params.put( "new_password" , newpass );
        params.put( "user_id" ,user_id);

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.CHANGE_PASSWORD_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean status = response.getBoolean( "responce" );
                            String msg = (String) response.get( "message" );
                          //  Toast.makeText( getActivity(),""+response,Toast.LENGTH_LONG ).show();
                            if (status)
                            {
                                loadingBar.dismiss();
                                Toast.makeText( getActivity(),""+msg,Toast.LENGTH_LONG ).show();
                                getActivity().finish();
                            }
                            else
                            {
                                loadingBar.dismiss();
                             Toast.makeText( getActivity(),""+msg,Toast.LENGTH_LONG ).show();
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
                } );

        AppController.getInstance().addToRequestQueue( jsonRequest,"UpdatePassword" );
    }

}
