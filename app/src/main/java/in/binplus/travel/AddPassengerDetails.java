package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.binplus.travel.Adapter.AddPassengerAdapter;
import in.binplus.travel.Adapter.CustomAddPassengerAdapter;
import in.binplus.travel.Model.AddPassengerToSeatModel;
import in.binplus.travel.Model.PassengerDetailsModel;
import in.binplus.travel.util.ToastMsg;

public class AddPassengerDetails extends AppCompatActivity {

    public static EditText et_name, et_adhar_id, et_age, et_mobile, et_nationality, et_email;
    public static RadioButton r_male, r_female;
    public static Button btnContinue;
    public static TextView txt_seat_no, txt_seat;
    RecyclerView recycler_add_passenger;
    AddPassengerAdapter addPassengerAdapter;
    CustomAddPassengerAdapter adapter;
    public static ArrayList<AddPassengerToSeatModel> p_list = new ArrayList<>();
    public static String getname, getadhar, getmobile, getnationality, getage, getseatno;
    public static String getgender = "", getemail = "";
    public static String seat_fare, source, destination, date, board_location, drop_location, v_type, v_id,v_name;
    int tot_seats = 0;

    TextView title, bus_name;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passenger_details);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_mobile = findViewById(R.id.et_mobile);
        et_email = findViewById(R.id.et_email);
        et_adhar_id = findViewById(R.id.et_adhar_id);
        et_nationality = findViewById(R.id.et_nationality);
        bus_name = findViewById(R.id.txt_vehicle_name);
        r_male = findViewById(R.id.male);
        r_female = findViewById(R.id.female);
        btnContinue = findViewById(R.id.btnContinue);
        txt_seat_no = findViewById(R.id.seat_no);
        txt_seat = findViewById(R.id.txt_seat);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        recycler_add_passenger = findViewById(R.id.recycler_add_passenger);
        v_type = getIntent().getStringExtra("v_type");
        seat_fare = getIntent().getStringExtra("seat_fare");
        date = getIntent().getStringExtra("date");
        source = getIntent().getStringExtra("source");
        v_type = getIntent().getStringExtra("v_type");
        v_id = getIntent().getStringExtra("v_id");
        v_name = getIntent().getStringExtra("v_name");
        destination = getIntent().getStringExtra("destination");
        if (v_type.equals("sharing")) {
            txt_seat_no.setText("");
            txt_seat.setText("");
            et_email.setVisibility(View.VISIBLE);
            tot_seats = Integer.parseInt(getIntent().getStringExtra("total_seats"));

        } else {
            txt_seat_no.setText(SelectSeatActivity.seat_list.get(0));
            board_location = getIntent().getStringExtra("board");
            drop_location = getIntent().getStringExtra("drop");
            et_email.setVisibility(View.GONE);

        }


        title.setText(source + " - " + destination);
        bus_name.setText(SelectSeatActivity.vehicle_name);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        adapter = new CustomAddPassengerAdapter(AddPassengerDetails.this, (ArrayList<String>) SelectSeatActivity.seat_list, v_type, tot_seats);
        recycler_add_passenger.setLayoutManager(new LinearLayoutManager(AddPassengerDetails.this, LinearLayoutManager.VERTICAL, false));
        recycler_add_passenger.setAdapter(adapter);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = et_name.getText().toString();
                String mobile_no = et_mobile.getText().toString();
                String adhaar_no = et_adhar_id.getText().toString();
                String nationality = et_nationality.getText().toString();
                String age = et_age.getText().toString();
                String email = et_email.getText().toString();
                String gen = "";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (r_male.isChecked()) {
                    gen = "M";

                } else if (r_female.isChecked()) {
                    gen = "F";
                }
                if (v_type.equals("sharing")) {
                    if (name.equals("") || name.isEmpty()) {
                        et_name.setError("Enter Name");
                        et_name.requestFocus();
                    } else if (mobile_no.equals("") || mobile_no.isEmpty()) {
                        et_mobile.setError("Enter Mobile Number");
                        et_mobile.requestFocus();
                    }
                    else if (mobile_no.length()!=10) {
                        et_mobile.setError("Enter Valid Mobile Number");
                        et_mobile.requestFocus();
                    }
                    else if (email.isEmpty() || email.equals("")) {
                        et_email.setError("Enter email address");
                        et_email.requestFocus();
                    }
                    else if (!email.matches(emailPattern)) {
                        et_email.setError("Enter valid email address");
                        et_email.requestFocus();
                    }
                    else if (adhaar_no.isEmpty() || adhaar_no.equals("")) {
                        et_adhar_id.setError("Enter Adhaar Card No");
                        et_adhar_id.requestFocus();
                    }
                    else if (adhaar_no.length()!=12) {
                        et_adhar_id.setError("Enter valid Adhaar No");
                        et_adhar_id.requestFocus();
                    }
                    else if (age.equals("") || age.isEmpty()) {
                        et_age.setError("Enter Age");
                        et_age.requestFocus();
                    } else if (gen.equals("") || gen.isEmpty()) {
                        new ToastMsg(AddPassengerDetails.this).toastIconError("Select A Gender");
                    } else {
                        String s_no = "";
                        getemail =email;
                        addpassengerinfo(name, age, gen, nationality, s_no, mobile_no, adhaar_no);
                        if (p_list.size() == tot_seats) {
                            startIntent();

//                      Toast.makeText( AddPassengerDetails.this, "plist :" + p_list.size() + "\n seatlist:" + SelectSeatActivity.seat_list.size(), Toast.LENGTH_LONG ).show();
                        } else {
                            Toast.makeText(AddPassengerDetails.this, "Enter passenger details", Toast.LENGTH_LONG).show();

                        }
                    }
                } else {
                    if (name.equals("") || name.isEmpty()) {
                        et_name.setError("Enter Name");
                        et_name.requestFocus();
                    } else if (mobile_no.equals("") || mobile_no.isEmpty()) {
                        et_mobile.setError("Enter Mobile Number");
                        et_mobile.requestFocus();
                    }
                    else if (mobile_no.length()!=10) {
                        et_mobile.setError("Enter Valid Mobile Number");
                        et_mobile.requestFocus();
                    }
                    else if (adhaar_no.isEmpty() || adhaar_no.equals("")) {
                        et_adhar_id.setError("Enter Adhaar Card No");
                        et_adhar_id.requestFocus();
                    }
                    else if (adhaar_no.length()!=12) {
                        et_adhar_id.setError("Enter valid Adhaar No");
                        et_adhar_id.requestFocus();
                    }
                    else if (age.equals("") || age.isEmpty()) {
                        et_age.setError("Enter Age");
                        et_age.requestFocus();
                    } else if (gen.equals("") || gen.isEmpty()) {
                        new ToastMsg(AddPassengerDetails.this).toastIconError("Select A Gender");
                    } else {
                        String s_no = txt_seat_no.getText().toString();
                        addpassengerinfo(name, age, gen, nationality, s_no, mobile_no, adhaar_no);
                        if (p_list.size() == SelectSeatActivity.seat_list.size()) {

                            startIntent();
//                            Log.e("pass", date + source+ destination);
//                        Toast.makeText( AddPassengerDetails.this, "plist :" + p_list.size() + "\n seatlist:" + SelectSeatActivity.seat_list.size(), Toast.LENGTH_LONG ).show();
                        } else {
                            Toast.makeText(AddPassengerDetails.this, "Enter passenger details", Toast.LENGTH_LONG).show();

                        }

                    }
                }


