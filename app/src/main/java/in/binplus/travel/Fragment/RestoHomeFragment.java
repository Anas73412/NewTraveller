package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import in.binplus.travel.ScanQrCodeActivity;


public class RestoHomeFragment extends Fragment {
    CardView total_earnings , pending_amount ,trans_history ,recharge_history,card_book ,card_hire ;
    Module module ;
    SliderLayout imgSlider;
    ProgressDialog loadingBar ;

    public RestoHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate( R.layout.fragment_resto_home, container, false );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");
        module = new Module( getActivity() );
        imgSlider = view.findViewById( R.id.home_slider );
        total_earnings = view.findViewById( R.id.card_total_earnings );
        pending_amount = view.findViewById( R.id.card_pending_amount );
        trans_history = view.findViewById( R.id.card_trans_history );
        recharge_history = view.findViewById( R.id.card_recharge_history );
        card_book = view.findViewById( R.id.card_scan );
        card_hire = view.findViewById( R.id.card_hire );
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );

        makeGetSliderRequest();

        card_book.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent( getActivity(), ScanQrCodeActivity.class );
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


        return  view ;
    }

    private void makeGetSliderRequest() {

        JsonArrayRequest req = new JsonArrayRequest( BaseURL.GET_R_SLIDER_URL,
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
                            Toast.makeText(getActivity(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
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

}
