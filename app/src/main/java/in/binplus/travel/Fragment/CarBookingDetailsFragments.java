package in.binplus.travel.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.binplus.travel.Adapter.PassengerListAdapter;
import in.binplus.travel.Adapter.SelectedStopsAdapter;
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.R;
import in.binplus.travel.util.Session_management;

import static in.binplus.travel.Config.Constants.KEY_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarBookingDetailsFragments extends Fragment {
    RecyclerView recycler_stops ;
    TextView text_to ,txt_from,txt_date,txt_total,txt_busname,txt_busno ,passengers ,cancel_booking ,txt_tot_seats ,txt_pass_name ,txt_pass_mobile;
    Dialog dialog ;
    TextView btn_no ,btn_yes ;
    EditText et_remark ,et_note ;
    String user_id,route ;
    Session_management session_management ;
    ArrayList<StopsModel> stop_list ;
    SelectedStopsAdapter passengerListAdapter ;
    Module module ;
    ProgressDialog loadingBar ;
    LinearLayout linear_note  ;

    String enquiry_id ,vehicle_id ,journey_date ,total_money ,vehicle_type,start_from ,end_to ,enquiry_date,start_date,end_date,vehicle_name,vehicle_number,name ,adhar_id,mobile,note ;



    public CarBookingDetailsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view =  inflater.inflate( R.layout.fragment_car_booking_details_fragments, container, false );
        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_cancel_order_layout);
        dialog.setCanceledOnTouchOutside(false);
        module = new Module( getActivity() );

        loadingBar = new ProgressDialog( getActivity() );
        loadingBar.setMessage("loading" );

        txt_from = view.findViewById( R.id.txt_from );
        text_to=view.findViewById( R.id.txt_to );
        txt_date=view.findViewById( R.id.txt_date );
        txt_total=view.findViewById( R.id.txt_price );
        txt_busname=view.findViewById( R.id.vehicle_name);
        txt_busno = view.findViewById( R.id.vehicle_no );
        cancel_booking = view.findViewById( R.id.cancel_booking );
        passengers = view.findViewById( R.id.passenger_detail );
        txt_tot_seats = view.findViewById( R.id.txt_tot_seats );
        txt_pass_name = view.findViewById( R.id.booking_name );
        txt_pass_mobile =view.findViewById( R.id.txt_mobile );
        recycler_stops = view.findViewById( R.id.recycler_stops );
        session_management = new Session_management( getActivity() );
        user_id = session_management.getUserDetails().get( KEY_ID );


        enquiry_id = getArguments().getString( "enquiry_id" );
        enquiry_date=getArguments().getString( "booking_date" );
        start_from =getArguments().getString( "start_from" );
        end_to =getArguments().getString( "end_to" );
        start_date=getArguments().getString( "journey_startdate" );
        end_date = getArguments().getString( "journey_enddate" );
        total_money=getArguments().getString( "total_money" );
        vehicle_id =getArguments().getString( "vehicles_id" );
        name = getArguments().getString("name");
        adhar_id = getArguments().getString( "adhar_no" );
        mobile = getArguments().getString( "mobile" );
        vehicle_number = getArguments().getString("vehicle_no");
        route = getArguments().getString("route");


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(start_from +"-"+end_to);
          Toast.makeText( getActivity(),"booking_id" +route, Toast.LENGTH_LONG ).show();

        stop_list = new ArrayList<>(  );
     return view ;
    }

}
