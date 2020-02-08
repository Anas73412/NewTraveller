package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.MainActivity;
import in.binplus.travel.R;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentsFragment extends Fragment {
    RecyclerView recycler_documents;
    ProgressDialog loadingBar ;
    String user_id ;
    Session_management session_management ;
    TextView text_adhar_no ,txt_pan_no ;
    ImageView img_adhar_front ,img_adhar_back,img_pancard;



    public DocumentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        session_management = new Session_management( getActivity() );
        user_id = session_management.getUserDetails().get( KEY_ID );

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Documents");
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_documents, container, false );

        text_adhar_no =view.findViewById( R.id.adhaar_no );
        txt_pan_no =view.findViewById( R.id.pancard_no );
        img_pancard=view.findViewById( R.id.img_pan );
        img_adhar_back=view.findViewById( R.id.adhar_back );
        img_adhar_front =view.findViewById( R.id.adhar_front );


    //    Toast.makeText(getActivity(),""+user_id.toString(),Toast.LENGTH_LONG).show();
        getDocuments( user_id );
        return  view ;
    }

    public void getDocuments(String user_id)
    {
        loadingBar.show();
        HashMap<String,String> params = new HashMap<>(  );
        params.put( "user_id",user_id );
        Log.d( "docs :","" );
        CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_DOCUMENTS, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean( "responce" );
                            if (status)
                            {
                                loadingBar.dismiss();
                                JSONArray array = response.getJSONArray( "data" );
                                String adhaar_no = array.getJSONObject( 0 ).getString( "adhaar_no" );
                                String adhaar_front = array.getJSONObject( 0 ).getString( "adhaar_front_image" );
                                String adhaar_back =array.getJSONObject( 0 ).getString( "adhaar_back_image" );
                                String pan_no = array.getJSONObject(0).getString( "pan_no" );
                                String pan_imge = array.getJSONObject( 0 ).getString( "pan_image" );
//                                Toast.makeText(getActivity(),""+adhaar_no.toString(),Toast.LENGTH_LONG).show();
                                txt_pan_no.setText( pan_no );
                                text_adhar_no.setText( adhaar_no );
                                Glide.with( getActivity() )
                                        .load( BaseURL.DOC_IMG_URL + adhaar_front )
                                        .fitCenter()
                                       // .placeholder( R.drawable.logo )
                                        .crossFade()
                                        .diskCacheStrategy( DiskCacheStrategy.ALL )
                                        .dontAnimate()
                                        .into( img_adhar_front );
                                Glide.with( getActivity() )
                                        .load( BaseURL.DOC_IMG_URL + adhaar_back )
                                        .fitCenter()
                                      //  .placeholder( R.drawable.logo )
                                        .crossFade()
                                        .diskCacheStrategy( DiskCacheStrategy.ALL )
                                        .dontAnimate()
                                        .into( img_adhar_back );
                                Glide.with( getActivity() )
                                        .load( BaseURL.DOC_IMG_URL + pan_imge )
                                        .fitCenter()
                                      //  .placeholder( R.drawable.logo )
                                        .crossFade()
                                        .diskCacheStrategy( DiskCacheStrategy.ALL )
                                        .dontAnimate()
                                        .into( img_pancard);


                            }
                            else
                            {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


//                        Toast.makeText(getActivity(),""+response.toString(),Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                } );

        AppController.getInstance().addToRequestQueue( jsonRequest,"documents" );
    }

}
