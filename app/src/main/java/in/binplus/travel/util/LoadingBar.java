package in.binplus.travel.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import in.binplus.travel.R;


/**
 * Developed by Binplus Technologies pvt. ltd.  on 06,April,2020
 */
public class LoadingBar {
    Context context;
    ProgressDialog dialog;

    public LoadingBar(Context context) {
        this.context = context;
        dialog=new ProgressDialog(context);
        dialog.setMessage("Loading");
        dialog.setCanceledOnTouchOutside(false);
    }
    public void show()
    {
        dialog.show();
    }

    public void dismiss()
    {
        dialog.dismiss();
    }

    public boolean isShowing()
    {
       return dialog.isShowing();
    }
}
