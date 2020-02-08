package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import in.binplus.travel.Fragment.OtpGenerateFragment;

public class VerificationActivity extends AppCompatActivity {

    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        type=getIntent().getStringExtra("type");
        initView();
    }

    private void initView() {
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_verification));
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        OtpGenerateFragment fm=new OtpGenerateFragment();
        fm.setArguments(bundle);
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().add(R.id.container_verification,fm).addToBackStack("null").commit();



    }

    @Override
    public void onBackPressed() {
        Fragment fm=getSupportFragmentManager().findFragmentById(R.id.container_verification);
        if(fm instanceof OtpGenerateFragment)
        {
            finish();
        }
        else
        {
            super.onBackPressed();
        }

    }
}
