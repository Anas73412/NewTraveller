package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.R;
import in.binplus.travel.RegisterActivity;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.ToastMsg;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtpGenerateFragment extends Fragment implements View.OnClickListener{

    ProgressDialog loadingBar;
    Module module;
    EditText edt_mobile;
    Button btn_send_otp;
    String type="";
    public OtpGenerateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_otp_generate, container, false);
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        loadingBar.setCanceledOnTouchOutside(false);
        edt_mobile=(EditText)view.findViewById(R.id.edt_mobile);
        btn_send_otp=(Button)view.findViewById(R.id.btn_send_otp);
        module=new Module(getActivity());
        Bundle bundle=getArguments();
        type=bundle.getString("type");

        btn_send_otp.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();
        if(id == R.id.btn_send_otp)
        {
            attemptVerification();
        }
    }

    private void attemptVerification() {
        String mobile=edt_mobile.getText().toString().trim();

        if(mobile.equals("") || mobile.isEmpty())
        {
            edt_mobile.setError(getActivity().getResources().getString(R.string.required_mobile));
            edt_mobile.requestFocus();
        }
        else if(mobile.length() != 10)
        {
            edt_mobile.setError(getActivity().getResources().getString(R.string.invalid_mobile));
            edt_mobile.requestFocus();

        }
        else
        {
            String otp=getRandom(6);

            if(type.equalsIgnoreCase("r"))
            {

                makeRequestForRegistrationVerification(mobile,otp);
            }
            else
            {

                makeRequestForForgotPassword(mobile,otp);
            }
        }
    }

    private void makeRequestForForgotPassword(final String mobile,final String otp) {
        loadingBar.show();

        String json_tag="json_verifiaction_tag";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("mobile",mobile);
        params.put("otp",otp);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.URL_GEN_OTP, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    Log.e("forget-response",response.toString());
                    //    Toast.makeText(getActivity(),""+response.toString(),Toast.LENGTH_LONG).show();
                    boolean responce=response.getBoolean("responce");
                    if(responce)
                    {
                        loadingBar.dismiss();
                        Bundle bundle=new Bundle();
                        OtpVerificationFragment fm=new OtpVerificationFragment();
                        bundle.putString("mobile",mobile);
                        bundle.putString("otp",otp);
                        bundle.putString("type",type);
                        fm.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container_verification, fm)
                                .addToBackStack(null).commit();
                    }
                    else
                    {
                        loadingBar.dismiss();
                        new ToastMsg(getActivity()).toastIconError(response.getString("error").toString());


                    }
                }
                catch (Exception ex)
                {
                    loadingBar.dismiss();
                    ex.printStackTrace();
                    new ToastMsg(getActivity()).toastIconError("Something Went Wrong");

                }
                // Toast.makeText(getActivity(),""+response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                String msg=module.VolleyErrorMessage(error);
                if(!msg.equals(""))
                {
                    new ToastMsg(getActivity()).toastIconError(msg);
                }
                //Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);

    }

    private void makeRequestForRegistrationVerification(final String mobile, final String otp) {

        loadingBar.show();

        String json_tag="json_verifiaction_tag";
        HashMap<String,String> params=new HashMap<String, String>();
        params.put("mobile",mobile);
        params.put("otp",otp);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.URL_REGISTER_OTP, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
                    //  Toast.makeText(getActivity(),""+response.toString(),Toast.LENGTH_LONG).show();
                    boolean responce=response.getBoolean("responce");
                    if(responce)
                    {
                        loadingBar.dismiss();
                        Bundle bundle=new Bundle();
                        OtpVerificationFragment fm=new OtpVerificationFragment();
                        bundle.putString("mobile",mobile);
                        bundle.putString("otp",otp);
                        bundle.putString("type",type);
                        fm.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.container_verification, fm)
                                .addToBackStack(null).commit();
                    }
                    else
                    {
                        loadingBar.dismiss();
                        new ToastMsg(getActivity()).toastIconError(response.getString("error").toString());

                    }
                }
                catch (Exception ex)
                {
                    loadingBar.dismiss();
                    ex.printStackTrace();
                    new ToastMsg(getActivity()).toastIconError("Something Went Wrong");
                }
                // Toast.makeText(getActivity(),""+response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg=module.VolleyErrorMessage(error);
                if(!msg.equals(""))
                {
                    new ToastMsg(getActivity()).toastIconError(msg);
                }
                //Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);


    }

    public String getRandom(int i)
    {
        String characters="0123456789";
        StringBuilder builder =new StringBuilder();
        while (i>0)
        {
            Random random=new Random();
           builder.append(characters.charAt(random.nextInt(characters.length())));
           i--;
        }
        return builder.toString();
    }
}
