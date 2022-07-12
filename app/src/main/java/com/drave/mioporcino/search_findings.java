package com.drave.mioporcino;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.drave.mioporcino.database.Contatto;
import com.drave.mioporcino.database.finding;
import com.drave.mioporcino.database.findingdb;
import com.drave.mioporcino.utility.Const;
import com.drave.mioporcino.utility.CustomAdapter;
import com.drave.mioporcino.utility.general;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class search_findings extends AppCompatActivity {

    finding f ;
    findingdb fdb;
    ListView lst_f;
    Spinner spn_search_tipo;
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yy HH:mm:ss");

    AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
            Contatto c = (Contatto) adapter.getItemAtPosition(position);
            elaboraItem(c);
            return false;
        }
    };

    //-----------------------------------------------------------------------------------------------------------------
    //  OnCreate
    //-----------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_findings);
        getSupportActionBar().hide();

        lst_f = (ListView)findViewById(R.id.lst_findings);
        lst_f.setOnItemLongClickListener(longClickListener);

        fill_spn_man_tipo();

        startActivity();
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Esecuzione e creazione listview
    //-----------------------------------------------------------------------------------------------------------------
    public void startActivity() {
        try {

            fdb = new findingdb(this);
            List<finding> flist = fdb.getAllFindings();
            fillListView(flist);
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
    // bottone CERCA
    //-----------------------------------------------------------------------------------------------------------------
    public void search_findings(View v) {
        try {

            Spinner st = (Spinner)findViewById(R.id.spn_search_tipo);
            String sc = st.getSelectedItem().toString();

            fdb = new findingdb(this);
            List<finding> fsearch = fdb.getTypeFindings(fdb.returnTypeFinding(sc));
            fillListView(fsearch);

        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------
    // bottone CHIUDI
    //-----------------------------------------------------------------------------------------------------------------
    public void chiudi(View v) {
        try {
            finish();
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------
    // riempie la LISTVIEW in base alla ricerca
    //-----------------------------------------------------------------------------------------------------------------
    private void fillListView(List<finding> fl) {
        List list = new LinkedList();

        for (finding f:fl) {
            String v = fdb.returnValutation(f.valutation_place);
            String l = Const.googlemapslink + Double.toString(f.latitude) + "," + Double.toString(f.longitude);
            Date d = new Date(f.time);
            list.add(new Contatto(f.location,f.note,v,l,DATE_FORMAT.format(d),f.typefinding,f.id));
        }

        CustomAdapter adapter = new CustomAdapter(this,R.layout.rowcustom, list);
        lst_f.setAdapter(adapter);
    }
    //-----------------------------------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------
    //  elaborazione su pressione della riga della ListView
    //-----------------------------------------------------------------------------------------------------------------
    private void elaboraItem(Contatto c) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(c.getLink()));
            startActivity(i);
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------
    // riempi spinner tipo_finding
    //----------------------------------------------------------------------
    private void fill_spn_man_tipo() {
        try {

            spn_search_tipo = (Spinner)findViewById(R.id.spn_search_tipo);

            List<String> list = new ArrayList<String>();
            list.add("Tutto");
            list.add("Fungaia");
            list.add("Auto");
            list.add("Parcheggio");
            list.add("Ristorante");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_search_tipo.setAdapter(dataAdapter);

        }
        catch (Exception e) {
            general.showError(this, e.getMessage());
        }

    }
    //----------------------------------------------------------------------

}
