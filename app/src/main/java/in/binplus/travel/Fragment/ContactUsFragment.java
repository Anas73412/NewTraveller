package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
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

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.R;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;
import in.binplus.travel.util.ToastMsg;

import static in.binplus.travel.Config.Constants.KEY_EMAIL;
import static in.binplus.travel.Config.Constants.KEY_ID;
import static in.binplus.travel.Config.Constants.KEY_MOBILE;
import static in.binplus.travel.Config.Constants.KEY_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {
    EditText et_name ,et_email,et_mobile ,et_message ;
    Button btnSubmit ;
    String email, mobile , name ;
    Session_management session_management ;
    ProgressDialog loadingBar ;
    String user_id ;
    Module module ;



    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_contact_us, container, false );
        et_name = view.findViewById( R.id.et_name );
        et_email = view.findViewById( R.id.et_email );
        et_mobile = view.findViewById( R.id.et_phone );
        et_message = view.findViewById( R.id.et_mesg );
        btnSubmit = view.findViewById( R.id.submit_button );
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Contact Us");

        session_management = new Session_management( getActivity() );
        mobile = session_management.getUserDetails().get( KEY_MOBILE );
        email = session_management.getUserDetails().get( KEY_EMAIL );
        name = session_management.getUserDetails().get( KEY_NAME );
        user_id=session_management.getUserDetails().get( KEY_ID );

        module = new Module( getActivity() );

        et_name.setText( name );
        et_email.setText(email );
        et_mobile.setText( mobile );

        btnSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getname = et_name.getText().toString();
                String getemail = et_email.getText().toString();
                String getmobile = et_mobile.getText().toString();
                String getmessage = et_message.getText().toString();


                if (getname.isEmpty())
                {
                    et_name.setError( "Enter name" );
                    et_name.requestFocus();
                }
                else if (getmobile.isEmpty())
                {
                    et_mobile.setError( "Enter Mobile Number" );
                    et_mobile.requestFocus();
                }
                else if (!(getmobile.length()==10))
                {
                    et_mobile.setError( "Enter Valid Mobile Number" );
                    et_mobile.requestFocus();
                }
                else if (getmessage.isEmpty())
                {
                    et_message.setError( "Enter Query" );
                    et_message.requestFocus();
                }

                   if (!(getemail.isEmpty()))
                    {
                        if (!(getemail.contains( "@" ))) {
                            et_email.requestFocus();
                            et_email.setError( "Enter valid email" );
                        }
                        else if (!(getemail.contains( "." )))
                        {
                            et_email.requestFocus();
                            et_email.setError( "Enter valid email" );
                        }
                        else
                        {
                            submitEnquiry( getname, getemail, getmobile, getmessage );
                        }
                    }

                else
                {
//                    else
//                        {
                        submitEnquiry( getname, getemail, getmobile, getmessage );
//                    }


                }
            }
        } );
        return  view ;
    }

    public void submitEnquiry(String name ,String email ,String mobile ,String msg )
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "user_id",user_id );
        params.put( "name",name);
        params.put( "email",email );
        params.put( "mobile",mobile );
        params.put( "message",msg );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.PUT_SUGGESTION_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loadingBar.dismiss();
                            Boolean status = response.getBoolean( "responce");
                        ///    Toast.makeText(getActivity(),""+response,Toast.LENGTH_LONG).show();
                            if (status)
                            {
                                String msg = response.getString( "message" );

                                new ToastMsg(getActivity()).toastIconSuccess(msg);
                                HomeFragment fm=new HomeFragment() ;
                                FragmentManager manager=getFragmentManager();
                                manager.beginTransaction().replace(R.id.container_frame,fm).addToBackStack(null).commit();
                            }
                            else
                            {

                                String msg = response.getString( "error" );

                                new ToastMsg(getActivity()).toastIconError(msg);
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
        AppController.getInstance().addToRequestQueue( jsonRequest,"Enquiry" );
    }

}
