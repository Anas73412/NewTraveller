package in.binplus.travel.Adapter.SeatAdapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Config.Module;
import in.binplus.travel.R;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 01,February,2020
 */
public class SleeperSeaterUpperAdapter extends RecyclerView.Adapter<SleeperSeaterUpperAdapter.ViewHolder> {
    Activity activity;
    int seats;
    String type="";
    int pos=-1;
    Module module;
    ArrayList<String> rm_list;
    ArrayList<String> seat_list=new ArrayList<>();

    public SleeperSeaterUpperAdapter(Activity activity, int seats, ArrayList<String> rm_list) {
        this.activity = activity;
        this.seats = seats;
        this.rm_list = rm_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(activity).inflate(R.layout.row_sleeper_seater_upper,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        for(int i=0; i<rm_list.size();i++)
        {
            String[] s=rm_list.get( i ).split( "" );

            pos=module.getRowReverseData( s[1].toString() );

            if(position == pos)
            {
                int seat_number=Integer.parseInt( s[2].toString() );

                if(seat_number ==1)
                {
                    holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.orange) ) );

                }
                else
                {
                    holder.seat_2.setImageTintList(ColorStateList.valueOf( activity.getResources().getColor(R.color.orange) ) );

                }
            }
            else {

            }

        }


        holder.seat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(module.getExistSeat( rm_list, module.getRowData(position)+"1"))
                {

                }
                else {
                    String seat_no = type + module.getRowData( position ) + "1";
                    if (module.getSeatDeseletct( seat_list, seat_no )) {
                        holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray ) ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, seat_no ) );

                    } else {
                        holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_black) ) );
                        seat_list.add( seat_no );

                    }
                    updateintent();
                }
            }
        });
        holder.seat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (module.getExistSeat( rm_list, module.getRowData( position ) + "2" )) {

                } else {
                    String seat_no = type + module.getRowData( position ) + "2";

                    if (module.getSeatDeseletct( seat_list, seat_no )) {
                        holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray) ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, seat_no ) );

                    } else {
                        holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_black ) ) );

                        seat_list.add( seat_no );
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
        ImageView seat_1,seat_2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            module=new Module(activity);
            seat_1=(ImageView)itemView.findViewById(R.id.seat_1);
            seat_2=(ImageView)itemView.findViewById(R.id.seat_2);
        }
    }

    private void updateintent() {
        Intent updates = new Intent("Seats");
        updates.putExtra("type", seat_list);
        activity.sendBroadcast(updates);
    }

    public ArrayList<String> getSeatList()
    {
        return seat_list;
    }


}
