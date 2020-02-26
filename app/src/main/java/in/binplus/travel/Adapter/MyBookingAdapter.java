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

import in.binplus.travel.Fragment.BookingDetails;
import in.binplus.travel.Model.BookingDetailsModel;
import in.binplus.travel.Model.MyBookingModel;
import in.binplus.travel.R;

public class MyBookingAdapter extends RecyclerView.Adapter<MyBookingAdapter.ViewHolder> {
    ArrayList<BookingDetailsModel> bookinglist ;
    Activity activity ;

    public MyBookingAdapter(ArrayList<BookingDetailsModel> bookinglist, Activity activity) {
        this.bookinglist = bookinglist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( activity ).inflate( R.layout.booking_history_layout,null );
        ViewHolder holder = new ViewHolder( view );

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookingDetailsModel model = new BookingDetailsModel();
        model = bookinglist.get( position );

        holder.txt_from.setText( model.getStart_from());
         holder.txt_to.setText(model.getEnd_to() );
         holder.txt_booking_id.setText("#"+ model.getBooking_id() );

         holder.txt_pass_name.setText( model.getB_name() );
         holder.txt_mobile.setText( model.getMobile() );


//        holder.txt_bus_name.setText(model.getVehicle_no());
        holder.txt_date.setText( model.getJourney_startdate());

        holder.txt_price.setText(activity.getResources().getString(R.string.currency)+ ""+model.getTotal_money() );
        int sts = Integer.parseInt( model.getStatus() );
        if (sts==0)
        {
           holder.txt_status.setText( "Pending" );
        }
        else if (sts ==1)
        {
            holder.txt_status.setText( "Confirmed" );
            holder.txt_status.setTextColor( Color.GREEN);
        }
        else if (sts==2)
        {
            holder.txt_status.setText( "Cancelled" );
            holder.txt_status.setTextColor( Color.RED );
        }
        else
        {

        }
    }

    @Override
    public int getItemCount() {
        return bookinglist.size();
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
