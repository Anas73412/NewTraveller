package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Model.CityListModel;
import in.binplus.travel.R;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder>implements Filterable {
    private ArrayList<CityListModel> citylist;
    private ArrayList<CityListModel> mFilteredList;
    Activity activity ;
    CityListAdapter adapter ;

    public CityListAdapter(ArrayList<CityListModel> citylist, Activity activity) {
        this.citylist = citylist;
        this.activity = activity;
    }

    public CityListAdapter(ArrayList<CityListModel> mArrayList, ArrayList<CityListModel> mFilteredList) {
        this.citylist = mArrayList;
        this.mFilteredList = mFilteredList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(activity ).inflate( R.layout.row_citylist,null );
        ViewHolder holder = new ViewHolder( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cityname.setText( citylist.get( position ).getTitle() );




    }

    @Override
    public int getItemCount() {
//      return mFilteredList.size();
       return citylist.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = citylist;
                } else {

                    ArrayList<CityListModel> filteredList = new ArrayList<>();

                    for (CityListModel cityListModel : citylist) {

                        if (cityListModel.getTitle().toLowerCase().contains( charString ))

                        {
                            filteredList.add( cityListModel );
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<CityListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void filterList(ArrayList<CityListModel> filterdNames) {
        this.citylist = filterdNames;
        notifyDataSetChanged();

    }

            public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityname ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            cityname = itemView.findViewById( R.id.txt_cityname );
        }
    }
}
