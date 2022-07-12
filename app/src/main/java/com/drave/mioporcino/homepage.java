package com.drave.mioporcino;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.drave.mioporcino.database.findingdb;
import com.drave.mioporcino.utility.general;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class homepage extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        getSupportActionBar().hide();


        if (!checkPermission()) requestPermission();


    }



    /*---------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------*/
    /*         CHECK PERMESSI                                                                            */
    /*---------------------------------------------------------------------------------------------------*/

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean coarseAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && coarseAccepted) {
                        Toast.makeText(this,"Permessi concessi, APP pronta per l'uso!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        finish();
                    }
                }

                break;
        }
    }
    /*---------------------------------------------------------------------------------------------------*/
    /*---------------------------------------------------------------------------------------------------*/

    //---------------------------------------------------------------------------------------
    //   NUOVO RITROVAMENTO
    //---------------------------------------------------------------------------------------
    public void newfinding(View v) {
        try {
            Intent intent = new Intent(this, new_finding.class);
            startActivity(intent);
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //---------------------------------------------------------------------------------------


    //---------------------------------------------------------------------------------------
    //   RICERCA LOCATION
    //---------------------------------------------------------------------------------------
    public void search(View v) {
        try {
            Intent intent = new Intent(this, search_findings.class);
            startActivity(intent);
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //---------------------------------------------------------------------------------------

    //---------------------------------------------------------------------------------------
    //  CANCELLA TUTTO
    //---------------------------------------------------------------------------------------
    public void goManagement(View v) {
        try {
            Intent intent = new Intent(this, findings_management.class);
            startActivity(intent);
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
    //---------------------------------------------------------------------------------------

    public void goLogout(View v) {
        try {
            finish();
        }
        catch (Exception e) {
            general.showError(this,e.getMessage());
        }
    }
}
