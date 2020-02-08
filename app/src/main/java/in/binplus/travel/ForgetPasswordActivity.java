package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;
import in.binplus.travel.util.ToastMsg;

import static in.binplus.travel.Config.Constants.KEY_MOBILE;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText et_pass , et_cpass ;
    ProgressDialog loadingBar;
    Button btnUpdate;
    String mobile ,getMobile;
    Session_management session_management ;
    LinearLayout linear_pass ;
    ImageView back ;
    Module module;
    Activity ctx=ForgetPasswordActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_forget_password );
        loadingBar = new ProgressDialog( this );
        loadingBar.setMessage( "loading" );
        loadingBar.setCanceledOnTouchOutside(false);
        module=new Module(ctx);
        getMobile=getIntent().getStringExtra("mobile");
        et_pass = findViewById( R.id.new_pass );
        et_cpass = findViewById( R.id.confrm_new_pass );
        btnUpdate = findViewById( R.id.btnUpdate );
        linear_pass = findViewById( R.id.linear_pass );
        session_management = new Session_management( this );
        mobile = session_management.getUserDetails().get(KEY_MOBILE );
        back = findViewById( R.id.back );
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );


        btnUpdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getpass = et_pass.getText().toString();
                String getcpass =et_cpass.getText().toString();
                if (getcpass.isEmpty())
                {
                    et_cpass.setError( "Re-enter Password" );
                    et_cpass.requestFocus();
                }
                else if (getpass.isEmpty())
                {
                    et_pass.requestFocus();
                    et_pass.setError( "Enter new Password" );
                }
             else if ((getcpass.length()<6))
                {
                    et_cpass.setError( "Password should be min 6 character long" );
                    et_cpass.requestFocus();
                }
             else if ((getpass.length()<6))
                {
                    et_pass.requestFocus();
                    et_pass.setError( "Enter new Password" );
                }
             else
                {
                    if (getcpass.equals( getpass ))
                    {
                       makeForgetPasswordRequest( getMobile,getpass );
                    }
                }
            }
        } );



    }

    public void makeForgetPasswordRequest(String mobile,String password)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put("mobile" , mobile);
        params.put( "password",password );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.FORGOT_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        loadingBar.dismiss();
                        try {
                            Boolean status = response.getBoolean( "responce" );
                            if (status)
                            {
                                String msg = String.valueOf( response.get( "message" ) );
                                new ToastMsg(ctx).toastIconSuccess(msg);
                                Intent intent=new Intent(ctx,LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                new ToastMsg(ctx).toastIconError(response.getString("error"));
                            }
                        } catch (JSONException e) {
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
                            new ToastMsg(ctx).toastIconError(msg);
                        }
                    }
                } );

        AppController.getInstance().addToRequestQueue( jsonRequest,"Forgot Password" );

    }
}
