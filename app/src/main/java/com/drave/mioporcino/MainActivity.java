package com.drave.mioporcino;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drave.mioporcino.database.findingdb;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private Button login_button_ok;
    private EditText usernameTxt, passwdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        usernameTxt = findViewById(R.id.username);
        passwdTxt = findViewById(R.id.password);
        login_button_ok = findViewById(R.id.login_button_ok);

        findingdb fdb = new findingdb(this);
        fdb.creaTables();
        fdb.checkFirstLogin();

        login_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameTxt.getText().length() > 0 && passwdTxt.getText().length() >0) {
                    if (fdb.checkLogin(usernameTxt.getText().toString(),passwdTxt.getText().toString())) {
                        vaia("HOME");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "User e/o password errati!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Inserire username e password!",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void vaia(String pg) {
        boolean isok = false;
        Intent intent = null;
        try {
            switch (pg.toUpperCase()) {
                case "MYGEO":
                    intent = new Intent(this, mycoords.class);
                    isok = true;
                    break;
                case "HOME":
                    intent = new Intent(this,homepage.class);
                    isok = true;
                    break;
            }

            if(isok && intent != null) startActivity(intent);
        }
        catch (Exception e) {
            String a = e.getMessage();
            //Toast.makeText(getApplicationContext(),e.getMessage());
        }
    }



}



