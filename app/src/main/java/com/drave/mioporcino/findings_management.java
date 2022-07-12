package com.drave.mioporcino;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.drave.mioporcino.database.findingdb;
import com.drave.mioporcino.utility.general;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class findings_management extends AppCompatActivity {

    Spinner spn_tipo, spn_valutazione;
    ImageButton dtFrom,dtTo;
    EditText man_date_from,man_date_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findings_management);
        getSupportActionBar().hide();

        dtFrom = (ImageButton)findViewById(R.id.man_cal_from);
        dtTo = (ImageButton)findViewById(R.id.man_cal_to);

        man_date_from = (EditText)findViewById(R.id.man_date_from);
        man_date_to = (EditText)findViewById(R.id.man_date_to);

        fill_spn_tipo();
        fill_spn_valutazione();


        dtFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showCalendar(0);
            }
        });

        dtTo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showCalendar(1);
            }
        });
    }

    //---------------------------------------------------------------------------------------
    //  CANCELLA TUTTO
    //---------------------------------------------------------------------------------------
    public void deleteall(View v) {
        try {
            findingdb fdb = new findingdb(this);
            fdb.deleterecord("");
            Toast.makeText(this," CANCELLATO TUTTO ",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //---------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------
    //  CANCELLA IN DETTAGLIO
    //---------------------------------------------------------------------------------------
    public void deleteDetails(View v) {
        try {
            findingdb fdb = new findingdb(this);

            EditText edt_from = (EditText)findViewById(R.id.man_date_from);
            EditText edt_to = (EditText)findViewById(R.id.man_date_to);
            Spinner sp_tipo = (Spinner)findViewById(R.id.man_search_tipo);
            Spinner sp_val = (Spinner)findViewById(R.id.man_valutazione);

            String From_date = edt_from.getText().toString();
            String To_date = edt_to.getText().toString();
            Integer tipo = fdb.returnTypeFinding(sp_tipo.getSelectedItem().toString());
            Integer valut = fdb.returnValutation(sp_val.getSelectedItem().toString());


            String c = creaCriterio(From_date,To_date,tipo,valut);
            if ( !c.equals(""))  {
                fdb.deleterecord(c);
                Toast.makeText(this,"Cancellazione eseguita!",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"Compilare correttamente i campi!",Toast.LENGTH_LONG).show();
            }

        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //---------------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------
    // bottone CHIUDI
    //-----------------------------------------------------------------------------------------------------------------
    public void chiudi1(View v) {
        try {
            finish();
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //-----------------------------------------------------------------------------------------------------------------



    //-----------------------------------------------------------------------------------------------------------------
    //  mostra DatePicker per scelta data
    //-----------------------------------------------------------------------------------------------------------------
    private void showCalendar(Integer richiedente) {
        try {

            final Calendar myCalendar = Calendar.getInstance();
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
            final Integer r = richiedente;

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    if (r == 0) man_date_from.setText(sdf.format(myCalendar.getTime()));
                    if (r == 1) man_date_to.setText(sdf.format(myCalendar.getTime()));
                }

            };

            new DatePickerDialog(findings_management.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();


        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }


    }


    //-----------------------------------------------------------------------------------------------------------------
    // ritorna il criterio della query di cancellazione
    //-----------------------------------------------------------------------------------------------------------------
    private String creaCriterio(String d1, String d2, Integer T, Integer V) {
        String mycriterio = "";

        if (!d1.equals("") && !d2.equals("")) mycriterio += " AND time  BETWEEN " + general.dateToLong(d1).toString() + "  AND " + general.dateToLong(d2).toString() ;

        if (T > 0) mycriterio += " AND typefinding = " + T.toString();

        if (V > 0) mycriterio += " AND valutation_place = " + V.toString();

        return mycriterio;
    }


    //----------------------------------------------------------------------
    // riempi spinner tipo_finding
    //----------------------------------------------------------------------
    private void fill_spn_tipo() {
        try {

            spn_tipo = (Spinner)findViewById(R.id.man_search_tipo);

            List<String> list = new ArrayList<String>();
            list.add("--------");
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

            spn_valutazione = (Spinner)findViewById(R.id.man_valutazione);

            List<String> list2 = new ArrayList<String>();
            list2.add("---------");
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
