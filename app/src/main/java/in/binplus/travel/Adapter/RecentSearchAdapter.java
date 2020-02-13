package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.R;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.ViewHolder> {
    ArrayList<StopsModel> stop_list ;
    Activity activity ;

    public RecentSearchAdapter(ArrayList<StopsModel> stop_list, Activity activity) {
        this.stop_list = stop_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( activity ).inflate( R.layout.row_recent_city ,null);
        ViewHolder holder = new ViewHolder( view );
        return holder ;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cityname.setText(stop_list.get( position ).getStop_name() );

    }

    @Override
    public int getItemCount() {
        return stop_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityname ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            cityname = itemView.findViewById( R.id.txt_cityname );
        }
    }
}
