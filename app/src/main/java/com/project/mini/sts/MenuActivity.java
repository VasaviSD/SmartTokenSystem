package com.project.mini.sts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnFind,btnWD,btnDep;
    Track t;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu );
        btnDep=(Button)findViewById(R.id.btnDep);
        btnWD=(Button)findViewById(R.id.btnWD);
        btnFind=(Button)findViewById(R.id.btnFind);

        btnFind.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=new Track(MenuActivity.this);
                latitude=t.getLatitude();
                longitude=t.getLongitude();
                Intent intent=new Intent( MenuActivity.this,MapActivity.class );
                Bundle extras = new Bundle();
                extras.putString("EXTRA_Latitude", String.valueOf( latitude ) );
                extras.putString("EXTRA_Longitude", String.valueOf( longitude ) );
                intent.putExtras(extras);
                startActivity(intent);
            }
        } );
    }
}
