package in.binplus.travel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Remote.IUploadAPI;
import in.binplus.travel.Remote.RetrofitClient;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.FileUtil;
import in.binplus.travel.util.ProgressRequestBody;
import in.binplus.travel.util.Session_management;
import in.binplus.travel.util.ToastMsg;
import in.binplus.travel.util.UploadCallBacks;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SubmitDocuments extends AppCompatActivity implements View.OnClickListener, UploadCallBacks {
    Button btn_submit,btn_adh_back,btn_adh_front,btn_pan ;
    EditText et_adhar_id ,et_pan_id ;
    ProgressDialog loadingBar ;
    Module module;
    int click_button=0;
    String mobile ;
    ImageView img_adh_front,img_adh_back,img_pan;
    String adh_f_name="",adh_b_name="",pan_name="";
    private static final int PICK_FILE_REQUEST=1001;
    IUploadAPI mService;
    ProgressDialog dialog;
    public static final int REQUEST_PERMISSION=1000;

    Uri adh_front_uri,adh_back_uri,pan_uri,selectedFileUri;
 public static final String base_url=BaseURL.BASE_URL;
    private IUploadAPI getUPIUpload()
    {
        return RetrofitClient.getClient(base_url).create(IUploadAPI.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_submit_documents );

        loadingBar = new ProgressDialog( this );
        loadingBar.setMessage( "loading" );
        loadingBar.setCanceledOnTouchOutside(false);
        module=new Module(SubmitDocuments.this);
        btn_submit = findViewById( R.id.btnSubmit );
        btn_adh_back = findViewById( R.id.btn_adh_back );
        btn_adh_front = findViewById( R.id.btn_adh_front );
        btn_pan = findViewById( R.id.btn_pan );
        img_adh_front = findViewById( R.id.img_adh_front );
        img_adh_back = findViewById( R.id.img_adh_back );
        img_pan = findViewById( R.id.img_pan );
        et_adhar_id = findViewById( R.id.adhar_id );
        et_pan_id = findViewById( R.id.pan_id );

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },REQUEST_PERMISSION);
        }
        mService=getUPIUpload();

        mobile = getIntent().getStringExtra( "mobile" );

        btn_pan.setOnClickListener(this);
        btn_adh_back.setOnClickListener(this);
        btn_adh_front.setOnClickListener(this);
        btn_submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getadhar = et_adhar_id.getText().toString();
                String getpan = et_pan_id.getText().toString();
                if (getadhar.isEmpty())
                {
                    et_adhar_id.setError( "enter adhaar number" );
                    et_adhar_id.requestFocus();
                }
                else if (!(getadhar.length()==12))
                {
                    et_adhar_id.setError( "enter valid adhaar number" );
                    et_adhar_id.requestFocus();
                }
                else if (getpan.isEmpty())
                {
                    et_pan_id.setError( "enter pan number" );
                    et_pan_id.requestFocus();
                }
                else if (!(getpan.length()==10))
                {
                    et_pan_id.setError( "enter valid pan number" );
                    et_pan_id.requestFocus();
                }
                else if(adh_f_name.equals("") || adh_f_name.isEmpty())
                {
                   new ToastMsg(SubmitDocuments.this).toastIconError(getResources().getString(R.string.required_adh_front));
                }else if(adh_b_name.equals("") || adh_b_name.isEmpty())
                {
                    new ToastMsg(SubmitDocuments.this).toastIconError(getResources().getString(R.string.required_adh_back));
                }else if(pan_name.equals("") || pan_name.isEmpty())
                {
                    new ToastMsg(SubmitDocuments.this).toastIconError(getResources().getString(R.string.required_pan));
                }
                else
                    {
                    registerUser(mobile, getadhar,getpan,adh_f_name,adh_b_name,pan_name);
                }
            }
        } );


    }
    public  void registerUser(final String mobile ,String adharid, String panid,String adh_f_name,String adh_b_name,String pan_name)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );

        params.put( "mobile",mobile);
        params.put( "adhaar_no",adharid );
        params.put( "pan_no",panid );
        params.put( "adhaar_front_image",adh_f_name );
        params.put( "adhaar_back_image",adh_b_name );
        params.put( "pan_image",pan_name );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.UPDATE_DOCUMENTS, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("update-res",response.toString());

