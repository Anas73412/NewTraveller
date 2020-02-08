package in.binplus.travel.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import in.binplus.travel.Model.AvailableBusesModel;
import in.binplus.travel.Model.CityListModel;
import in.binplus.travel.R;
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

        holder.tv_time.setText(getTimeInFormat(model.getFrom_time()) +" - "+getTimeInFormat(model.getTo_time()));
        holder.tv_comp_name.setText(model.getCompany_name());
        holder.tv_duration.setText(getTimeDuration(getTimeInFormat(model.getFrom_time()),getTimeInFormat(model.getTo_time())));
        holder.tv_price.setText("From : "+activity.getResources().getString(R.string.currency)+model.getSeat_fare());
        holder.tv_seats.setText("available seat : "+model.getTotal_seats());
        holder.tv_type.setText(model.getVehicle_type() +" "+model.getSitting_type());


    }

//    @Override
//    public Filter getFilter() {
//
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String charString = charSequence.toString();
//
//                if (charString.isEmpty()) {
//
//                    mFilteredList = buslist;
//                } else {
//
//                    ArrayList<AvailableBusesModel> filteredList = new ArrayList<>();
//
//                    for (AvailableBusesModel busesModel : buslist) {
//
//                        if (busesModel.getDate().toLowerCase().contains( charString ))
//
//                        {
//                            filteredList.add( busesModel);
//                        }
//                    }
//
//                    mFilteredList = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mFilteredList;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                mFilteredList = (ArrayList<AvailableBusesModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    @Override
    public int getItemCount() {
        return buslist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_time , tv_duration , tv_price , tv_comp_name , tv_seats,tv_type;

        public viewHolder(@NonNull View itemView) {
            super( itemView );
            tv_time = itemView.findViewById( R.id.tv_time);
            tv_duration = itemView.findViewById( R.id.tv_duration );
            tv_price = itemView.findViewById( R.id.tv_price );
            tv_comp_name = itemView.findViewById( R.id.tv_comp_name );
            tv_seats = itemView.findViewById( R.id.tv_seats );
            tv_type = itemView.findViewById(R.id.tv_type );

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
