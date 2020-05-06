package in.binplus.travel.Adapter.SeatAdapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.binplus.travel.Config.Module;
import in.binplus.travel.R;
import in.binplus.travel.util.ToastMsg;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 30,January,2020
 */
public class Seater3x2Adapter extends RecyclerView.Adapter<Seater3x2Adapter.ViewHolder> {
    Activity activity;
    int seats;
    int pos=-1;
    Module module;
    ArrayList<String> rm_list;
    ArrayList<String> seat_list=new ArrayList<>();
    ArrayList<String> femaleList=new ArrayList<>();
    ArrayList<String> resList=new ArrayList<>();

    public Seater3x2Adapter(Activity activity, int seats, ArrayList<String> rm_list,ArrayList<String> femaleList, ArrayList<String> resList) {
        this.activity = activity;
        this.seats = seats;
        this.rm_list = rm_list;
        this.femaleList=femaleList;
        this.resList=resList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.row_sitting_3x2,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        for(int res=0; res<resList.size();res++)
        {
            String[] s=resList.get( res ).split( "" );
//            pos=module.getRowReverseData( s[2].toString() );
            pos=Integer.parseInt(s[2].toString());
            if(position == pos) {
                String seat_number = s[1].toString();

                if (seat_number.equalsIgnoreCase("A")) {
                    holder.seat_1.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color_1)));
                    holder.seat_1.setClickable(false);

                } else if (seat_number.equalsIgnoreCase("B")) {
                    holder.seat_2.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color_1)));
                    holder.seat_2.setClickable(false);
                } else if (seat_number.equalsIgnoreCase("C")) {
                    holder.seat_3.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color_1)));
                    holder.seat_3.setClickable(false);
                } else if (seat_number.equalsIgnoreCase("D")) {
                    holder.seat_4.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color_1)));
                    holder.seat_4.setClickable(false);
                } else {
                    holder.seat_5.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.color_1)));
                    holder.seat_5.setClickable(false);
                }
            }
        }

        for(int i=0; i<rm_list.size();i++)
        {
            String[] s=rm_list.get( i ).split( "" );
//            pos=module.getRowReverseData( s[2].toString() );
            pos=Integer.parseInt(s[2].toString());
            if(position == pos) {
                String seat_number = s[1].toString();

                if(seat_number.equalsIgnoreCase("A"))
                {
                    holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.red_600) ) );
                    holder.seat_1.setClickable(false);

                }
                else if(seat_number.equalsIgnoreCase("B"))
                {
                    holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.red_600) ) );
                    holder.seat_2.setClickable(false);
                }
                else if(seat_number.equalsIgnoreCase("C"))
                {
                    holder.seat_3.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.red_600) ) );
                    holder.seat_3.setClickable(false);
                }else if(seat_number.equalsIgnoreCase("D"))
                {
                    holder.seat_4.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.red_600) ) );
                    holder.seat_4.setClickable(false);
                }
                else
                {
                    holder.seat_5.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.red_600) ) );
                    holder.seat_5.setClickable(false);
                }
            }
            else {

            }

        }

        holder.seat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatString="A"+(position+1);
                if(module.getExistSeat( rm_list, seatString))
                {
                    Log.e("asdas","adsdas");

                }
                else {

                    if (module.getSeatDeseletct( seat_list, seatString)) {
                        holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray) ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, seatString) );

                    } else {
                        holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.green_500 ) ) );
                        seat_list.add( seatString );
                    }
                    updateintent();
                }
            }
        });
        holder.seat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String seatString="B"+(position+1);


                if(module.getExistSeat( rm_list, seatString))
                {

                }
                else {
                    if (module.getSeatDeseletct( seat_list, seatString)) {
                        holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray ) ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, seatString ));

                    } else {
                        holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.green_500 ) ) );
                        seat_list.add( seatString );
                    }
                    updateintent();
                }
            }
        });
         holder.seat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String seatString="C"+(position+1);

                if(module.getExistSeat( rm_list,seatString))
                {

                }
                else {
                    if (module.getSeatDeseletct( seat_list, seatString)) {
                        holder.seat_3.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray) ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, seatString) );

                    } else {
                        holder.seat_3.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.green_500) ) );
                        seat_list.add( seatString );
                    }
                    updateintent();
                }
            }
        });holder.seat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String seatString="D"+(position+1);
                if(module.getExistSeat( rm_list, seatString))
                {

                }
                else {

                    if (module.getSeatDeseletct( seat_list, seatString )) {
                        holder.seat_4.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray ) ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, seatString ) );

                    } else {
                        holder.seat_4.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.green_500 ) ) );
                        seat_list.add( seatString );
                    }
                    updateintent();
                }
            }
        });holder.seat_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String seatString="E"+(position+1);
                if(module.getExistSeat( rm_list, seatString))
                {

                }
                else {

                    if (module.getSeatDeseletct( seat_list, seatString )) {
                        holder.seat_5.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.white ) ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, seatString ) );

                    } else {
                        holder.seat_5.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.green_500 ) ) );
                        seat_list.add(seatString );
                    }
                    updateintent();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView seat_1,seat_2,seat_3,seat_4,seat_5;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            module=new Module(activity);
            seat_1=(ImageView)itemView.findViewById(R.id.seat_1);
            seat_2=(ImageView)itemView.findViewById(R.id.seat_2);
            seat_3=(ImageView)itemView.findViewById(R.id.seat_3);
            seat_4=(ImageView)itemView.findViewById(R.id.seat_4);
            seat_5=(ImageView)itemView.findViewById(R.id.seat_5);
        }
    }


    public ArrayList<String> getSeatList()
    {
        return seat_list;
    }

    private void updateintent() {
        Intent updates = new Intent("Seats");
        updates.putExtra("type", seat_list);
        activity.sendBroadcast(updates);
    }
}
