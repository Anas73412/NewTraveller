package in.binplus.travel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import in.binplus.travel.R;

/**
 * Developed by Binplus Technologies pvt. ltd.  on 28,January,2020
 */
public class CustomSeatAdapter extends BaseAdapter {
    private Context mContext;

    private final int[] Imageid;
    public CustomSeatAdapter(Context c,int[] Imageid ) {
        mContext = c;
        this.Imageid = Imageid;

    }
    @Override
    public int getCount() {
        return Imageid.length;
    }

    @Override
    public Object getItem(int position) {
        return Imageid[position];
    }

    @Override
    public long getItemId(int position) {
        return Imageid[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.row_grid, null);


            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);

            imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }



        return grid;
    }
}
