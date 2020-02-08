package in.binplus.travel.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.binplus.travel.Adapter.SeatAdapters.Seater2x2Adapter;
import in.binplus.travel.Adapter.SeatAdapters.Seater3x2Adapter;
import in.binplus.travel.Adapter.SeatAdapters.SemiSleeper2x2Adapter;
import in.binplus.travel.Adapter.SeatAdapters.SemiSleeper3x2Adapter;
import in.binplus.travel.Adapter.SeatAdapters.SleeperAdapter;
import in.binplus.travel.AppController;
import in.binplus.travel.Config.Module;
import in.binplus.travel.R;
import in.binplus.travel.SelectSeatActivity;
import in.binplus.travel.util.CustomVolleyJsonRequest;
import in.binplus.travel.util.ToastMsg;

import static in.binplus.travel.Config.BaseURL.URL_VEHICLE_DETAILS_WID;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeatSelectionFragment extends Fragment {

    Module module;
    ProgressDialog loadingBar;
    RecyclerView rec_sleeper;
    Seater3x2Adapter seater3x2Adapter;
    Seater2x2Adapter seater2x2Adapter;
    SemiSleeper2x2Adapter semiSleeper2x2Adapter;
    SemiSleeper3x2Adapter semiSleeper3x2Adapter;
    SleeperAdapter sleeperAdapter;
    List<String> seat_list=new ArrayList<>();
    Button btn_book;
    String bus_id="";
    public SeatSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_seat_selection, container, false);
     loadingBar=new ProgressDialog(getActivity());
     loadingBar.setMessage("Loading...");
     loadingBar.setCanceledOnTouchOutside(false);
     module=new Module(getActivity());
        Bundle bundle=getArguments();
        bus_id=bundle.getString("bus_id");
        rec_sleeper=(RecyclerView)view.findViewById(R.id.rec_sleeper);
        btn_book=(Button) view.findViewById(R.id.btn_book);


        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //seat_list= sleeperAdapter.getSeatList();

                new ToastMsg(getActivity()).toastIconSuccess(String.valueOf(seat_list.size()));
            }
        });

        getBusData(bus_id);
        return view;
    }

    private void getBusData(String bus_id) {

        loadingBar.show();
        String json_tag="json_bus_details_id_tag";
        HashMap<String,String> params=new HashMap<>();
        params.put("vehicle_id",bus_id);

        CustomVolleyJsonRequest request=new CustomVolleyJsonRequest(Request.Method.POST, URL_VEHICLE_DETAILS_WID, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            loadingBar.dismiss();
                try
                {
                    Log.e("details-data",response.toString());
                    JSONArray array=response.getJSONArray("data");
                    JSONObject object=array.getJSONObject(0);
                    String layout=object.getString("layout");
                    String sitting_type=object.getString("sitting_type");
                    int total_seats=Integer.parseInt(object.getString("total_seats"));
                    double seat_fare=Double.parseDouble(object.getString("seat_fare"));

                    if(sitting_type.equalsIgnoreCase("Seater"))
                    {
                        if(layout.equalsIgnoreCase("2X2"))
                        {
//                            int row=total_seats/4;
//                            seater2x2Adapter=new Seater2x2Adapter(getActivity(),row,rem);
//                            rec_sleeper.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            rec_sleeper.setAdapter(seater2x2Adapter);
//                            seater2x2Adapter.notifyDataSetChanged();
                        }
                        else if(layout.equalsIgnoreCase("3X2"))
                        {
//                            int row=total_seats/5;
//                            seater3x2Adapter=new Seater3x2Adapter(getActivity(),row);
//                            rec_sleeper.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            rec_sleeper.setAdapter(seater3x2Adapter);
//                            seater3x2Adapter.notifyDataSetChanged();
                        }
                    }
                    else if(sitting_type.equalsIgnoreCase("Semi Sleeper"))
                    {
                        if(layout.equalsIgnoreCase("2X2"))
                        {
//                            int row=total_seats/4;
//                            semiSleeper2x2Adapter=new SemiSleeper2x2Adapter(getActivity(),row);
//                            rec_sleeper.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            rec_sleeper.setAdapter(semiSleeper2x2Adapter);
//                            semiSleeper2x2Adapter.notifyDataSetChanged();
                        }
                        else if(layout.equalsIgnoreCase("3X2"))
                        {
//                            int row=total_seats/5;
//                            semiSleeper3x2Adapter=new SemiSleeper3x2Adapter(getActivity(),row);
//                            rec_sleeper.setLayoutManager(new LinearLayoutManager(getActivity()));
//                            rec_sleeper.setAdapter(semiSleeper3x2Adapter);
//                            semiSleeper3x2Adapter.notifyDataSetChanged();
                        }
                    }
                    else if(sitting_type.equalsIgnoreCase("Sleeper"))
                    {

//                        int row=total_seats/3;
//                        sleeperAdapter=new SleeperAdapter(getActivity(),row,"L");
//                        rec_sleeper.setLayoutManager(new LinearLayoutManager(getActivity()));
//                        rec_sleeper.setAdapter(sleeperAdapter);
//                        sleeperAdapter.notifyDataSetChanged();
                    }






                   // new ToastMsg(getActivity()).toastIconSuccess("done");
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    new ToastMsg(getActivity()).toastIconError(ex.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                String msg=module.VolleyErrorMessage(error);
                if(!(msg.isEmpty() || msg.equals("")))
                {
                    new ToastMsg(getActivity()).toastIconError(msg);
                    //Toast.makeText(getActivity(),""+msg.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        AppController.getInstance().addToRequestQueue(request,json_tag);

    }

}
