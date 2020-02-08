package in.binplus.travel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.binplus.travel.Model.BoardingPointModel;
import in.binplus.travel.Model.BookingDetailsModel;

public class SelectBoardingPoint extends AppCompatActivity {
    TextView txt_title ;
    ImageView back ;
    RecyclerView recycler_boarding , recycler_droping ;
    RelativeLayout rel_board ,rel_drop ;
    String source,destination ,date ,seat_fare ;
    View view_board ,view_drop ;
    ArrayList<BoardingPointModel> board_list ;
    ArrayList<BookingDetailsModel> drop_list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_select_boarding_point );
        rel_board = findViewById( R.id.tab_board );
        rel_drop = findViewById( R.id.tab_drop );
        txt_title = findViewById( R.id.title );
        recycler_boarding = findViewById( R.id.recycler_boarding_point );
        recycler_droping = findViewById( R.id.recycler_dropping_point );
        view_board = findViewById( R.id.view_b );
        view_drop = findViewById( R.id.view_d );
        back = findViewById( R.id.back );

        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );



        source = getIntent().getStringExtra( "source" );
        destination =getIntent().getStringExtra( "destination" );
        date = getIntent().getStringExtra( "date" );
        seat_fare = getIntent().getStringExtra( "seat_fare" );

        txt_title.setText(source +"-"+destination );

        rel_board.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_board.setVisibility(SelectBoardingPoint.this.getResources().getColor( R.color.red_600 ) );
                view_drop.setBackgroundColor(SelectBoardingPoint.this.getResources().getColor( R.color.white ) );
                recycler_droping.setVisibility( View.GONE);
                recycler_boarding.setVisibility( View.VISIBLE );

                Intent  intent = new Intent( SelectBoardingPoint.this,AddPassengerDetails.class);
                intent.putExtra( "seat_fare",String.valueOf( seat_fare ));
                intent.putExtra( "date",String.valueOf( date ));
                intent.putExtra( "source",source );
                intent.putExtra( "destination",destination );
                startActivity( intent );

            }
        } );

        rel_drop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_board.setVisibility( SelectBoardingPoint.this.getResources().getColor( R.color.white ) );
                view_drop.setVisibility( SelectBoardingPoint.this.getResources().getColor( R.color.red_600 ));
                recycler_droping.setVisibility( View.VISIBLE );
                recycler_boarding.setVisibility( View.GONE );
            }
        }  );


    }
}
