package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.ForgetPasswordActivity;
import in.binplus.travel.R;
import in.binplus.travel.RegisterActivity;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.ToastMsg;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtpVerificationFragment extends Fragment implements View.OnClickListener{

    ProgressDialog loadingBar;
    Module module;
    EditText edt_otp;
    TextView tv_countdown,tv_resend;
    Button btn_verify_otp;
    String type="";
    String mobile="";
    //For CountdownTimer
    CountDownTimer countDownTimer;
    public static final long START_TIME_IN_MILLIS=120000;
    boolean mTimerRunning;
    public long mLeft_TIME_IN_MILLIS=START_TIME_IN_MILLIS;
    public OtpVerificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_otp_verification, container, false);
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        loadingBar.setCanceledOnTouchOutside(false);
        edt_otp=(EditText)view.findViewById(R.id.edt_otp);
        btn_verify_otp=(Button)view.findViewById(R.id.btn_verify_otp);
        tv_resend=(TextView)view.findViewById(R.id.tv_resend);
        tv_countdown=(TextView)view.findViewById(R.id.tv_countdown);
        module=new Module(getActivity());
        Bundle bundle=getArguments();
        type=bundle.getString("type");
        mobile=bundle.getString("mobile");
       startTimer();
        btn_verify_otp.setOnClickListener(this);
        tv_resend.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id == R.id.btn_verify_otp)
        {
             allValidation();
        }
        else if(id == R.id.tv_resend)
        {
            resendOTP();
        }

    }

    private void resendOTP() {

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

    private void allValidation() {

        String otp=edt_otp.getText().toString().trim();

        if(otp.equals("") || otp.isEmpty())
        {
            edt_otp.setError(getActivity().getResources().getString(R.string.required_otp));
            edt_otp.requestFocus();
        }
        else if(otp.length()!=6)
        {
            edt_otp.setError(getActivity().getResources().getString(R.string.length_otp));
            edt_otp.requestFocus();

        }
        else if(tv_countdown.getText().toString().equals("timeout"))
        {
            new ToastMsg(getActivity()).toastIconError("TimeOut Resend Your OTP");
        }
        else
        {
            if(type.equals("f"))
            {
                verifyMobileWithOtp(mobile,otp);

            }
            else if(type.equals("r"))
            {
                verifyRegisterMobileWithOtp(mobile,otp);

            }
        }

    }

    private void verifyRegisterMobileWithOtp(final String mobile, final String otp) {
        loadingBar.show();
        String json_tag="json_verification";
        HashMap<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        map.put("otp",otp);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.URL_VERIFY_REGISTER_OTP, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    Log.d("verify",response.toString());
                    boolean status=response.getBoolean("responce");
                    if(status)
                    {
                        String data=response.getString("data");

                        Intent intent = new Intent( getActivity(), RegisterActivity.class );
                        intent.putExtra( "mobile", mobile );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity( intent );
                        getActivity().finish();

                    }
                    else
                    {
                        loadingBar.dismiss();
                        new ToastMsg(getActivity()).toastIconError(response.getString("error").toString());
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    new ToastMsg(getActivity()).toastIconError(ex.getMessage());
                }
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
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);
    }


    private void verifyMobileWithOtp(final String number, String otp) {
        loadingBar.show();
        String json_tag="json_verification";
        HashMap<String,String> map=new HashMap<>();
        map.put("mobile",number);
        map.put("otp",otp);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.URL_VERIFY_OTP, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    Log.d("verify",response.toString());
                    boolean status=response.getBoolean("responce");
                    if(status)
                    {
                        String data=response.getString("data");

                        Intent intent = new Intent( getActivity(), ForgetPasswordActivity.class );
                        intent.putExtra( "mobile", number );
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity( intent );
                        getActivity().finish();

                    }
                    else
                    {
                        loadingBar.dismiss();
                        Toast.makeText(getActivity(),""+response.getString("error").toString(),Toast.LENGTH_LONG).show();
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
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
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);
    }

    public void startTimer()
    {
        countDownTimer=new CountDownTimer(mLeft_TIME_IN_MILLIS,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(tv_resend.getVisibility() == View.VISIBLE)
                {
                    tv_resend.setVisibility(View.GONE);
                }
              mLeft_TIME_IN_MILLIS=millisUntilFinished;
              updateCounterText();
            }

            @Override
            public void onFinish() {
                tv_countdown.setText("timeout");
                tv_countdown.setTextColor(Color.RED);
                tv_resend.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning=true;
    }

    private void updateCounterText() {
        int minutes=(int)(mLeft_TIME_IN_MILLIS/1000)/60;
        int seconds=(int)(mLeft_TIME_IN_MILLIS/1000)%60;

        String timeLeftInFormat=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        tv_countdown.setText(timeLeftInFormat);
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
                    //    Toast.makeText(getActivity(),""+response.toString(),Toast.LENGTH_LONG).show();
                    boolean responce=response.getBoolean("responce");
                    if(responce)
                    {
                        loadingBar.dismiss();
                        new ToastMsg(getActivity()).toastIconSuccess("OTP Sent Successfully..");

                        timerRestart();
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
                        new ToastMsg(getActivity()).toastIconSuccess("OTP Sent Successfully..");
                         timerRestart();
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

    public void timerRestart()
    {
        countDownTimer.cancel();
        mLeft_TIME_IN_MILLIS=START_TIME_IN_MILLIS;
        startTimer();
    }
}
