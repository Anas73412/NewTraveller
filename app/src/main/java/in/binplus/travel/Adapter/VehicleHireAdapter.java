package in.binplus.travel.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Model.VehicleHireModel;
import in.binplus.travel.R;

public class VehicleHireAdapter extends RecyclerView.Adapter<VehicleHireAdapter.viewHolder> {
   Context context;
    ArrayList<VehicleHireModel> vehicleList ;

    public VehicleHireAdapter(Context context, ArrayList<VehicleHireModel> vehicleList) {
        this.context = context;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public VehicleHireAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.row_vehicle,null );
        viewHolder viewHolder = new viewHolder( view );

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHireAdapter.viewHolder holder, int position) {
        VehicleHireModel model = vehicleList.get( position );
        holder.txt_name.setText( model.getName() );
        holder.txt_desc.setText( model.getDescription() );
        holder.vehicle_img.setBackgroundResource( model.getImage());


    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt_name ,txt_desc ;
        ImageView vehicle_img ;

        public viewHolder(@NonNull View itemView) {
            super( itemView );
            txt_name = itemView.findViewById( R.id.vehicle_name );
            txt_desc = itemView.findViewById( R.id.vehicle_desc );
            vehicle_img = itemView.findViewById( R.id.vehicle_img );
        }
    }
}
