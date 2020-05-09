package in.binplus.travel.Adapter.SeatAdapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
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
    ArrayList<String> femaleList=new ArrayList<>();
    ArrayList<String> resList=new ArrayList<>();

    public SleeperAdapter(Activity activity, int seats, String type, ArrayList<String> rm_list,ArrayList<String> resList, ArrayList<String> femaleList) {
        this.activity = activity;
        this.seats = seats;
        this.type = type;
        this.rm_list = rm_list;
        this.femaleList=femaleList;
        this.resList=resList;
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

        for(int res=0; res<femaleList.size();res++)
        {
            String[] s=femaleList.get( res ).split( "" );
//            pos=module.getRowReverseData( s[2].toString() );
            int posf=Integer.parseInt(s[3].toString());
            if(position+1 == posf) {
                String seat_number = s[2].toString();
                String seatType=s[1].toString();
                if(seatType.equals(type)) {

                    if (seat_number.equalsIgnoreCase("A")) {
                        holder.seat_1.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.female_seat)));
                        holder.seat_1.setClickable(false);

                    } else if (seat_number.equalsIgnoreCase("B")) {
                        holder.seat_2.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.female_seat)));
                        holder.seat_2.setClickable(false);
                    } else if (seat_number.equalsIgnoreCase("C")) {
                        holder.seat_3.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.female_seat)));
                        holder.seat_3.setClickable(false);
                    }
                }
            }
        }

        for(int res=0; res<resList.size();res++)
        {
            String[] s=resList.get( res ).split( "" );
//            pos=module.getRowReverseData( s[2].toString() );
            int posr=Integer.parseInt(s[3].toString());
            if(position+1 == posr) {
                String seat_number = s[2].toString();
                String seatType=s[1].toString();
                if(seatType.equals(type)) {

                    if (seat_number.equalsIgnoreCase("A")) {
                        holder.seat_1.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.reserve_seat)));
                        holder.seat_1.setClickable(false);

                    } else if (seat_number.equalsIgnoreCase("B")) {
                        holder.seat_2.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.reserve_seat)));
                        holder.seat_2.setClickable(false);
                    } else if (seat_number.equalsIgnoreCase("C")) {
                        holder.seat_3.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.reserve_seat)));
                        holder.seat_3.setClickable(false);
                    }
                }
            }
        }



        for(int i=0; i<rm_list.size();i++)
        {
            String[] s=rm_list.get( i ).split( "" );

//            pos=module.getRowReverseData( s[2].toString() );
           int pos_rem=Integer.parseInt(s[3].toString());

            if(position == pos_rem)
            {
                String seat_number= s[2].toString();
                String seatType=s[1].toString();
                if(seatType.equals(type)) {

                    if (seat_number.equalsIgnoreCase("A")) {
                        holder.seat_1.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.booked_seat)));
                        holder.seat_1.setClickable(false);

                    } else if (seat_number.equalsIgnoreCase("B")) {
                        holder.seat_2.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.booked_seat)));
                        holder.seat_2.setClickable(false);
                    } else {
                        holder.seat_3.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.booked_seat)));
                        holder.seat_3.setClickable(false);
                    }
                }
                 }
            else {

            }

        }



        holder.seat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String seatString=type + "A"+(position+1);
                setSeatMap(holder.seat_1,seatString);
//                if(module.getExistSeat( rm_list, type+module.getRowData(position)+"1"))
//                {
//
//                }
//                else {
//
//
//                    if (getSeatDeseletct( seat_list, seat_no )) {
//                        holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray) ) );
//                        seat_list.remove( getSeatListPosition( seat_list, seat_no ) );
//
//                    } else {
//                        holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.green_500 ) ) );
//                        seat_list.add( seat_no );
//
//                    }
//                    updateintent();
//                }
            }
        });
        holder.seat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatString=type + "B"+(position+1);
                setSeatMap(holder.seat_2,seatString);


//                if(module.getExistSeat( rm_list, type+module.getRowData(position)+"2"))
//                {
//
//                }
//                else {
//
//                    String seat_no = type + getRowData( position ) + "2";
//
//                    if (getSeatDeseletct( seat_list, seat_no )) {
//                        holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray) ) );
//                        seat_list.remove( getSeatListPosition( seat_list, seat_no ) );
//
//                    } else {
//                        holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.green_500 ) ) );
//
//                        seat_list.add( seat_no );
//                    }
//                    updateintent();
//                }
            }
        });
         holder.seat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatString=type + "C"+(position+1);
                setSeatMap(holder.seat_3,seatString);


//                if(module.getExistSeat( rm_list, type+module.getRowData(position)+"3"))
//                {
//
//                }
//                else {
//
//                    String seat_no = type + getRowData( position ) + "3";
//
//                    if (getSeatDeseletct( seat_list, seat_no )) {
//                        holder.seat_3.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.dark_gray ) ) );
//                        seat_list.remove( getSeatListPosition( seat_list, seat_no ) );
//
//                    } else {
//
//                        holder.seat_3.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.green_500 ) ) );
//                        seat_list.add( seat_no );
//                    }
//                    updateintent();
//                }
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
        updates.putExtra("deck", "lowersleeper");
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

    public void setSeatMap(ImageView img,String seatString)
    {
        switch(module.getImageColorTint(img))
        {
            case "av":
                img.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.select_seat ) ) );
                seat_list.add( seatString );
                updateintent();
                break;
            case "pending":
                String stNo=seat_list.get(module.getSeatListPosition( seat_list,seatString ));
                if(stNo.contains("F"))
                {
                    img.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.female_seat ) ) );

                }
                else
                {
                    img.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.avl_seat ) ) );

                }

                seat_list.remove( module.getSeatListPosition( seat_list, seatString ));
                updateintent();
                break;
            case "reserve":
                module.showToast("Reserved Seat");
                break;
            case "female":
                module.showToast("Female Seat");
                img.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor( R.color.select_seat)));
                String se= seatString+"F";
                seat_list.add(se);
                updateintent();
                break;
            case "booked":
                module.showToast("Already Booked");
                break;
        }
    }
}


