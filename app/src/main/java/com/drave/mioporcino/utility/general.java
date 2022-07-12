package com.drave.mioporcino.utility;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class general {

    public static void showError(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public static Long dateToLong(String d) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        try {
            Date mDate = sdf.parse(d);
            return mDate.getTime();
        }
        catch (Exception e) {
            return null;
        }
    }
}
