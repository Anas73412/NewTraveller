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
import in.binplus.travel.Config.Module;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.R;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 01,February,2020
 */
public class CustomAddPassengerAdapter extends RecyclerView.Adapter<CustomAddPassengerAdapter.ViewHolder> {
    Activity activity;

    ArrayList<String> seatList;
    ArrayList<String> p_list;
    String v_type ;
    int tot_seats ;
    Module module;

    public CustomAddPassengerAdapter(Activity activity, ArrayList<String> seatList, String v_type, int tot_seats) {
        this.activity = activity;
        this.seatList = seatList;
        this.v_type = v_type;
        this.tot_seats = tot_seats;
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
            if (v_type.equals("bus")) {
                holder.seat_no.setText(module.removeF(seatList.get(position)));
                holder.et_mobile.setVisibility(View.GONE);
            }
            else
            {
                holder.seat_no.setText("");
                holder.txt_seat.setText("");

            }
            if(seatList.get(position).contains("F"))
            {
                holder.male.setEnabled(false);
                holder.female.setChecked(true);
            }
        }


    }

    @Override
    public int getItemCount() {
        int size ;
        if (v_type.equals("bus")) {
            size= seatList.size();
        }
        else
        {
            size = tot_seats;
        }
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public EditText et_name;
        public EditText et_age ,et_mobile;
        public TextView seat_no ,txt_seat;

        public EditText et_nationality;
        public RadioButton male,female;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            p_list=new ArrayList<>();
            seat_no=(TextView)itemView.findViewById(R.id.seat_no);
            txt_seat=(TextView)itemView.findViewById(R.id.txt_seat);
            et_name=(EditText) itemView.findViewById(R.id.et_name);
            et_age=(EditText) itemView.findViewById(R.id.et_age);
            et_mobile=(EditText) itemView.findViewById(R.id.et_mobile);
            et_nationality=(EditText) itemView.findViewById(R.id.et_nationality);
            male=(RadioButton)itemView.findViewById(R.id.male);
            female=(RadioButton)itemView.findViewById(R.id.female);
            module=new Module(activity);

        }
    }

    public ArrayList<String> getPassengerData()
    {
        return p_list;
    }
}
