package in.binplus.travel.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import in.binplus.travel.R;

public class WalletFragment extends Fragment {
    CardView card_total_earning , card_credit_limit ,card_req_recharge,card_recharge_histry ,card_trans_history ,card_today_booking ,card_pending_amt ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_wallet,container,false );
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Wallet");
        card_total_earning = view.findViewById( R.id.card_total_earnings );
        card_credit_limit = view.findViewById( R.id.card_credit_limit );
        card_req_recharge = view.findViewById( R.id.card_recharge_req );
        card_recharge_histry = view.findViewById( R.id.card_recharge_history );
        card_trans_history = view.findViewById( R.id.card_trans_history );
        card_today_booking = view.findViewById( R.id.card_todays_booking );
        card_pending_amt = view.findViewById( R.id.card_pending_amount );

        card_pending_amt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );
        card_total_earning.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );
        card_credit_limit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        } );
        card_req_recharge.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               RequestRechargeFragment req =new RequestRechargeFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,req )
                        .addToBackStack( null ).commit();

            }
        } );
        card_recharge_histry.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               RechargeHistoryFragment recharge =new RechargeHistoryFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,recharge )
                        .addToBackStack( null ).commit();

            }
        } );
        card_trans_history.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionHistory trans =new TransactionHistory();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,trans )
                        .addToBackStack( null ).commit();

            }
        } );

        card_today_booking.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             TodaysBookingFragment today =new TodaysBookingFragment();
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace( R.id.container_frame,today )
                        .addToBackStack( null ).commit();

            }
        } );
        return view;
    }
}
