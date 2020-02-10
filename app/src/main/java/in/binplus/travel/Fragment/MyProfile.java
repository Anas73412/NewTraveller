package in.binplus.travel.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Module;
import in.binplus.travel.R;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_EMAIL;
import static in.binplus.travel.Config.Constants.KEY_FRANCHISE_ADD;
import static in.binplus.travel.Config.Constants.KEY_FRANCHISE_NAME;
import static in.binplus.travel.Config.Constants.KEY_ID;
import static in.binplus.travel.Config.Constants.KEY_IMAGE;
import static in.binplus.travel.Config.Constants.KEY_MOBILE;
import static in.binplus.travel.Config.Constants.KEY_NAME;
import static in.binplus.travel.Config.Constants.KEY_USER_TYPE;

public class MyProfile extends Fragment {
    String type ;
    CircleImageView user_img ;
    TextView user_name , user_mobile , user_email,user_franchise ,user_add ;
    RelativeLayout rel_edit_profile , rel_update_pass ,rel_my_docs;
    Switch active_switch ;
    TextView txt_status ;
    Module module ;
    String user_id ;
    int active_state ;
    Session_management session_management ;
    ProgressDialog loadingBar ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_myprofile,container,false );

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Profile");
        user_img = view.findViewById( R.id.user_img );
        user_name = view.findViewById( R.id.user_name );
        user_mobile = view.findViewById( R.id.user_mobile );
        user_email = view.findViewById( R.id.user_email );
        user_franchise = view.findViewById( R.id.user_franchise );
        user_add = view.findViewById( R.id.user_add );
        rel_edit_profile = view.findViewById( R.id.edit_profile );
        rel_update_pass = view.findViewById( R.id.update_pass );
        rel_my_docs= view.findViewById( R.id.my_doc );
        txt_status =view.findViewById( R.id.status );
        active_switch=view.findViewById( R.id.available_status );
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        module = new Module( getActivity() );



        session_management = new Session_management( getActivity() );
        type = session_management.getUserDetails().get( KEY_USER_TYPE );
        user_id = session_management.getUserDetails().get( KEY_ID );
        getStatus( user_id );


        final String name = session_management.getUserDetails().get( KEY_NAME );
        final String mobile_no = session_management.getUserDetails().get(KEY_MOBILE );
        final String email = session_management.getUserDetails().get( KEY_EMAIL );
        final String franchise = session_management.getUserDetails().get(KEY_FRANCHISE_NAME );
        final String address = session_management.getUserDetails().get( KEY_FRANCHISE_ADD );

        String getimage=session_management.getUpdateProfile().get(KEY_IMAGE);
        if (!TextUtils.isEmpty(getimage)) {


            Glide.with( getActivity())
                    .load( BaseURL.IMG_PROFILE_URL + getimage)
                    .fitCenter()
                    .placeholder( R.drawable.logo )
                    .crossFade()
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .dontAnimate()
                    .into( user_img );
        }

        user_name.setText( name );
        user_mobile.setText( mobile_no );
        user_email.setText( email );
        user_franchise.setText( franchise );
        user_add.setText( address );

        rel_my_docs.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentsFragment docs = new DocumentsFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,docs )
                        .addToBackStack( null )
                        .commit();


            }
        } );

        rel_update_pass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordFragment pass = new ChangePasswordFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,pass )
                        .addToBackStack( null )
                        .commit();
            }
        } );

        rel_edit_profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle(  );
                args.putString( "name",name );
                args.putString( "f_name",franchise );
                args.putString( "mobile" , mobile_no );
                args.putString( "address",address );
                args.putString( "email",email );
                args.putString( "type",type );

                EditProfileFragment edit = new EditProfileFragment();
                edit.setArguments( args );
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,edit )
                        .addToBackStack( null )
                        .commit();
            }
        } );

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
        return view;
    }

    private void getStatus(String id) {

        String json_tag="json_get_status";
        HashMap<String,String> map=new HashMap<>();
        map.put("user_id",id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.GET_CURRENT_STATUS, map, new Response.Listener<JSONObject>() {
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
}
