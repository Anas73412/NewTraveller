package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Model.SeatModel;
import in.binplus.travel.R;

public class SelectedSeatAdapter extends RecyclerView.Adapter<SelectedSeatAdapter.ViewHolder> {
    ArrayList<SeatModel> seatlist ;
    Activity activity ;

    public SelectedSeatAdapter(ArrayList<SeatModel> seatlist, Activity activity) {
        this.seatlist = seatlist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SelectedSeatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from( activity ).inflate( R.layout.row_selected_seat,null );
       ViewHolder holder = new ViewHolder( view );
       return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedSeatAdapter.ViewHolder holder, int position) {
        SeatModel seatmodel = seatlist.get( position );
        holder.txt_seat_id.setText( seatmodel.getSeat_no() );
        String seat_type = seatmodel.getSeat_type();
        if (seat_type.equals( "sleeper" ))
        {
          holder.img_seat.setImageResource( R.drawable.sleeper );


        }
        else if (seat_type.equals( "seater" ))
        {
            holder.img_seat.setImageResource( R.drawable.armchair_32px );
        }

    }

    @Override
    public int getItemCount() {
        return seatlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_seat_id ;
        ImageView img_seat ;
      public static RadioButton radio_seat ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            txt_seat_id = itemView.findViewById( R.id.seat_no );
            radio_seat =itemView.findViewById( R.id.radio_seat );
            img_seat = itemView.findViewById( R.id.seat_img );



        }
    }
}
