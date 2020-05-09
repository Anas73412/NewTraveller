package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.AddPassengerDetails;
import in.binplus.travel.Fragment.BookingDetails;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.Model.PassengerDetailsModel;
import in.binplus.travel.R;

public class PassengerListAdapter extends RecyclerView.Adapter<PassengerListAdapter.ViewHolder> {
    ArrayList<AddPassengerToSeatModel> passenger_list;
    Activity activity ;

    public PassengerListAdapter(ArrayList<AddPassengerToSeatModel> passenger_list, Activity activity) {
        this.passenger_list = passenger_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( activity ).inflate( R.layout.row_passenger,null );
        ViewHolder holder = new ViewHolder( view );
        return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

      AddPassengerToSeatModel model = new AddPassengerToSeatModel();
        model = passenger_list.get( position );
        String gender = model.getGender();
        if (gender.equalsIgnoreCase( "F" ))
        {
            holder.img_icon.setImageResource( R.drawable.icons8_female_profile_96px );
        }
        else if (gender.equalsIgnoreCase( "M" ))
        {
            holder.img_icon.setImageResource( R.drawable.icons8_male_user_96px );
        }

        holder.txt_pname.setText( model.getPassenger_name());
        holder.txt_age.setText("Age : "+ model.getAge());
       holder.txt_seat_no.setText( model.getSeat_no() );
//       if(model.getSeat_price().equalsIgnoreCase("null"))
//       {
//          double tot= Double.parseDouble(BookingDetails.total_money.toString());
//           int singleRent=(int)(tot/passenger_list.size());
//           holder.txt_price.setText(activity.getResources().getString(R.string.currency)+" "+ String.valueOf(singleRent) );
//       }
//       else
//       {
           holder.txt_price.setText(activity.getResources().getString(R.string.currency)+" "+ model.getSeat_price() );
//       }






    }

    @Override
    public int getItemCount() {
        return passenger_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_pname ,txt_age,txt_seat_no ,txt_price ;
        ImageView img_icon ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            txt_pname = itemView.findViewById( R.id.passenger_name );
            txt_age = itemView.findViewById( R.id.passenger_age );
            txt_seat_no= itemView.findViewById( R.id.passenger_seat );
            txt_price = itemView.findViewById( R.id.p_seat_price );
            img_icon = itemView.findViewById( R.id.img );




        }
    }
}
