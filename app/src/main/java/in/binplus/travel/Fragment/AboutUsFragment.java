package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.About_us_model;

import in.binplus.travel.R;
import in.binplus.travel.util.CustomVolleyJsonRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {
    TextView txt_msg ;
    ProgressDialog loadingBar ;
    Module module ;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate( R.layout.fragment_about_us, container, false );
        txt_msg = view.findViewById( R.id.text );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About Us");

        module = new Module( getActivity() );
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );

        getMessage();

        return view ;
    }

    public void getMessage()
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );

        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.ABOUTUS_URL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean status = false;
                        try {
                            status = response.getBoolean( "responce" );

                            if (status) {
                                loadingBar.dismiss();

                                List<About_us_model> support_info_modelList = new ArrayList<>();

                                Gson gson = new Gson();
                                Type listType = new TypeToken<List<About_us_model>>() {
                                }.getType();


                                support_info_modelList = gson.fromJson( response.getString( "data" ), listType );
                                String desc = support_info_modelList.get( 0 ).getPg_descri();
                                String title = support_info_modelList.get( 0 ).getPg_title();

                                txt_msg.setText( Html.fromHtml( desc ) );

                            }
                        }
                        catch (JSONException e) {
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
                }
        );
        AppController.getInstance().addToRequestQueue( jsonRequest,"About us" );
    }

}
