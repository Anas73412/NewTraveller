package in.binplus.travel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.binplus.travel.Model.StopsModel;
import in.binplus.travel.R;

public class StopsListAdapter extends BaseAdapter {
    ArrayList<String> stop_list ;
    Activity activity ;
    @Override
    public int getCount() {
        return stop_list.size();
    }

    @Override
    public Object getItem(int i) {
        return stop_list.get( i );
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       view  = LayoutInflater.from( activity ).inflate( R.layout.row_recent_city,null );

        TextView txt_name = view.findViewById( R.id.txt_cityname );

        txt_name.setText( stop_list.get( i ).toString() );

      return view;
    }
}
