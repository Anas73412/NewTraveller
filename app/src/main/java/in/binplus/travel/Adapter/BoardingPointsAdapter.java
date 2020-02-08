package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Model.BoardingPointModel;
import in.binplus.travel.R;

public class BoardingPointsAdapter extends RecyclerView.Adapter<BoardingPointsAdapter.ViewHolder> {
    Activity activity ;
    ArrayList<BoardingPointModel> point_list ;

    public BoardingPointsAdapter(Activity activity, ArrayList<BoardingPointModel> point_list) {
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
        BoardingPointModel model = point_list.get( position );
        holder.txt_time.setText( model.getLocation_time() );
        holder.txt_name.setText( model.getLocation_name() );
        holder.txt_desc.setText( model.getLocation_desc() );



    }

    @Override
    public int getItemCount() {
        return point_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton radiobtn ;
        TextView txt_name ,txt_desc ,txt_time;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            radiobtn = itemView.findViewById( R.id.radio_point );
            txt_desc = itemView.findViewById( R.id.txt_desc );
            txt_name = itemView.findViewById( R.id.txt_name );
            txt_time = itemView.findViewById( R.id.txt_time );
        }
    }
}
