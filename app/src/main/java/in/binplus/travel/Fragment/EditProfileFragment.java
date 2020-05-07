package in.binplus.travel.Fragment;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import in.binplus.travel.AppController;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Config.Constants;
import in.binplus.travel.Config.Module;
import in.binplus.travel.MainActivity;
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


public class EditProfileFragment extends Fragment {
    String type , name ,f_name ,add ,email , mobile ;
    EditText et_name ,et_f_name ,et_add,et_email ,et_mobile ;
    Button btnUpdate ;
    String user_id ;
    Session_management session_management;
    ProgressDialog loadingBar ;
    Module module ;

        public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate( R.layout.fragment_edit_profile, container, false );

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Edit Profile");

        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "Loading" );
      type = getArguments().getString( "type" );
      name = getArguments().getString( "name" );
      f_name = getArguments().getString( "f_name" );
      add = getArguments().getString( "add" );
      email = getArguments().getString( "email" );
      mobile =getArguments().getString( "mobile" );
      session_management = new Session_management( getActivity() );
      user_id = session_management.getUserDetails().get( KEY_ID );

      et_name = view.findViewById( R.id.et_name );
      et_f_name = view.findViewById( R.id.et_f_name );
      et_add = view.findViewById( R.id.et_address );
      et_email =view.findViewById( R.id.et_email );
      et_mobile=view.findViewById( R.id.et_mobile );
      btnUpdate = view.findViewById( R.id.btnUpdate );


      et_name.setText( name );
      et_f_name.setText( f_name );
      et_add.setText( add );
      et_email.setText( email );
      et_mobile.setText( mobile );

      btnUpdate.setOnClickListener( new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String getname = et_name.getText().toString();
              String getfname = et_f_name.getText().toString();
              String getadd =et_add.getText().toString();
              String getemail = et_email.getText().toString();
              String getmobile = et_mobile.getText().toString();

                    if (!(getemail.isEmpty()))
            {
                  if (!(getemail.contains( "@" )))
                  {
                      et_email.requestFocus();
                      et_email.setError( "Enter Valid Email Address" );
                  }
                  else if (!(getemail.contains( "." )))
                  {
                      et_email.requestFocus();
                      et_email.setError( "Enter Valid Email Address" );
                  }
            }
                    else {

                    }

            if (getname.isEmpty())
            {
                et_name.setError( "Enter Name" );
                et_name.requestFocus();
            }

            else if (getfname.isEmpty())
            {
                et_f_name.setError( "Enter Franchise Name" );
                et_f_name.requestFocus();
            }
            else if (getmobile.isEmpty())
            {
                et_mobile.setError( "Enter Mobile No" );
                et_mobile.requestFocus();
            }

            else if (!(getmobile.length()==10))
             {
                 et_mobile.setError( "Enter Valid Mobile Number" );
                 et_mobile.requestFocus();
             }

            else if (getadd.isEmpty())
            {
                et_add.setError( "Enter Address" );
                et_add.requestFocus();
            }
//

             else
             {
               editProfileRequest( getname,getfname,getemail,getmobile,getadd );
             //   Toast.makeText( getActivity(),"Updated",Toast.LENGTH_LONG ).show();
             }


          }
      } );


      return view ;
    }

   public void editProfileRequest(final String name , final String f_name, final String email, final String mobile, final String address)
   {
       loadingBar.show();
       HashMap<String,String> params = new HashMap<>(  );
       params.put( "name",name );
       params.put( "agent_name",f_name );
       params.put( "email",email );
       params.put( "mobile",mobile );
       params.put( "address",address );
       params.put( "user_id",user_id );

       CustomVolleyJsonRequest jsonRequest = new CustomVolleyJsonRequest( Request.Method.POST, BaseURL.EDIT_PROFILE_URL, params,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       try {
                           loadingBar.dismiss();
                           Boolean status = response.getBoolean( "responce" );
                           String msg = response.getString( "message" );
                           if(status)
                           {
                               session_management.updateProfile( "",name,"",f_name,address,mobile,email );
                               Toast.makeText( getActivity(),""+msg,Toast.LENGTH_LONG ).show();

                           }
                           else
                           {
                               Toast.makeText( getActivity(),""+msg,Toast.LENGTH_LONG ).show();
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
//                       loadingBar.dismiss();

                       Toast.makeText( getActivity(),""+response,Toast.LENGTH_LONG ).show();

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
               } );

       AppController.getInstance().addToRequestQueue( jsonRequest );

   }

}
