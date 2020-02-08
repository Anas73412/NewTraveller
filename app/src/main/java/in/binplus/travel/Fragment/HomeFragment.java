package in.binplus.travel.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.AppController;
import in.binplus.travel.BookingActivity;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.CustomSlider;
import in.binplus.travel.R;
import in.binplus.travel.networkconnectivity.NetworkConnection;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;
import in.binplus.travel.util.ToastMsg;

import static in.binplus.travel.Config.Constants.KEY_ID;
import static in.binplus.travel.Config.Constants.KEY_MOBILE;
import static in.binplus.travel.Config.Constants.KEY_WALLET_Ammount;

public class HomeFragment extends Fragment
{
    CardView total_earnings , pending_amount ,trans_history ,recharge_history,card_book ,card_hire ;
    Module module ;
    ProgressDialog loadingBar ;
    TextView txt_status ,txt_wallet;

    Switch active_switch;
    int active_state ;
    String user_id ;
    String mobile_no ;
    String w_amount ;
    Double wallet ;
    Session_management session_management ;
    SliderLayout imgSlider;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate( R.layout.fragment_home,container,false );

        total_earnings = view.findViewById( R.id.card_total_earnings );
        pending_amount = view.findViewById( R.id.card_pending_amount );
        trans_history = view.findViewById( R.id.card_trans_history );
        recharge_history = view.findViewById( R.id.card_recharge_history );
        card_book = view.findViewById( R.id.card_book );
        card_hire = view.findViewById( R.id.card_hire );
        txt_status =view.findViewById( R.id.status );
        active_switch=view.findViewById( R.id.available_status );
        imgSlider = view.findViewById( R.id.home_slider );
        txt_wallet = view.findViewById( R.id.txt_amount );
        module = new Module( getActivity());
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "Loading" );
        loadingBar.setCancelable(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");

        session_management = new Session_management( getActivity() );
        user_id =session_management.getUserDetails().get(KEY_ID );
        mobile_no= session_management.getUserDetails().get( KEY_MOBILE );
        w_amount = session_management.getUserDetails().get( KEY_WALLET_Ammount );
        wallet = Double.parseDouble( w_amount );


        txt_wallet.setText( getActivity().getResources().getString(R.string.currency)+w_amount );

//        Toast.makeText( getActivity(), "user_id"+user_id +"\n mobile_no" +mobile_no +"\n wallet" +w_amount, Toast.LENGTH_SHORT ).show();

      if ( NetworkConnection.connectionChecking(getActivity())) {
          makeGetSliderRequest();
          getStatus( user_id );
          getWalletAmount( user_id );
      }
      else
      {
          Intent intent = new Intent(getActivity(), NetworkError.class);
          startActivity(intent);
      }

        card_book.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent( getActivity(), BookingActivity.class );
                startActivity( intent );

//
            }
        } );

