package in.binplus.travel.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Model.CarEnquiryHistroyModel;
import in.binplus.travel.R;

public class CarBookingHistoryAdapter extends RecyclerView.Adapter<CarBookingHistoryAdapter.ViewHolder> {
    ArrayList<CarEnquiryHistroyModel> car_list ;
    Activity activity ;

    public CarBookingHistoryAdapter(ArrayList<CarEnquiryHistroyModel> car_list, Activity activity) {
        this.car_list = car_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from( activity ).inflate( R.layout.booking_history_layout,null);
       ViewHolder holder = new ViewHolder( view );
       return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CarEnquiryHistroyModel model = car_list.get( i );

        viewHolder.txt_booking_id.setText( model.getEnquiry_id() );
        viewHolder.txt_date.setText( model.getJourney_date() );
        viewHolder.txt_from.setText( model.getFrom_location() );
        viewHolder.txt_to.setText( model.getTo_locations() );
        viewHolder.txt_pass_name.setText( model.getName() );
        viewHolder.txt_mobile.setText( model.getMobile_no() );

        int sts = Integer.parseInt( model.getStatus() );
        if (sts==0)
        {
           viewHolder.txt_status.setText( "Pending" );
           viewHolder.txt_status.setTextColor( Color.CYAN );
        }
        else if (sts ==1)
        {
            viewHolder.txt_status.setText( "Confirmed" );
           viewHolder.txt_status.setTextColor( Color.GREEN);
        }
        else if (sts==2)
        {
            viewHolder.txt_status.setText( "Cancelled" );
            viewHolder.txt_status.setTextColor( Color.RED );
        }

    }

    @Override
    public int getItemCount() {
        return car_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_from,txt_to,txt_booking_id,txt_date ,txt_price ,txt_mobile ,txt_pass_name  ,txt_status;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            txt_from = itemView.findViewById( R.id.txt_from );
            txt_to = itemView.findViewById( R.id.txt_to );
            txt_date = itemView.findViewById( R.id.txt_date );
            txt_booking_id = itemView.findViewById( R.id.txt_booking_id );
            txt_mobile = itemView.findViewById( R.id.txt_mobile );
            txt_price = itemView.findViewById( R.id.txt_price );
            txt_pass_name= itemView.findViewById( R.id.txt_pass_name );
            txt_status = itemView.findViewById( R.id.txt_status );
        }
    }
}
