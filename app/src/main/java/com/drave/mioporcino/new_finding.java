package com.drave.mioporcino;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drave.mioporcino.database.finding;
import com.drave.mioporcino.database.findingdb;
import com.drave.mioporcino.geo.MyGEO;
import com.drave.mioporcino.utility.Const;
import com.drave.mioporcino.utility.general;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class new_finding extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    MyGEO mg ;
    private EditText myLat,myLon,e_q;
    LinearLayout l_b;
    Spinner spn_tipo, spn_valutazione;

    finding f ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_finding);
        getSupportActionBar().hide();


        e_q = (EditText)findViewById(R.id.txt_quantita);
        e_q.clearFocus();
        l_b = (LinearLayout)findViewById(R.id.lnr_btn);
        l_b.requestFocus();

        mg = new MyGEO(this);
        ascoltaCoord();

        myLat = findViewById(R.id.txt_lat);
        myLon = findViewById(R.id.txt_lon);
        myLat.setText("");
        myLon.setText("");

        setta_campi_coords();
        fill_spn_tipo();
        fill_spn_valutazione();
    }



    //------------------------------------------------------------------------
    // Bottone ricarica
    //------------------------------------------------------------------------
    public void ricarica_coords(View v) {
        try {
            Toast.makeText(this, "Ricarico Coordinate!", Toast.LENGTH_SHORT).show();
            myLat.setText(Double.toString(mg.lastLatitude));
            myLon.setText(Double.toString(mg.lastLongitude));
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //----------------------------------------------------------------------

    //------------------------------------------------------------------------
    // Bottone vedi mappa
    //------------------------------------------------------------------------
    public void viewMapsCoords(View v) {
        try {
            String url = Const.googlemapslink  + Double.toString(mg.lastLatitude) + "," + Double.toString(mg.lastLongitude);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //------------------------------------------------------------------------


    //----------------------------------------------------------------------
    // fa partire il listener delle coordinate
    //----------------------------------------------------------------------
    private void ascoltaCoord() {
        try {
            mg.start();
        }
        catch ( Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //----------------------------------------------------------------------


    //----------------------------------------------------------------------
    //  setta i campi di testo latitudine e longitudine
    //----------------------------------------------------------------------
    private void setta_campi_coords() {

        try {
            myLat.setText(Double.toString(mg.lastLatitude));
            myLon.setText(Double.toString(mg.lastLongitude));

        }
        catch ( Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //----------------------------------------------------------------------



    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            mg.stop();
        }
        catch ( Exception e) {
            general.showError(this,e.getMessage());
        }
    }




    //------------------------------------------------------------------------
    // Bottone SALVA
    //------------------------------------------------------------------------
    public void salvataggio_finding(View v) {
        try {

            TextView t_q = (TextView)findViewById(R.id.txt_quantita);
            TextView t_l = (TextView)findViewById(R.id.txt_localita);
            TextView t_lat = (TextView)findViewById(R.id.txt_lat);
            TextView t_lon = (TextView)findViewById(R.id.txt_lon);
            TextView t_not =  (TextView)findViewById(R.id.txt_note);

            Spinner s_tf = (Spinner)findViewById(R.id.spn_tipo_finding);
            Spinner s_v = (Spinner)findViewById(R.id.spn_valutazione);

            Integer qqq = 0;
            if (!t_q.getText().toString().trim().equals("")) qqq = Integer.parseInt(t_q.getText().toString());

            findingdb fdb = new findingdb(this);
            f = new finding();

            Calendar rightNow = Calendar.getInstance();
            f.time = rightNow.getTimeInMillis();
            f.typefinding = fdb.returnTypeFinding(s_tf.getSelectedItem().toString());
            f.time_zone_offset = rightNow.get(Calendar.ZONE_OFFSET);
            f.time_dst_offset = rightNow.get(Calendar.DST_OFFSET);
            f.lasttime_finding = rightNow.getTimeInMillis();

            f.last_quantity = qqq;
            f.valutation_place = fdb.returnValutation(s_v.getSelectedItem().toString());
            f.location = t_l.getText().toString();
            f.note = t_not.getText().toString();;
            f.latitude = Double.parseDouble(t_lat.getText().toString());
            f.longitude = Double.parseDouble(t_lon.getText().toString());
            //f.gpstime = mg.lastGPSTimeMillis;;

            fdb.addReading(f);

            Toast.makeText(this,"Dato Salvato",Toast.LENGTH_SHORT).show();

            finish();
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //------------------------------------------------------------------------


    //------------------------------------------------------------------------
    // Bottone CHIUDI
    //------------------------------------------------------------------------
    public void chiudi(View v) {
        try {
            mg.stop();
            finish();
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }


    //----------------------------------------------------------------------
    // riempi spinner tipo_finding
    //----------------------------------------------------------------------
    private void fill_spn_tipo() {
        try {

            spn_tipo = (Spinner)findViewById(R.id.spn_tipo_finding);

            List<String> list = new ArrayList<String>();
            list.add("Fungaia");
            list.add("Auto");
            list.add("Parcheggio");
            list.add("Ristorante");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_tipo.setAdapter(dataAdapter);

        }
        catch (Exception e) {
            general.showError(this, e.getMessage());
        }

    }
    //----------------------------------------------------------------------


    //----------------------------------------------------------------------
    // riempi spinner tipo_finding
    //----------------------------------------------------------------------
    private void fill_spn_valutazione() {
        try {

            spn_valutazione = (Spinner)findViewById(R.id.spn_valutazione);

            List<String> list2 = new ArrayList<String>();
            list2.add("Niente");
            list2.add("Scarsa");
            list2.add("Discreta");
            list2.add("Buona");
            list2.add("Ottima");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , list2);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_valutazione.setAdapter(dataAdapter);

        }
        catch (Exception e) {
            general.showError(this, e.getMessage());
        }

    }
    //----------------------------------------------------------------------
}
