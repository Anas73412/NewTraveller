package in.binplus.travel.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import de.hdodenhof.circleimageview.CircleImageView;
import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.R;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_EMAIL;
import static in.binplus.travel.Config.Constants.KEY_FRANCHISE_ADD;
import static in.binplus.travel.Config.Constants.KEY_FRANCHISE_NAME;
import static in.binplus.travel.Config.Constants.KEY_IMAGE;
import static in.binplus.travel.Config.Constants.KEY_MOBILE;
import static in.binplus.travel.Config.Constants.KEY_NAME;
import static in.binplus.travel.Config.Constants.KEY_USER_TYPE;

public class MyProfile extends Fragment {
    String type ;
    CircleImageView user_img ;
    TextView user_name , user_mobile , user_email,user_franchise ,user_add ;
    RelativeLayout rel_edit_profile , rel_update_pass ,rel_my_docs;

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
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );


        session_management = new Session_management( getActivity() );
        type = session_management.getUserDetails().get( KEY_USER_TYPE );


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
        return view;
    }
}