//                            Toast.makeText(SubmitDocuments.this,"" +response,Toast.LENGTH_LONG ).show();
//                            Toast.makeText(SubmitDocuments.this,"" +mobile +"\n" +name +"\n"+password + "\n" +email +"\n" +type,Toast.LENGTH_LONG ).show();
                            Boolean status = response.getBoolean( "responce" );
                            if(status)
                            {
                                loadingBar.dismiss();
                                String msg = String.valueOf( response.get( "message" ) );
                                new ToastMsg(SubmitDocuments.this).toastIconSuccess(msg);
                               Intent intent = new Intent( SubmitDocuments.this,LoginActivity.class );
                               startActivity( intent );

                            }
                            else
                            {
                              loadingBar.dismiss();
                                new ToastMsg(SubmitDocuments.this).toastIconError(response.get( "error" ).toString());

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
                            new ToastMsg(SubmitDocuments.this).toastIconError(msg);

                        }

                    }
                } );

        AppController.getInstance().addToRequestQueue(jsonRequest,"Register User");
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

        if(id == R.id.btn_adh_front)
        {
          String ch_btn=btn_adh_front.getText().toString();
          if(ch_btn.equalsIgnoreCase("Choose"))
          {
              click_button=1;
              chooseFile();
          }
          else if(ch_btn.equalsIgnoreCase("Upload"))
          {
              uploadFile();
          }
          else
          {
              new ToastMsg(SubmitDocuments.this).toastIconSuccess("Adhaar Card Front Image already uploded");
          }
        }
        else if( id == R.id.btn_adh_back)
        {
            String ch_btn=btn_adh_back.getText().toString();
            if(ch_btn.equalsIgnoreCase("Choose"))
            {
                click_button=2;
                chooseFile();
            }
            else if(ch_btn.equalsIgnoreCase("Upload"))
            {
                uploadFile();
            }
            else
            {
                new ToastMsg(SubmitDocuments.this).toastIconSuccess("Adhaar Card Back Image already uploded");
            }
        }
        else if(id == R.id.btn_pan)
        {
            String ch_btn=btn_pan.getText().toString();
            if(ch_btn.equalsIgnoreCase("Choose"))
            {
                click_button=3;
                chooseFile();
            }
            else if(ch_btn.equalsIgnoreCase("Upload"))
            {
                uploadFile();
            }
            else
            {
                new ToastMsg(SubmitDocuments.this).toastIconSuccess("PAN Card Image already uploded");
            }
        }

    }

    private void uploadFile() {
        if(selectedFileUri !=null)
        {
            dialog=new ProgressDialog(SubmitDocuments.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(100);
            dialog.setMessage("Loading...");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();

            String file_nm="";
            File file=null;
            try
            {
                if(click_button ==1)
                {
                    file= FileUtil.from(SubmitDocuments.this,adh_front_uri);
                   file_nm=getUniqueCode()+"_adh_front"+"."+getFileExtension(file);
                   adh_f_name=file_nm;
                }
                else if(click_button == 2)
                {
                    file= FileUtil.from(SubmitDocuments.this,adh_back_uri);
                    file_nm=getUniqueCode()+"_adh_back"+"."+getFileExtension(file);
                    adh_b_name=file_nm;
                }
                else if(click_button == 3)
                {
                    file= FileUtil.from(SubmitDocuments.this,pan_uri);
                    file_nm=getUniqueCode()+"_pan"+"."+getFileExtension(file);
                    pan_name=file_nm;
                }
            }
            catch (Exception ex)
            {
                new ToastMsg(SubmitDocuments.this).toastIconError(ex.getMessage());
            }

            ProgressRequestBody requestFile=new ProgressRequestBody(file,this);

            final MultipartBody.Part body=MultipartBody.Part.createFormData("uploaded_file",file_nm,requestFile);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    mService.uploadFile(body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, retrofit2.Response<String> response) {

                                    dialog.dismiss();
                                    if(click_button == 1)
                                    {
                                        btn_adh_front.setText(getResources().getString(R.string.btn_uploaded));
                                    }
                                    else if(click_button == 2)
                                    {
                                        btn_adh_back.setText(getResources().getString(R.string.btn_uploaded));
                                    }
                                    else if(click_button == 3)
                                    {
                                        btn_pan.setText(getResources().getString(R.string.btn_uploaded));
                                    }

                                    else {
                                        Toast.makeText(SubmitDocuments.this,"data upp",Toast.LENGTH_SHORT).show();
                                    }
                                 new ToastMsg(SubmitDocuments.this).toastIconSuccess("Uploaded");
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    new ToastMsg(SubmitDocuments.this).toastIconError(t.getMessage());

                                }
                            });
                }
            }).start();
        }
        }


    private void chooseFile() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == PICK_FILE_REQUEST)
            {
                if(data !=null)
                {
                    if(click_button == 1)
                    {
                        adh_front_uri=data.getData();
                        selectedFileUri=adh_front_uri;
                        img_adh_front.setImageURI(adh_front_uri);
                        btn_adh_front.setText(getResources().getString(R.string.btn_upload));

                    }else if(click_button == 2)
                    {
                        adh_back_uri=data.getData();
                        selectedFileUri=adh_back_uri;
                        img_adh_back.setImageURI(adh_back_uri);
                        btn_adh_back.setText(getResources().getString(R.string.btn_upload));

                    } else if(click_button == 3)
                    {
                        pan_uri=data.getData();
                        selectedFileUri=pan_uri;
                        img_pan.setImageURI(pan_uri);
                        btn_pan.setText(getResources().getString(R.string.btn_upload));

                    }
                    else {

                        new ToastMsg(SubmitDocuments.this).toastIconError("Please select any one image");
                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_PERMISSION:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                new ToastMsg(SubmitDocuments.this).toastIconSuccess("Permisson Granted");
                }
                else
                {
                    new ToastMsg(SubmitDocuments.this).toastIconSuccess("Permisson Denied");
                }
        }
    }

    @Override
    public void onProgressUpdate(int percentage) {
       dialog.setProgress(percentage);
    }

    public String getUniqueCode()
    {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("ddMMyyHHmm");
        String d_name=simpleDateFormat.format(date);
        String code=mobile+d_name;
        return code;
    }

    public String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
