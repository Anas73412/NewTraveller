package in.binplus.travel.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.binplus.travel.Model.AvailableBusesModel;
import in.binplus.travel.Model.CityListModel;
import in.binplus.travel.R;
import in.binplus.travel.SelectSeatActivity;
import in.binplus.travel.util.ToastMsg;

public class AvailableBusesAdapter extends RecyclerView.Adapter<AvailableBusesAdapter.viewHolder> {
    ArrayList<AvailableBusesModel> buslist ;
    private ArrayList<AvailableBusesModel> mFilteredList;
    Activity activity ;


    public AvailableBusesAdapter(ArrayList<AvailableBusesModel> buslist, Activity activity) {
        this.buslist = buslist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( activity ).inflate( R.layout.row_available_buses,null );
        viewHolder holder = new viewHolder( view );

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        AvailableBusesModel model = buslist.get( position );


        holder.tv_comp_name.setText(model.getVehicle_name());
        if (model.getV_type().equalsIgnoreCase( "car" )) {
            holder.tv_time.setVisibility( View.GONE );
            holder.tv_duration.setVisibility( View.GONE );
        }
        else {

        if (model.getFrom_time().equals( "" ) || model.getTo_time().equals( "" )) {

            holder.tv_time.setText( model.getVehicle_name() );
            holder.tv_duration.setVisibility( View.GONE );
        }

             else {

                holder.tv_time.setText( getTimeInFormat( model.getFrom_time() ) + " - " + getTimeInFormat( model.getTo_time() ) );
                holder.tv_duration.setText( getTimeDuration( getTimeInFormat( model.getFrom_time() ), getTimeInFormat( model.getTo_time() ) ) );
            }
        }

        holder.tv_price.setText("From : "+activity.getResources().getString(R.string.currency)+model.getSeat_fare());
        holder.tv_seats.setText("available seats : "+model.getTotal_seats());
        holder.tv_type.setText(model.getVehicle_type() +" "+model.getSitting_type());

//        holder.rippleLayout.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( activity, SelectSeatActivity.class );
//                activity.startActivity( intent );
//            }
//        }  );
    }

//

    @Override
    public int getItemCount() {
        return buslist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_time , tv_duration , tv_price , tv_comp_name , tv_seats,tv_type ;
        MaterialRippleLayout  rippleLayout ;

        public viewHolder(@NonNull View itemView) {
            super( itemView );
            tv_time = itemView.findViewById( R.id.tv_time);
            tv_duration = itemView.findViewById( R.id.tv_duration );
            tv_price = itemView.findViewById( R.id.tv_price );
            tv_comp_name = itemView.findViewById( R.id.tv_comp_name );
            tv_seats = itemView.findViewById( R.id.tv_seats );
            tv_type = itemView.findViewById(R.id.tv_type );
            rippleLayout = itemView.findViewById( R.id.ripple );

        }
    }

    public String getTimeInFormat(String s)
    {
        String[] arr_s=s.split(":");
        String timeInFormat=arr_s[0]+":"+arr_s[1];
        return timeInFormat;
    }

    public String getTimeDuration(String time_first,String time_second){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        int days=0;
        int hours=0;
        int min=0;
        try {

            Date startDate = simpleDateFormat.parse(time_first);
            Date endDate = simpleDateFormat.parse(time_second);

            long difference = endDate.getTime() - startDate.getTime();
            if(difference<0)
            {
                Date dateMax = simpleDateFormat.parse("24:00");
                Date dateMin = simpleDateFormat.parse("00:00");
                difference=(dateMax.getTime() -startDate.getTime() )+(endDate.getTime()-dateMin.getTime());
            }
             days = (int) (difference / (1000*60*60*24));
             hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
             min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

        }
        catch (Exception ex)
        {
            new ToastMsg(activity).toastIconError(ex.getMessage());
        }
        Log.i("log_tag","Hours: "+hours+", Mins: "+min);
        String d=hours+"h  "+min+"m";
        return d;
    }
}
