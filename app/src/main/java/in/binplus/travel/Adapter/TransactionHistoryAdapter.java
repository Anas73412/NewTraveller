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

import in.binplus.travel.Model.TransactionModel;
import in.binplus.travel.R;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    ArrayList<TransactionModel> arrayList = new ArrayList<>(  );
    Activity activity ;

    public TransactionHistoryAdapter(ArrayList<TransactionModel> arrayList, Activity activity) {
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( activity ).inflate( R.layout.row_transaction_history,null );
        ViewHolder holder = new ViewHolder( view );
        return  holder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModel t_model = arrayList.get( position );
        holder.txt_id.setText(t_model.getId() );
        holder.txt_date.setText( t_model.getDate() +"\n"+ t_model.getTime() );
        String status = t_model.getStatus();
        if (status.equals( "credit" )) {
            holder.txt_status.setText( "Credited" );
            holder.txt_status.setTextColor( Color.GREEN );
        }
        else if (status.equals( "debit" ))
        {
            holder.txt_status.setText( "Debited" );
            holder.txt_status.setTextColor( Color.RED );
        }
        else
        {
            holder.txt_status.setText( status );
            holder.txt_status.setTextColor( Color.YELLOW);
        }
        holder.txt_amt.setText( activity.getResources().getString(R.string.currency)+""+t_model.getAmount() );

        holder.txt_desc.setText( t_model.getDescription() );

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_id ,txt_amt ,txt_status ,txt_date ,txt_desc ;
        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            txt_id = itemView.findViewById( R.id.transaction_id );
            txt_amt = itemView.findViewById( R.id.trsaction_amt );
            txt_status = itemView.findViewById( R.id.transaction_status );
            txt_date = itemView.findViewById( R.id.transaction_date );
            txt_desc = itemView.findViewById( R.id.description );
        }
    }
}
