package in.binplus.travel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.NetworkError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import in.binplus.travel.Config.BaseURL;
import in.binplus.travel.Fragment.AboutUsFragment;
import in.binplus.travel.Fragment.ContactUsFragment;
import in.binplus.travel.Fragment.HomeFragment;
import in.binplus.travel.Fragment.MyBookingsFragment;
import in.binplus.travel.Fragment.MyProfile;
import in.binplus.travel.Fragment.PrivacyFragment;
import in.binplus.travel.Fragment.RestoHomeFragment;
import in.binplus.travel.Fragment.TrmsFragment;
import in.binplus.travel.Fragment.WalletFragment;
import in.binplus.travel.util.ConnectivityReceiver;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_EMAIL;
import static in.binplus.travel.Config.Constants.KEY_IMAGE;
import static in.binplus.travel.Config.Constants.KEY_MOBILE;
import static in.binplus.travel.Config.Constants.KEY_NAME;
import static in.binplus.travel.Config.Constants.KEY_USER_TYPE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ConnectivityReceiver.ConnectivityReceiverListener {
    Session_management session_management ;
    String type ;
    private Menu nav_menu;
    public static ImageView iv_profile;
    TextView tv_name ,tv_mobile ;
    String previouslyEncodedImage="";

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = findViewById( R.id.toolbar );

        session_management = new Session_management(MainActivity.this);
        setSupportActionBar( toolbar );
        type = session_management.getUserDetails().get(KEY_USER_TYPE );
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        tv_name = (TextView)header.findViewById(R.id.user_name);
        tv_mobile= (TextView)header.findViewById( R.id.user_mobile );


         drawer = findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener( MainActivity.this );
        navigationView.bringToFront();
        updateHeader();

        iv_profile = (ImageView) header.findViewById(R.id.user_img);

        String getimage=session_management.getUpdateProfile().get( KEY_IMAGE);
        if (!TextUtils.isEmpty(getimage)) {


            Glide.with( MainActivity.this )
                    .load( BaseURL.IMG_PROFILE_URL + getimage)
                    .fitCenter()
                    .placeholder( R.drawable.logo )
                    .crossFade()
                    .diskCacheStrategy( DiskCacheStrategy.ALL )
                    .dontAnimate()
                    .into( iv_profile );
        }

        if (type.equals( "f" )) {
            HomeFragment homeFragment=new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,homeFragment )
//                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                    .commit();
        }
        else if (type.equals( "r" ))
        {
            RestoHomeFragment restoHomeFragment = new RestoHomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,restoHomeFragment )
//                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                    .commit();
        }


    }

    public void updateHeader() {
        if (session_management.isLoggedIn()) {

            String getname = session_management.getUserDetails().get( KEY_NAME );
            String getimage = session_management.getUserDetails().get(KEY_IMAGE );
            String getemail = session_management.getUserDetails().get(KEY_EMAIL );
            String getmobile = session_management.getUserDetails().get( KEY_MOBILE );
            SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences( this );
            previouslyEncodedImage = shre.getString( "image_data", "" );
            if (!previouslyEncodedImage.equalsIgnoreCase( "" )) {
                byte[] b = Base64.decode( previouslyEncodedImage, Base64.DEFAULT );
                Bitmap bitmap = BitmapFactory.decodeByteArray( b, 0, b.length );
                iv_profile.setImageBitmap( bitmap );
            }

            if (!TextUtils.isEmpty( getimage )) {


                Glide.with( MainActivity.this )
                        .load( BaseURL.IMG_PROFILE_URL + getimage )
                        .fitCenter()
                        .placeholder( R.drawable.logo )
                        .crossFade()
                        .diskCacheStrategy( DiskCacheStrategy.ALL )
                        .dontAnimate()
                        .into( iv_profile );
            }
            tv_name.setText( getname );
            tv_mobile.setText( getmobile );

        } else {
            //linearLayout_login.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        drawer.closeDrawers();
        if(id == R.id.nav_home)
        {
            if (type.equalsIgnoreCase( "f" )) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame, homeFragment )
                        .addToBackStack( null )
                        .commit();
            }
            else if (type.equalsIgnoreCase( "r" ))
            {
               RestoHomeFragment homeFragment = new RestoHomeFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame, homeFragment )
                        .addToBackStack( null )
                        .commit();
            }

        }
        else if(id == R.id.nav_gallery)
        {
            WalletFragment walletFragment=new WalletFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,walletFragment ).addToBackStack( null ).commit();
        }
        else if (id ==R.id.nav_profiel )
        {
            MyProfile profile =new MyProfile();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,profile ).addToBackStack( null ).commit();
        }
        else if (id ==R.id.nav_Bookings )
        {
            MyBookingsFragment bookingsFragment =new MyBookingsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,bookingsFragment ).addToBackStack( null ).commit();
        }
        else if (id == R.id.nav_logout)
        {

            AlertDialog.Builder builder=new AlertDialog.Builder(this);

            builder.setMessage("LOGOUT?")
                    .setCancelable(false)
                    .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            session_management.logoutSession();
                            finish();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        else if (id == R.id.nav_privacy) {
            PrivacyFragment privacy = new PrivacyFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,privacy ).addToBackStack( null ).commit();

        }
        else if (id == R.id.nav_aboutus) {
            AboutUsFragment aboutus = new AboutUsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,aboutus ).addToBackStack( null ).commit();

        }
        else if (id == R.id.nav_terms) {
            TrmsFragment terms = new TrmsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,terms ).addToBackStack( null ).commit();

        }
        else if (id == R.id.nav_contact) {
            ContactUsFragment contact = new ContactUsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace( R.id.container_frame,contact ).addToBackStack( null ).commit();

        }

        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
        private void showSnack(boolean isConnected) {
            String message;
            int color;

            if (!isConnected) {
                Intent intent = new Intent(MainActivity.this, NetworkError.class);
                startActivity(intent);
            }

    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_frame);

        if (f instanceof HomeFragment) {
//            finish();
//            System.exit(0);
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

            builder.setIcon( R.drawable.logo );
            builder.setMessage("Are you sure want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finishAffinity();
                    //  getActivity().finishAffinity();


                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog dialog=builder.create();
            dialog.show();

        } else {
            super.onBackPressed();
        }

    }
}
