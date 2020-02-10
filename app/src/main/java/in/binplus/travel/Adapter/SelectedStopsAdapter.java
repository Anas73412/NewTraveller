package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.CarBookingActivity;
import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.R;

public class SelectedStopsAdapter extends RecyclerView.Adapter<SelectedStopsAdapter.ViewHolder> {
    ArrayList<StopsModel> seatlist ;
    Activity activity ;

    public SelectedStopsAdapter(ArrayList<StopsModel> seatlist, Activity activity) {
        this.seatlist = seatlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SelectedStopsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from( activity ).inflate( R.layout.row_stops,null );
       ViewHolder holder = new ViewHolder( view );
       return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedStopsAdapter.ViewHolder holder, int position) {
        final StopsModel seatmodel = seatlist.get( position );
        holder.txt_seat_id.setText(seatmodel.getStop_name() );

        holder.check_seat.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true)
                {
                    CarBookingActivity.selected_stop_list.add( seatmodel );
                }
                else
                {
                    CarBookingActivity.selected_stop_list.remove( seatmodel );
                }

            }
        } );
    }

    @Override
    public int getItemCount() {
        return seatlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_seat_id ;
        ImageView img_seat ;
       CheckBox check_seat ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            txt_seat_id = itemView.findViewById( R.id.stop_name );
            check_seat =itemView.findViewById( R.id.radio_seat );
//            img_seat = itemView.findViewById( R.id.seat_img );



        }
    }
}
