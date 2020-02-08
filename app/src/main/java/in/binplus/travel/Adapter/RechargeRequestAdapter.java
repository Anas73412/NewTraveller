package in.binplus.travel.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.binplus.travel.Model.REchargeHistoryModel;
import in.binplus.travel.R;

public class RechargeRequestAdapter extends RecyclerView.Adapter<RechargeRequestAdapter.ViewHolder> {
    ArrayList<REchargeHistoryModel> recharge_list ;
    Activity activity ;

    public RechargeRequestAdapter(ArrayList<REchargeHistoryModel> recharge_list, Activity activity) {
        this.recharge_list = recharge_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from( activity ).inflate( R.layout.row_recharge_history,null );
       ViewHolder holder = new ViewHolder( view );
       return  holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        REchargeHistoryModel model = new REchargeHistoryModel(  );
        model= recharge_list.get( position );
        holder.txt_date.setText( model.getDate()+"\n" +model.getTime() );

        holder.txt_amount.setText( activity.getResources().getString(R.string.currency)+""+model.getRequest_amount() );
       String sts = model.getStatus();
        if (sts.equalsIgnoreCase( "0" ))
        {
            holder.txt_status.setText( "Pending" );
            holder.txt_status.setTextColor( Color.GRAY );
        }
        else if (sts.equalsIgnoreCase( "1" ))
        {
            holder.txt_status.setText( "Complete" );
            holder.txt_status.setTextColor( Color.BLUE );
        }
        else
        {
            holder.txt_status.setText(sts );

        }
//        holder.txt_status.setText( model.getStatus() );

    }

    @Override
    public int getItemCount() {
        return recharge_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_amount ,txt_status ,txt_date ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            txt_amount= itemView.findViewById( R.id.request_amt );
            txt_status = itemView.findViewById( R.id.request_status );
            txt_date = itemView.findViewById( R.id.request_date );
        }
    }
}
