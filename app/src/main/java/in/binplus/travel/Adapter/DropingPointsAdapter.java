package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import in.binplus.travel.Model.BoardingModel;
import in.binplus.travel.R;

public class DropingPointsAdapter extends RecyclerView.Adapter<DropingPointsAdapter.ViewHolder> {
    Activity activity;
    String location="";
    int position=-1;
    ArrayList<BoardingModel> point_list ;
    HashMap<String,String> points=new HashMap<>();
    private int lastSelectedPosition=-1;

    public DropingPointsAdapter(Activity activity, ArrayList<BoardingModel> point_list) {
        this.activity = activity;

        this.point_list = point_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
   View view = LayoutInflater.from( activity ).inflate( R.layout.row_boardingpoints,null );
   ViewHolder holder = new ViewHolder( view );
   return holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BoardingModel model = point_list.get( position );

        if(model.getTime().isEmpty() || model.getTime().equals("") || model.getTime().equals("null"))
        {
            holder.txt_time.setText( model.getTime() );

        }
        else
        {
            holder.txt_time.setText( setTimeInFormat(model.getTime()) );

        }
        holder.txt_name.setText( model.getLocation() );
       // holder.txt_desc.setText( model.getLocation_desc() );
       holder.radiobtn.setChecked(lastSelectedPosition == position);


    }

    @Override
    public int getItemCount() {
        return point_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton radiobtn ;
        RelativeLayout rel_choice;
        TextView txt_name  ,txt_time;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            radiobtn = itemView.findViewById( R.id.radio_point );
            txt_name = itemView.findViewById( R.id.txt_name );
            txt_time = itemView.findViewById( R.id.txt_time );
            rel_choice=(RelativeLayout)itemView.findViewById(R.id.rel_choice);

            rel_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    position=getAdapterPosition();
                    location=point_list.get(position).getLocation().toString();

                    lastSelectedPosition=getAdapterPosition();
                    notifyDataSetChanged();


                }
            });
        }
    }

    public String setTimeInFormat(String tm)
    {
        String[] str_arr=tm.split(":");
        int hrs=0;
        String fm="";
        int h= Integer.parseInt(str_arr[0]);
        if(h>12)
        {
            hrs=h-12;
            fm="PM";
        }
        else if(h == 12)
        {
            hrs=12;
            fm="PM";
        }
        else
        {
            hrs=h;
            fm="AM";
        }

        String hh="";
        String hours=String.valueOf(hrs);
        if(hours.length()<2)
        {
            hh="0"+hours;
        }
        else
        {
            hh=hours;
        }

        return hh+":"+str_arr[1]+" "+fm;

    }

    public String getDropingLocation()
    {
        return location;
    }
}
