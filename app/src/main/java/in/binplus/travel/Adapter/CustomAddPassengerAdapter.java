package in.binplus.travel.Adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.AddPassengerDetails;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.R;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 01,February,2020
 */
public class CustomAddPassengerAdapter extends RecyclerView.Adapter<CustomAddPassengerAdapter.ViewHolder> {
    Activity activity;

    ArrayList<String> seatList;
   ArrayList<String> p_list;
    public CustomAddPassengerAdapter(Activity activity, ArrayList<String> seatList) {
        this.activity = activity;
        this.seatList = seatList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.row_add_passenger,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (position==0)
        {
            holder.itemView.setVisibility(View.GONE );
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
          //  AddPassengerDetails.passengerList.add( 0, new AddPassengerToSeatModel( AddPassengerDetails.getname, AddPassengerDetails.getage, AddPassengerDetails.getgender, AddPassengerDetails.getnationality,AddPassengerDetails.getseatno ) );
        }
        else
        {
            holder.seat_no.setText(seatList.get(position));

        }


    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public EditText et_name;
        public EditText et_age;
        public TextView seat_no;

        public EditText et_nationality;
       public RadioButton male,female;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            p_list=new ArrayList<>();
            seat_no=(TextView)itemView.findViewById(R.id.seat_no);
            et_name=(EditText) itemView.findViewById(R.id.et_name);
            et_age=(EditText) itemView.findViewById(R.id.et_age);
            et_nationality=(EditText) itemView.findViewById(R.id.et_nationality);
            male=(RadioButton)itemView.findViewById(R.id.male);
            female=(RadioButton)itemView.findViewById(R.id.female);

        }
    }

    public ArrayList<String> getPassengerData()
    {
        return p_list;
    }
}
