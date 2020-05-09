package in.binplus.travel.Config;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.util.ArrayList;

import in.binplus.travel.Model.KeyValueModel;
import in.binplus.travel.R;

public class Module {

    Context context;

    public Module(Context context) {
        this.context = context;
    }
//
    public String VolleyErrorMessage(VolleyError error)
    {
        String str_error ="";
        if (error instanceof TimeoutError) {
            str_error="Connection Timeout";
        } else if (error instanceof AuthFailureError) {
            str_error="Session Timeout";
            //TODO
        } else if (error instanceof ServerError) {
            str_error="Server not responding please try again later";
            //TODO
        } else if (error instanceof NetworkError) {
            str_error="Server not responding please try again later";
            //TODO
        } else if (error instanceof ParseError) {
            //TODO
            str_error="An Unknown error occur";
        }else if(error instanceof NoConnectionError){
            str_error="no Internet Connection";
        }

        return str_error;
    }
//
    public void preventMultipleClick(final View view) {
        view.setEnabled(false);
    }

    public String getRowData(int pos)
    {
        String first="";
        switch (pos)
        {
            case 0:
                first="A";
                break;
            case 1:
                first="B";
                break;
            case 2:
                first="C";
                break;
            case 3:
                first="D";
                break;
            case 4:
                first="E";
                break;
            case 5:
                first="F";
                break;
            case 6:
                first="G";
                break;
            case 7:
                first="H";
                break;
            case 8:
                first="I";
                break;
            case 9:
                first="J";
                break;
            case 10:
                first="K";
                break;
            case 11:
                first="L";
                break;
            case 12:
                first="M";
                break;
            case 13:
                first="N";
                break;
            case 14:
                first="O";
                break;
            case 15:
                first="P";
                break;
            case 16:
                first="Q";
                break;
            case 17:
                first="R";
                break;
            case 18:
                first="S";
                break;
            default:
                first="";
                break;
        }
        return first;
    }


    public boolean getSeatDeseletct(ArrayList<String> list,String seat)
    {
        boolean fd=false;
        for(int i=0; i<list.size();i++)
        {
            if(list.get(i).equals(seat))
            {
                fd=true;
                break;
            }
            else
            {
                fd=false;

            }
        }
        return fd;
    }

    public int getSeatListPosition(ArrayList<String> list,String seat)
    {
        int position=-1;
        for(int i=0; i<list.size();i++)
        {

            if(removeF(list.get(i)).equals(removeF(seat)))
            {
                position=i;
                Log.e("existance",""+list.get(i)+" - "+i+" - "+seat);
                break;
            }

        }
        return position;
    }

    public String removeF(String str)
    {
        String strValue="";
        if(str.contains("F"))
        {
            strValue=str.split("F")[0].toString();
        }
        else
        {
            strValue=str;
        }
        return strValue;
    }



    public int getRowReverseData(String pos)
    {
        int first=-1;
        switch (pos)
        {
            case "A":
                first=0;
                break;
            case "B":
                first=1;
                break;
            case "C":
                first=2;
                break;
            case "D":
                first=3;
                break;
            case "E":
                first=4;
                break;
            case "F":
                first=5;
                break;
            case "G":
                first=6;
                break;
            case "H":
                first=7;
                break;
            case "I":
                first=8;
                break;
            case "J":
                first=9;
                break;
            case "K":
                first=10;
                break;
            case "L":
                first=11;
                break;
            case "M":
                first=12;
                break;
            case "N":
                first=13;
                break;
            case "O":
                first=14;
                break;
            case "P":
                first=15;
                break;
            case "Q":
                first=16;
                break;
            case "R":
                first=17;
                break;
            case "S":
                first=18;
                break;
            default:
                first=-1;
                break;
        }
        return first;
    }


    public boolean getExistSeat(ArrayList<String> list, String str)
    {
        boolean flag=false;

        for(int i=0; i<list.size();i++)
        {
            if(list.get( i ).equals( str ))
            {
                flag =true;
                break;
            }
            else
                flag =false;

        }
        return flag;
    }
    public void showToast(String s)
    {
        Toast.makeText(context,""+s,Toast.LENGTH_SHORT).show();
    }

    public String getImageColorTint(ImageView img)
    {
        String str="av";
        int color=img.getImageTintList().getDefaultColor();
        if(color==context.getResources().getColor(R.color.avl_seat))
        {
            str="av";
        }
        else if(color == context.getResources().getColor(R.color.female_seat))
        {
            str="female";
        }
        else if(color == context.getResources().getColor(R.color.select_seat))
        {
            str="pending";
        }
        else if(color == context.getResources().getColor(R.color.reserve_seat))
        {
          str="reserve";
        }
        else if(color == context.getResources().getColor(R.color.booked_seat))
        {
            str="booked";
        }

        return str;
    }

    public String getParticulerSeatRent(String total_money,int length)
    {
        double tot=Double.parseDouble(total_money);
        int r=(int)(tot/length);
        return String.valueOf(r);
    }
}

