package in.binplus.travel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import in.binplus.travel.util.ToastMsg;

public class RegisterActivity extends AppCompatActivity {
    EditText et_name , et_fname ,et_mobile , et_email ,et_pass ,et_cpass , et_add ;
    Button btn_register ;
    RadioGroup radio_type ;
    RadioButton radio_f , radio_r ;
    String type ="" ;
    Module module;
    String getusername = "";
    String getfname = "";
    String getmobile = "";
    String getemail = "";
    String getadd = "";
    String getpass = "";
    String getcpass = "";
    ProgressDialog loadingBar ;
    String intent_mobile="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        loadingBar = new ProgressDialog( RegisterActivity.this );
        loadingBar.setMessage( "loading" );
        module=new Module(RegisterActivity.this);
        intent_mobile=getIntent().getStringExtra("mobile");
        et_name = findViewById( R.id.et_name);
        et_fname = findViewById( R.id.et_company_name );
        et_mobile = findViewById( R.id.et_phone );
        et_email = findViewById( R.id.et_email );
        et_add = findViewById( R.id.et_address );
        et_pass = findViewById( R.id.et_password );
        et_cpass = findViewById( R.id.et_reg__con_password );
        btn_register = findViewById( R.id.btnRegister );
        radio_type = findViewById( R.id.type );
        radio_f = findViewById( R.id.franchise );
        radio_r = findViewById( R.id.restaurant );
        et_mobile.setText(intent_mobile);
        et_mobile.setEnabled(false);
        radio_f.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked())
                {
                    Toast.makeText( RegisterActivity.this," You will be registering as a franchise",Toast.LENGTH_LONG ).show();
                    type ="f";
                }
            }
        } );
        radio_r.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText( RegisterActivity.this,"You will be registering as Restaurant Owner",Toast.LENGTH_LONG ).show();
                type = "r";
            }
        } );



        btn_register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_f.isChecked() || radio_r.isChecked()) {

                    getusername = et_name.getText().toString().trim();
                    getfname = et_fname.getText().toString().trim();
                     getmobile = et_mobile.getText().toString().trim();
                    getemail = et_email.getText().toString().trim();
                    getadd = et_add.getText().toString().trim();
                    getpass = et_pass.getText().toString().trim();
                    getcpass = et_cpass.getText().toString().trim();

                    if (getusername.isEmpty()) {
                        et_name.setError( "Please Enter Name" );
                        et_name.requestFocus();
                    } else if (getfname.isEmpty()) {
                        et_fname.setError( "Please Enter Franchise Name" );
                        et_fname.requestFocus();
                    } else if (getmobile.isEmpty()) {
                        et_mobile.setError( "Please Enter Mobile No" );
                        et_mobile.requestFocus();
                    } else if (getmobile.startsWith( "+" )) {
                        et_mobile.setError( "Invalid Mobile No" );
                        et_mobile.requestFocus();
                    } else if (!(getmobile.length() == 10)) {
                        et_mobile.setError( "Invalid Mobile No" );
                        et_mobile.requestFocus();
                    } else if (getpass.isEmpty()) {
                        et_pass.setError( "Please enter a password" );
                        et_pass.requestFocus();
                    }
                    else if(getpass.length()<6)
                    {   et_pass.setError( "Password should be min 6 characters long" );
                        et_pass.requestFocus();
                    }
                    else if (getcpass.isEmpty()) {
                        et_cpass.setError( "Please re-enter password" );
                        et_cpass.requestFocus();
                    }
                    else if(getcpass.length()<6)
                    {   et_cpass.setError( "Password should be min 6 characters long" );
                        et_cpass.requestFocus();
                    }else if (getadd.isEmpty()) {
                        et_add.setError( "Please Enter Franchise Address" );
                        et_add.requestFocus();
                    }

                       else if (!(getemail.contains( "@" ))) {
                            et_email.setError( "enter valid email" );
                            et_email.requestFocus();
                        }
                        else if (!(getemail.contains( "." ))) {
                            et_email.setError( "enter valid email" );
                            et_email.requestFocus();
                        }
                                       else {
                        if (getcpass.equals( getpass )) {

                            try {
                                registerUser(getusername,getfname,getmobile,getemail,getpass,type,getadd);

                            }
                            catch (Exception ex)
                            {
                                new ToastMsg(RegisterActivity.this).toastIconError(ex.getMessage());
                            }
                        }
                        else
                        {
                            et_cpass.setError( "password should be same" );
                            et_cpass.requestFocus();
                        }
                    }
                }
                else
                {
                    radio_type.requestFocus();
                    new ToastMsg(RegisterActivity.this).toastIconError("Select type ");
                }

            }
        } );
    }

    public  void registerUser(final String name , final String f_name , final String mobile , final String email , final String password, final String type, String address)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "name",f_name);
        params.put ("agent_name",name);
        params.put( "mobile",mobile);
        params.put( "password",password);
        params.put( "email",email);
        params.put( "type",type );
        params.put( "address",address );


        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.REGISTER_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loadingBar.dismiss();
                        try {
//                            Toast.makeText(SubmitDocuments.this,"" +response,Toast.LENGTH_LONG ).show();
//                            Toast.makeText(SubmitDocuments.this,"" +mobile +"\n" +name +"\n"+password + "\n" +email +"\n" +type,Toast.LENGTH_LONG ).show();
                            Boolean status = response.getBoolean( "responce" );
                            if(status)
                            {

                                String msg = String.valueOf( response.get( "message" ) );
                                new ToastMsg(RegisterActivity.this).toastIconSuccess(msg);

                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setIcon(R.drawable.logo)


                                        .setTitle("Submit Your Documents")

                                        .setMessage("You need to upload your adhar card and pan card for verification")

                                        .setPositiveButton("Upload Now", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                Intent intent = new Intent(RegisterActivity.this, SubmitDocuments.class);

                                                intent.putExtra("mobile", getmobile);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })

                                        .setNegativeButton("Upload Later", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what should happen when negative button is clicked
                                                dialogInterface.dismiss();
                                                Intent intent = new Intent( RegisterActivity.this,LoginActivity.class );
                                                startActivity( intent );
                                            }
                                        });
                                AlertDialog dialog = builder.create();
                                dialog.show();


                            }
                            else
                            {

                                new ToastMsg(RegisterActivity.this).toastIconError(response.get( "message" ).toString());

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
                            new ToastMsg(RegisterActivity.this).toastIconError(msg);

                        }

                    }
                } );

        AppController.getInstance().addToRequestQueue(jsonRequest,"Register User");
    }


}
