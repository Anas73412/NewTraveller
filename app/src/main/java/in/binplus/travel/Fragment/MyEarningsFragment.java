package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.binplus.travel.Config.Module;
import in.binplus.travel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyEarningsFragment extends Fragment {
    ProgressDialog loadingBar ;
    Module module ;


    public MyEarningsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Earnings");
        loadingBar = new ProgressDialog(  getActivity() );
        loadingBar.setMessage( "loading" );
        module = new Module( getActivity() );

       View view =inflater.inflate( R.layout.fragment_my_earnings, container, false );
       return  view ;
    }

}
