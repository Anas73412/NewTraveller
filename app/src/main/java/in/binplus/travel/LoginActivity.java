package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;
import in.binplus.travel.util.ToastMsg;

public class LoginActivity extends AppCompatActivity {

    EditText et_user_id , et_pass ;
    Button btn_continue , btn_register ;
    Session_management session_management ;
    TextView btn_forget ;
    ProgressDialog loadingBar ;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        session_management = new Session_management( LoginActivity.this );
        loadingBar = new ProgressDialog(this );
        loadingBar.setMessage( "loading" );
        module=new Module(LoginActivity.this);
        et_user_id = findViewById( R.id.et_login_email );
        et_pass = findViewById( R.id.et_login_pass );
        btn_continue =findViewById( R.id.btnContinue );
        btn_register=findViewById( R.id.btnRegister );
        btn_forget = findViewById( R.id.btnForgot );

        btn_forget.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this,VerificationActivity.class );
                intent.putExtra("type","f");
                startActivity( intent );
            }
        } );

        btn_continue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getuser_id = et_user_id.getText().toString().trim();
                String getpass = et_pass.getText().toString().trim();
                if (getuser_id.isEmpty())
                {
                    et_user_id.setError( "mobile no is required" );
                    et_user_id.requestFocus();
                }
                else if( getpass.isEmpty())
                {
                    et_pass.setError( "password is required" );
                    et_pass.requestFocus();
                }
                else
                {
                    makeLoginRequest( getuser_id,getpass );

                }

            }
        } );

        btn_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this,VerificationActivity.class );
                intent.putExtra("type","r");
                startActivity( intent );


            }
        } );
    }

    private void makeLoginRequest(String number, final String password) {

        loadingBar.show();
        // Tag used to cancel the request
        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", number);
        params.put("password", password);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest( Request.Method.POST,
                BaseURL.LOGIN_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("login_request", response.toString());

                loadingBar.dismiss();
                try {
                    Boolean status = response.getBoolean("responce");
                   // Toast.makeText(LoginActivity.this, "" +response, Toast.LENGTH_SHORT).show();
                    if (status)
                    {
                        JSONObject obj = response.getJSONObject("data");
                        String user_id = obj.getString("user_id");
                        String user_name = obj.getString("user_name");
                        String agent_name = obj.getString( "agent_name");
                        String user_email = obj.getString("user_email");
                        String user_phone = obj.getString("user_mobile");
                        String f_address = obj.getString( "user_address" );
                        String wallet_ammount = obj.getString("wallet");
                        String type = obj.getString( "type" );
                        String is_verified=obj.getString("is_verified");

                        if(is_verified.equalsIgnoreCase("0"))
                        {
                            new ToastMsg(LoginActivity.this).toastIconError("Your documents not Uploaded \n Please do upload them");
                            Intent intent=new Intent(LoginActivity.this,SubmitDocuments.class);
                            intent.putExtra("mobile",user_phone);
                            startActivity(intent);
                        }

                        else if(is_verified.equalsIgnoreCase("1"))
                        {
                            Session_management sessionManagement = new Session_management(LoginActivity.this);
                            sessionManagement.createLoginSession(user_id,user_email,user_name,user_phone,"",wallet_ammount,"","",agent_name,f_address,type,password);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                            btn_continue.setEnabled(false);
                        }
//                        String user_image = obj.getString("user_image");

                   //     String reward_points = obj.getString("rewards");

                      //  Toast.makeText(LoginActivity.this, "" +response, Toast.LENGTH_SHORT).show();

                    } else {
                        String error = response.getString("error");
                        btn_continue.setEnabled(true);
                        new ToastMsg(LoginActivity.this).toastIconError(error);

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                Log.e("erroror",""+error.getMessage());
                String msg=module.VolleyErrorMessage(error);
                if(!msg.equals(""))
                {
                    new ToastMsg(LoginActivity.this).toastIconError(msg);

                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


}
