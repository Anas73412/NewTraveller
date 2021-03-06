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
    ArrayList<String> femaleList=new ArrayList<>();
    ArrayList<String> resList=new ArrayList<>();


    public SemiSleeper2x2Adapter(Activity activity, int seats, ArrayList<String> rm_list,ArrayList<String> resList, ArrayList<String> femaleList) {
        this.activity = activity;
        this.seats = seats;
        this.rm_list = rm_list;
        this.femaleList=femaleList;
        this.resList=resList;
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


        for(int res=0; res<resList.size();res++)
        {
            String[] s=resList.get( res ).split( "" );
//            pos=module.getRowReverseData( s[2].toString() );
            int posr=Integer.parseInt(s[2].toString());
            if(position+1 == posr) {
                String seat_number = s[1].toString();

                if (seat_number.equalsIgnoreCase("A")) {
                    holder.seat_1.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.reserve_seat)));
                    holder.seat_1.setClickable(false);

                } else if (seat_number.equalsIgnoreCase("B")) {
                    holder.seat_2.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.reserve_seat)));
                    holder.seat_2.setClickable(false);
                } else if (seat_number.equalsIgnoreCase("C")) {
                    holder.seat_3.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.reserve_seat)));
                    holder.seat_3.setClickable(false);
                } else if (seat_number.equalsIgnoreCase("D")) {
                    holder.seat_4.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.reserve_seat)));
                    holder.seat_4.setClickable(false);
                }
            }
        }

        for(int res=0; res<femaleList.size();res++)
        {
            String[] s=femaleList.get( res ).split( "" );
//            pos=module.getRowReverseData( s[2].toString() );
            int posf=Integer.parseInt(s[2].toString());
            if(position+1 == posf) {
                String seat_number = s[1].toString();

                if (seat_number.equalsIgnoreCase("A")) {
                    holder.seat_1.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.female_seat)));
                    holder.seat_1.setClickable(false);

                } else if (seat_number.equalsIgnoreCase("B")) {
                    holder.seat_2.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.female_seat)));
                    holder.seat_2.setClickable(false);
                } else if (seat_number.equalsIgnoreCase("C")) {
                    holder.seat_3.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.female_seat)));
                    holder.seat_3.setClickable(false);
                } else if (seat_number.equalsIgnoreCase("D")) {
                    holder.seat_4.setImageTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.female_seat)));
                    holder.seat_4.setClickable(false);
                }
            }
        }


        for(int i=0; i<rm_list.size();i++)
        {
            String[] s=rm_list.get( i ).split( "" );
//            pos=module.getRowReverseData( s[2].toString() );
            int pos_rem=Integer.parseInt(s[2].toString());
            if(position+1 == pos_rem) {
                String seat_number = s[1].toString();
                if(seat_number.equalsIgnoreCase("A"))
                {
                    holder.seat_1.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.booked_seat) ) );
                    holder.seat_1.setClickable(false);

                }
                else if(seat_number.equalsIgnoreCase("B"))
                {
                    holder.seat_2.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.booked_seat) ) );
                    holder.seat_2.setClickable(false);
                }
                else if(seat_number.equalsIgnoreCase("C"))
                {
                    holder.seat_3.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.booked_seat) ) );
                    holder.seat_3.setClickable(false);
                }else if(seat_number.equalsIgnoreCase("D"))
                {
                    holder.seat_4.setImageTintList( ColorStateList.valueOf( activity.getResources().getColor(R.color.booked_seat) ) );
                    holder.seat_4.setClickable(false);
                }

            }
            else {

            }

        }

        holder.seat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatString="A"+(position+1);
                setSeatMap(holder.seat_1,seatString);

            }
        });
        holder.seat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatString="B"+(position+1);
                setSeatMap(holder.seat_2,seatString);
            }
        });
         holder.seat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatString="C"+(position+1);
                setSeatMap(holder.seat_3,seatString);
            }
        });
         holder.seat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatString="D"+(position+1);
                setSeatMap(holder.seat_4,seatString);
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
        updates.putExtra("deck", "");
        activity.sendBroadcast(updates);
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