//        card_hire.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                HireFragment hire =new HireFragment();
//                FragmentManager fragmentManager=getFragmentManager();
//                fragmentManager.beginTransaction().replace( R.id.container_frame,hire )
//                        .addToBackStack( null ).commit();
//            }
//        } );
        active_switch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {


                    active_state = 1;
                    txt_status.setText( "Active" );
                    setStatus(user_id,mobile_no,active_state);
                    //Toast.makeText( getContext(), "on", Toast.LENGTH_LONG ).show();
                }
                else
                {


                    active_state = 0 ;
                    txt_status.setText( "Inactive" );
                    setStatus(user_id,mobile_no,active_state);
                    //   Toast.makeText( getContext(), "off", Toast.LENGTH_LONG ).show();
                }


            }
        } );

        total_earnings.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        } );
        pending_amount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             MyEarningsFragment earningsFragment =new MyEarningsFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,earningsFragment )
                        .addToBackStack( null ).commit();

            }
        } );
        trans_history.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionHistory trans =new TransactionHistory();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,trans )
                        .addToBackStack( null ).commit();
            }
        } );

        recharge_history.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RechargeHistoryFragment rechargeFragment =new RechargeHistoryFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,rechargeFragment )
                        .addToBackStack( null ).commit();
            }
        } );


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getStatus( user_id );
       getWalletAmount( user_id );
    }

    private void setStatus(String id, String mobile, final int active_state) {
        loadingBar.show();
        String json_tag="json_status_tag";
//      Toast.makeText(getActivity(),"set:"+id +""+mobile +""+active_state,Toast.LENGTH_SHORT).show();

        HashMap<String,String> map=new HashMap<>();
        map.put("user_id",id);
        map.put("status", String.valueOf( active_state ) );
        map.put( "mobile",mobile );


        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_AVAILABILITY_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               loadingBar.dismiss();
                try
                {
                    boolean res=response.getBoolean("responce");
//                    Toast.makeText(getActivity(),"set:"+response,Toast.LENGTH_SHORT).show();
                    if(res)
                    {
                        if(active_state == 1)
                        {
                            txt_status.setText( "Active" );
                            txt_status.setBackgroundTintList(getResources().getColorStateList(R.color.green_switch));

                        }
                        else
                        {
                            txt_status.setText( "Inactive" );
                            txt_status.setBackgroundTintList(getResources().getColorStateList(R.color.red_switch));

                        }

                        // Toast.makeText(getActivity(),""+response.getString("message").toString(),Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        txt_status.setText( "Inactive" );
                        Toast.makeText(getActivity(),""+response.getString("error").toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              loadingBar.dismiss();
                String msg=module.VolleyErrorMessage(error);
                if(!(msg.isEmpty() || msg.equals("")))
                {
                    Toast.makeText(getActivity(),""+msg.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);

    }
    private void getStatus(String id) {

        String json_tag="json_get_status";
        HashMap<String,String> map=new HashMap<>();
        map.put("user_id",id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_CURRENT_STATUS, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try
                {
//                    boolean resp=response.getBoolean("responce");
//                    if(resp)
//                    {
                 //   Toast.makeText(getActivity(),"get " +user_id +"/n"+response.toString(),Toast.LENGTH_SHORT).show();
                        String status="";
                        JSONArray array=response.getJSONArray("data");
                        for(int i=0; i<array.length();i++)
                        {
                            JSONObject object=array.getJSONObject(0);
                            status=object.getString("f_status");

                        }
                        if(status.equals("1"))
                        {
                            active_switch.setChecked(true);
                            txt_status.setText( "Active" );
                            txt_status.setBackgroundTintList(getResources().getColorStateList(R.color.green_switch));


                        }
                        else
                        {
                            active_switch.setChecked(false);
                            txt_status.setText( "Inactive" );
                            txt_status.setBackgroundTintList(getResources().getColorStateList(R.color.red_switch));

                        }
//                    }
//                    else
//                    {
//                        Toast.makeText(getActivity(),""+response.getString("error").toString(),Toast.LENGTH_SHORT).show();
//                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg=module.VolleyErrorMessage(error);
                if(!(msg.isEmpty() || msg.equals("")))
                {
                    Toast.makeText(getActivity(),""+msg.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);
    }

    private void makeGetSliderRequest() {
        JsonArrayRequest req = new JsonArrayRequest( BaseURL.GET_SLIDER_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("sliders", response.toString());
                        try {
                            ArrayList<HashMap<String, String>> listarray = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                HashMap<String, String> url_maps = new HashMap<String, String>();
                                url_maps.put("slider_title", jsonObject.getString("slider_title"));
                                url_maps.put("sub_cat", jsonObject.getString("sub_cat"));
                                url_maps.put("slider_image", BaseURL.IMG_SLIDER_URL + jsonObject.getString("slider_image"));
                                listarray.add(url_maps);
                            }
                            for (final HashMap<String, String> name : listarray) {
                                CustomSlider textSliderView = new CustomSlider(getActivity());
                                textSliderView.description(name.get("")).image(name.get("slider_image")).setScaleType( BaseSliderView.ScaleType.Fit);
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle().putString("extra", name.get("slider_title"));
                                textSliderView.getBundle().putString("extra", name.get("sub_cat"));
                                imgSlider.addSlider(textSliderView);
                                final String sub_cat = (String) textSliderView.getBundle().get("extra");
                                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {


                                    }
                                });


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String msg=module.VolleyErrorMessage(error);
                if(!msg.equals(""))
                {
                    Toast.makeText(getActivity(),""+msg,Toast.LENGTH_LONG).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(req);

    }

    public void getWalletAmount ( String user_id)
    {

        HashMap<String,String> params = new HashMap<>(  );
        params.put( "user_id",user_id );
        CustomVolleyJsonRequest  jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GETWALLET_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                          String status = response.getString( "success"  );
                            if (status.equals( "success" ))
                            {
                                String w_amount = response.getString( "wallet" );
                                Double wallet_amount = Double.parseDouble( w_amount );
//                                if (!(wallet_amount==wallet))
//                                {
//                                    session_management.updateWallet( w_amount );
//                                    txt_wallet.setText( getActivity().getResources().getString(R.string.currency)+w_amount );
////                                    Toast.makeText( getActivity(),"previous"+wallet+"\nnew"+wallet_amount ,Toast.LENGTH_LONG).show();
//                                }
//
                            }
                            else
                            {
//                                String w_amount = response.getString( "wallet" );
//                                Toast.makeText( getActivity(),"previous"+wallet+"\nnew"+w_amount,Toast.LENGTH_LONG).show();
//                                txt_wallet.setText( getActivity().getResources().getString(R.string.currency)+"0" );

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( getActivity(),""+error.getMessage(),Toast.LENGTH_LONG ).show();

                    }
                } );
        AppController.getInstance().addToRequestQueue( jsonRequest,"Wallet Amount" );
    }
}
