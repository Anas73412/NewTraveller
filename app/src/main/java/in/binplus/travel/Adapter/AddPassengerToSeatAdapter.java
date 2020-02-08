package in.binplus.travel.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.R;

public class AddPassengerToSeatAdapter extends RecyclerView.Adapter<AddPassengerToSeatAdapter.ViewHolder> {
    Activity activity;
    ArrayList<AddPassengerToSeatModel> passenger_list ;

    public AddPassengerToSeatAdapter(Activity activity, ArrayList<AddPassengerToSeatModel> passenger_list) {
        this.activity = activity;
        this.passenger_list = passenger_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate( R.layout.row_passenger_details,null );
        ViewHolder viewHolder = new ViewHolder( view );
        return  viewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        AddPassengerToSeatModel model = passenger_list.get( position );
        holder.p_age.setText( model.getAge() );
        holder.p_name.setText( model.getPassenger_name() );
//        holder.seat_no.setText( model.getSeat_no() );
        holder.p_nationality.setText( model.getNationality() );

        String getgender = model.getGender();
        if (getgender.equals( "female" ))
        {
            holder.p_age.setTextColor(activity.getResources().getColor( R.color.purple ) );
            holder.p_name.setTextColor(activity.getResources().getColor( R.color.purple ) );
            holder.seat_no.setTextColor(activity.getResources().getColor( R.color.purple ) );
            holder.p_nationality.setTextColor(activity.getResources().getColor( R.color.purple ) );
        }
        else if (getgender.equals( "male" ))
        {
            holder.p_age.setTextColor(activity.getResources().getColor( R.color.color_2 ) );
            holder.p_name.setTextColor(activity.getResources().getColor( R.color.color_2) );
            holder.seat_no.setTextColor(activity.getResources().getColor( R.color.color_2 ) );
            holder.p_nationality.setTextColor(activity.getResources().getColor( R.color.color_2 ) );
        }

        holder.img_close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passenger_list.remove( position );
                notifyDataSetChanged();
            }
        } );

    }

    @Override
    public int getItemCount() {
        return passenger_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView seat_no , p_name ,p_age ,p_nationality;
        ImageView img_close ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            seat_no = itemView.findViewById( R.id.seat_no );
            p_name = itemView.findViewById( R.id.pass_name );
            p_age = itemView.findViewById( R.id.pass_age );
            p_nationality = itemView.findViewById( R.id.pass_nationality );
            img_close = itemView.findViewById( R.id.close );

        }
    }
}
