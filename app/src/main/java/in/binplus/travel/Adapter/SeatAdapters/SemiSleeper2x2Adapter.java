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
import java.util.List;

import in.binplus.travel.Config.Module;
import in.binplus.travel.R;
import in.binplus.travel.util.ToastMsg;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 30,January,2020
 */
public class SemiSleeper2x2Adapter extends RecyclerView.Adapter<SemiSleeper2x2Adapter.ViewHolder> {
    Activity activity;
    int seats;
    int pos=-1;
    ArrayList<String> rm_list;
    Module module;
    ArrayList<String> seat_list=new ArrayList<>();

    public SemiSleeper2x2Adapter(Activity activity, int seats, ArrayList<String> rm_list) {
        this.activity = activity;
        this.seats = seats;
        this.rm_list = rm_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.row_semi_seater_2x2,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        for(int i=0; i<rm_list.size();i++) {
            String[] s = rm_list.get( i ).split( "" );

            pos = module.getRowReverseData( s[1].toString() );

            if (position == pos) {
                int seat_number = Integer.parseInt( s[2].toString() );

                if (seat_number == 1) {
                    holder.seat_1.setBackgroundColor( activity.getResources().getColor( R.color.orange ) );

                } else if (seat_number == 2) {
                    holder.seat_2.setBackgroundColor( activity.getResources().getColor( R.color.orange ) );
                    holder.seat_1.setClickable( false );
                } else if (seat_number == 3) {
                    holder.seat_3.setBackgroundColor( activity.getResources().getColor( R.color.orange ) );
                } else {
                    holder.seat_4.setBackgroundColor( activity.getResources().getColor( R.color.orange ) );
                }
            } else {

            }
        }

        holder.seat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(module.getExistSeat( rm_list, module.getRowData(position)+"1"))
                {

                }
                else {
                if(module.getSeatDeseletct(seat_list,module.getRowData(position)+"1"))
                {
                    holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.white) ) );
                    seat_list.remove(module.getSeatListPosition(seat_list,module.getRowData(position)+"1"));

                }
                else {

                    holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.dark_black) ) );
                    seat_list.add(module.getRowData(position) + "1");
                }
                updateintent();}
            }
        });
        holder.seat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(module.getExistSeat( rm_list, module.getRowData(position)+"2"))
                {

                }
                else {
                if(module.getSeatDeseletct(seat_list,module.getRowData(position)+"2"))
                {
                    holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.dark_gray) ) );
                    seat_list.remove(module.getSeatListPosition(seat_list,module.getRowData(position)+"2"));

                }
                else {

                    holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.dark_black) ) );
                    seat_list.add(module.getRowData(position) + "2");
                }
                updateintent();
            }
            }
        });
         holder.seat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(module.getExistSeat( rm_list, module.getRowData(position)+"3"))
                {

                }
                else {
                    if (module.getSeatDeseletct( seat_list, module.getRowData( position ) + "3" )) {
                        holder.seat_3.setBackgroundColor( activity.getResources().getColor( R.color.white ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, module.getRowData( position ) + "3" ) );

                    } else {

                        holder.seat_3.setBackgroundColor( activity.getResources().getColor( R.color.dark_black) );
                        seat_list.add( module.getRowData( position ) + "3" );
                    }
                    updateintent();
                }
            }
        });
         holder.seat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(module.getExistSeat( rm_list, module.getRowData(position)+"4"))
                {

                }
                else {
                    if (module.getSeatDeseletct( seat_list, module.getRowData( position ) + "4" )) {
                        holder.seat_4.setBackgroundColor( activity.getResources().getColor( R.color.white ) );
                        seat_list.remove( module.getSeatListPosition( seat_list, module.getRowData( position ) + "4" ) );

                    } else {

                        holder.seat_4.setBackgroundColor( activity.getResources().getColor( R.color.dark_black ) );
                        seat_list.add( module.getRowData( position ) + "4" );
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
        ImageView seat_1,seat_2,seat_3,seat_4;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            module=new Module(activity);
            seat_1=(ImageView)itemView.findViewById(R.id.seat_1);
            seat_2=(ImageView)itemView.findViewById(R.id.seat_2);
            seat_3=(ImageView)itemView.findViewById(R.id.seat_3);
            seat_4=(ImageView)itemView.findViewById(R.id.seat_4);
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
