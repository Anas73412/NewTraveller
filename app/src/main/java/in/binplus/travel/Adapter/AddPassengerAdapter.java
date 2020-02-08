package in.binplus.travel.Adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import in.binplus.travel.AddPassengerDetails;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.Model.PassengerDetailsModel;
import in.binplus.travel.R;
import in.binplus.travel.util.ToastMsg;

public class AddPassengerAdapter extends RecyclerView.Adapter<AddPassengerAdapter.ViewHolder> {
    Activity activity;
    ArrayList<String>seatlist ;
    String getname ="";
    String getage = "";
    String getnationality = "" ;
    String getgender = "";

    public AddPassengerAdapter(Activity activity, ArrayList<String> seatlist) {
        this.activity = activity;
        this.seatlist = seatlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( activity ).inflate( R.layout.row_add_passenger ,null);
        ViewHolder holder = new ViewHolder( view );
        return  holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
       // String model = seatlist.get( position);
        if (position==0)
        {
            holder.itemView.setVisibility(View.GONE );
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
         //  AddPassengerDetails.passengerList.add( 0, new AddPassengerToSeatModel( AddPassengerDetails.getname, AddPassengerDetails.getage, AddPassengerDetails.getgender, AddPassengerDetails.getnationality,AddPassengerDetails.getseatno ) );
        }
        else
        {
            holder.txt_seat_no.setText( "Seat No: " + seatlist.get( position ) );

        }



    }

    @Override
    public int getItemCount() {
        return seatlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static EditText et_name ,et_age, et_nationality;
        public static TextView txt_seat_no ;
        public static RadioButton radio_male ,radio_female ;
        public static LinearLayout linear_layout;
       public static String getname="" ;
        public static String getage="";
        public static String getnationality ="" ;
        public static String getgender="";
        public static String getseatno="";

        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            txt_seat_no = itemView.findViewById( R.id.seat_no );
            et_name = itemView.findViewById( R.id.et_name );
            et_age =itemView.findViewById( R.id.et_age );
            et_nationality =itemView.findViewById( R.id.et_nationality );
            radio_female = itemView.findViewById( R.id.female );
            radio_male = itemView.findViewById( R.id.male );
            linear_layout = itemView.findViewById( R.id.linear_passenger );

        }

        public static  void getData(int position)
        {
            int pos = position ;
            getname =et_name.getText().toString();
            getage = et_age.getText().toString();
            getgender = et_age.getText().toString();
            getnationality = et_nationality.getText().toString();
            getseatno = txt_seat_no.getText().toString();

            if( getname.equals( "" ) )
            {
                et_name.setError( "Enter name" );
                et_name.requestFocus();
//                new ToastMsg(get).toastIconError( "Enter passenger details" );
            }
            else if(getage.equals( "" ))
            {
                et_age.setError( "Enter age" );
                et_age.requestFocus();
            }

            else
            {
                 if (getgender.equals( "" ))
                      {
//                          new ToastMsg(activity).toastIconError( "Enter passenger details" );
                     }
                 else
                     {
                 //    AddPassengerDetails.passengerList.add(pos, new AddPassengerToSeatModel( getname, getage, getgender, getnationality,getseatno ) );
                    }
            }
        }
    }
}
