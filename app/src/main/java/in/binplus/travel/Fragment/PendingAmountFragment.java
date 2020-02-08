package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.binplus.travel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingAmountFragment extends Fragment {
    ProgressDialog loadingBar ;


    public PendingAmountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pending Amount");
        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage( "loading" );
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_pending_amount, container, false );
    }

}
