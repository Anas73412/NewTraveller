package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Model.CityListModel;
import in.binplus.travel.R;

public class TopCityAdapter extends RecyclerView.Adapter<TopCityAdapter.ViewHolder> {
    ArrayList<CityListModel> citylist ;
    Activity activity ;

    public TopCityAdapter(ArrayList<CityListModel> citylist, Activity activity) {
        this.citylist = citylist;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate( R.layout.row_top_cities ,null);
        ViewHolder holder = new ViewHolder( view );

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CityListModel model = citylist.get( position );
        holder.cityname.setText( model.getTitle() );

    }

    @Override
    public int getItemCount() {
        return citylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityname ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            cityname = itemView.findViewById( R.id.txt_cityname );
        }
    }
}
