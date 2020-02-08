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
import in.binplus.travel.SelectSeatActivity;
import in.binplus.travel.util.ToastMsg;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 30,January,2020
 */
public class SleeperAdapter extends RecyclerView.Adapter<SleeperAdapter.ViewHolder> {
    Activity activity;
    int seats;
    String type="";
    int pos=-1;
    Module module;
    ArrayList<String> rm_list;
    ArrayList<String> seat_list=new ArrayList<>();

    public SleeperAdapter(Activity activity, int seats, String type, ArrayList<String> rm_list) {
        this.activity = activity;
        this.seats = seats;
        this.type = type;
        this.rm_list = rm_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.row_full_sleeper,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        for(int i=0; i<rm_list.size();i++)
        {
            String[] s=rm_list.get( i ).split( "" );

            pos=module.getRowReverseData( s[2].toString() );

            if(position == pos)
            {
                int seat_number=Integer.parseInt( s[3].toString() );

                if(seat_number ==1)
                {
                    holder.seat_1.setBackgroundColor(activity.getResources().getColor(R.color.orange));

                }
                else if(seat_number == 2)
                {
                    holder.seat_2.setBackgroundColor(activity.getResources().getColor(R.color.orange));

                }
                else
                {
                    holder.seat_3.setBackgroundColor(activity.getResources().getColor(R.color.orange));
                }
                 }
            else {

            }

        }



        holder.seat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(module.getExistSeat( rm_list, type+module.getRowData(position)+"1"))
                {

                }
                else {


                    String seat_no = type + getRowData( position ) + "1";
                    if (getSeatDeseletct( seat_list, seat_no )) {
                        holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray) ) );
                        seat_list.remove( getSeatListPosition( seat_list, seat_no ) );

                    } else {
                        holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_black ) ) );
                        seat_list.add( seat_no );

                    }
                    updateintent();
                }
            }
        });
        holder.seat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(module.getExistSeat( rm_list, type+module.getRowData(position)+"2"))
                {

                }
                else {

                    String seat_no = type + getRowData( position ) + "2";

                    if (getSeatDeseletct( seat_list, seat_no )) {
                        holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray) ) );
                        seat_list.remove( getSeatListPosition( seat_list, seat_no ) );

                    } else {
                        holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_black ) ) );

                        seat_list.add( seat_no );
                    }
                    updateintent();
                }
            }
        });
         holder.seat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(module.getExistSeat( rm_list, type+module.getRowData(position)+"3"))
                {

                }
                else {

                    String seat_no = type + getRowData( position ) + "3";

                    if (getSeatDeseletct( seat_list, seat_no )) {
                        holder.seat_3.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray ) ) );
                        seat_list.remove( getSeatListPosition( seat_list, seat_no ) );

                    } else {

                        holder.seat_3.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_black ) ) );
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
        ImageView seat_1,seat_2,seat_3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            module =new Module( activity );
            seat_1=(ImageView)itemView.findViewById(R.id.seat_1);
            seat_2=(ImageView)itemView.findViewById(R.id.seat_2);
            seat_3=(ImageView)itemView.findViewById(R.id.seat_3);
        }
    }

    public String getRowData(int pos)
    {
        String first="";
        switch (pos)
        {
            case 0:
                first="A";
                break;
            case 1:
                first="B";
                break;
            case 2:
                first="C";
                break;
            case 3:
                first="D";
                break;
            case 4:
                first="E";
                break;
            case 5:
                first="F";
                break;
            case 6:
                first="G";
                break;
            case 7:
                first="H";
                break;
            case 8:
                first="I";
                break;
            case 9:
                first="J";
                break;
            case 10:
                first="K";
                break;
            case 11:
                first="L";
                break;
            case 12:
                first="M";
                break;
            case 13:
                first="N";
                break;
            case 14:
                first="O";
                break;
            case 15:
                first="P";
                break;
            case 16:
                first="Q";
                break;
            case 17:
                first="R";
                break;
            case 18:
                first="S";
                break;
           default:
               first="";
               break;
        }
        return first;
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

    public boolean getSeatDeseletct(ArrayList<String> list,String seat)
    {
        boolean fd=false;
        for(int i=0; i<list.size();i++)
        {
            if(list.get(i).equals(seat))
            {
                fd=true;
                break;
            }
            else
            {
                fd=false;

            }
        }
        return fd;
    }

    public int getSeatListPosition(ArrayList<String> list,String seat)
    {
        int position=-1;
        for(int i=0; i<list.size();i++)
        {
            if(list.get(i).equals(seat))
            {
                position=i;
                break;
            }

        }
        return position;
    }
}