//

            }
        });


    }

    public void startIntent() {
        Intent intent = new Intent(AddPassengerDetails.this, BookingConfirmation.class);
        intent.putExtra("p_name", p_list.get(0).getPassenger_name());
        intent.putExtra("adhar_id", p_list.get(0).getAdhaar_no());
        intent.putExtra("mobile", p_list.get(0).getMobile_no());
        intent.putExtra("seat_fare", seat_fare);
        intent.putExtra("date", date);
        intent.putExtra("v_type", v_type);
        intent.putExtra("v_id",v_id);
        intent.putExtra("v_name",v_name);
        intent.putExtra("board", board_location);
        intent.putExtra("drop", drop_location);
        intent.putExtra("source", source);
        intent.putExtra("destination", destination);
        startActivity(intent);
    }

    public void addpassengerinfo(String name, String age, String gen, String nationality, String s_no, String mobile_no, String adhaar_no) {
        p_list = new ArrayList<>();


        p_list.add(0, new AddPassengerToSeatModel(name, age, gen, nationality, s_no, mobile_no, adhaar_no, seat_fare));


        for (int i = 1; i < adapter.getItemCount(); i++) {
            AddPassengerToSeatModel model = new AddPassengerToSeatModel();
            CustomAddPassengerAdapter.ViewHolder viewHolder = (CustomAddPassengerAdapter.ViewHolder) recycler_add_passenger.findViewHolderForAdapterPosition(i);
            EditText edt_name = viewHolder.et_name;
            EditText edt_age = viewHolder.et_age;
            EditText edt_mobile = viewHolder.et_mobile;
            EditText edt_nationality = viewHolder.et_nationality;
            TextView tv_seat_no = viewHolder.seat_no;
            RadioButton rd_male = viewHolder.male;
            RadioButton rd_female = viewHolder.female;
            String g = "";
//
            model.setSeat_no(tv_seat_no.getText().toString());
            model.setPassenger_name(edt_name.getText().toString());
            model.setMobile_no(edt_mobile.getText().toString());
            model.setAge(edt_age.getText().toString());
            model.setNationality(edt_nationality.getText().toString());
            model.setSeat_price(seat_fare);


            if (rd_male.isChecked()) {
                g = "M";
            } else if (rd_female.isChecked()) {
                g = "F";
            }
            model.setGender(g);
            if (model.getAge().isEmpty() || model.getPassenger_name().isEmpty() || model.getGender().isEmpty()) {
                Toast.makeText(AddPassengerDetails.this, "Enter details", Toast.LENGTH_LONG).show();
            } else {
                p_list.add(model);
            }
//
        }
    }
}
