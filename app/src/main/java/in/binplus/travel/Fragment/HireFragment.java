package in.binplus.travel.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import in.binplus.travel.Adapter.VehicleHireAdapter;
import in.binplus.travel.HireDetailsActivity;
import in.binplus.travel.Model.VehicleHireModel;
import in.binplus.travel.R;
import in.binplus.travel.util.RecyclerTouchListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HireFragment extends Fragment {
    ArrayList<VehicleHireModel> vehicleList ;
    Activity activity ;
    RecyclerView vehicle_recycler ;
    VehicleHireAdapter vehicleHireAdapter ;

    public HireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate( R.layout.fragment_hire, container, false );
       vehicle_recycler = view.findViewById( R.id.recylcer_vehicle );
       vehicleList = new ArrayList<>(  );


       vehicleList.add( new VehicleHireModel( "Buses",R.drawable.bus,"suitable for 20 people") );
        vehicleList.add( new VehicleHireModel( "Traveller",R.drawable.van,"suitable for 16 people") );
        vehicleList.add( new VehicleHireModel( "Suvs",R.drawable.suvcar,"suitable for 6-8 people") );
        vehicleList.add( new VehicleHireModel( "Cars",R.drawable.car,"suitable for 4-5 people") );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager( activity,2 );
        vehicle_recycler.setLayoutManager( gridLayoutManager);
        vehicleHireAdapter = new VehicleHireAdapter(getActivity(),vehicleList);
        vehicle_recycler.setAdapter( vehicleHireAdapter );

        vehicle_recycler.addOnItemTouchListener( new RecyclerTouchListener( getActivity(), vehicle_recycler, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent( getActivity(), HireDetailsActivity.class );
                intent.putExtra( "type",vehicleList.get( position ).getName() );
                startActivity( intent );

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        } ) );





       return view ;
    }

}
