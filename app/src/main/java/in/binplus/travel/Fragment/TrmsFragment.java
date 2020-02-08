package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.Support_info_model;
import in.binplus.travel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrmsFragment extends Fragment {
    Module module ;
    TextView txt_info ;
    ProgressDialog loadingBar ;



    public TrmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_trms, container, false );
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Terms & Conditions");
        txt_info = view.findViewById( R.id.text );
        makeGetInfoRequest( );
        return  view ;
    }
    private void makeGetInfoRequest() {
        loadingBar.show();

        // Tag used to cancel the request
        String tag_json_obj = "json_info_req";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest( Request.Method.GET,
                BaseURL.TERMS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("terms", response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object

                    boolean status = response.getBoolean("responce");
                    if (status) {
                        loadingBar.dismiss();

                        List<Support_info_model> support_info_modelList = new ArrayList<>();

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Support_info_model>>() {
                        }.getType();

                        support_info_modelList = gson.fromJson(response.getString("data"), listType);

                        String desc = support_info_modelList.get(0).getPg_descri();
                        String title = support_info_modelList.get(0).getPg_title();

                        txt_info.setText( Html.fromHtml(desc));

                    }

                } catch (JSONException e) {
                    loadingBar.dismiss();
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

}
