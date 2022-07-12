package com.drave.mioporcino;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.drave.mioporcino.geo.MyGEO;
import com.drave.mioporcino.utility.general;

public class mycoords extends AppCompatActivity  {

    MyGEO mg ;
    TextView txt_lat;
    TextView txt_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycoords);
        getSupportActionBar().hide();

        txt_lat = (TextView)findViewById(R.id.latitude);
        txt_lon = (TextView)findViewById(R.id.longitude);
        txt_lat.setText("");
        txt_lon.setText("");

        setta_campi_coords();
    }



    public void ricarica_coords(View v) {
        try {
            txt_lat.setText(Double.toString(mg.lastLatitude));
            txt_lon.setText(Double.toString(mg.lastLongitude));
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }

    private void ascoltaCoord() {
        try {
            mg.start();
        }
        catch ( Exception e) {
            general.showError(this,e.getMessage());
        }
    }

    private void setta_campi_coords() {

        try {
            txt_lat.setText(Double.toString(mg.lastLatitude));
            txt_lon.setText(Double.toString(mg.lastLongitude));

        }
        catch ( Exception e) {
            general.showError(this,e.getMessage());
        }
    }
}
