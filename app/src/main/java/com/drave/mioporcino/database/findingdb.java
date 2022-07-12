package com.drave.mioporcino.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.drave.mioporcino.utility.general;

import java.util.ArrayList;
import java.util.List;

public class findingdb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbFinding";

    // Contacts table name
    private static final String TABLE_FINDINGS = "Findings";
    private static final String TABLE_LOGIN = "LoginTable";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_TYPEFINDIG = "typefinding";
    private static final String KEY_TIME_ZONE_OFFSET = "timezoneoffset";
    private static final String KEY_TIME_DST_OFFSET = "timedstoffset";
    private static final String KEY_LASTTIME_FINDING  = "lasttime_finding";
    private static final String KEY_LASTQUANTITY = "last_quantity";
    private static final String KEY_VALUTATION_PLACE = "valutation_place";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_MUSHROMM_TYPE = "mushroom_type";
    private static final String KEY_NOTE = "note";
    private static final String KEY_GPSTIME = "gpstime";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LON = "longitude";


    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private Context thisContext = null;

    public findingdb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        thisContext = context;
    }

    //----------------------------------------------------------------------------------------------
    // Creating Tables
    //----------------------------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    //----------------------------------------------------------------------------------------------



    public void creaTables() {
        SQLiteDatabase db = this.getWritableDatabase();

        String CREATE_FINDINGS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FINDINGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TIME + " INTEGER,"
                + KEY_TYPEFINDIG + " INTEGER,"
                + KEY_TIME_ZONE_OFFSET + " INTEGER,"
                + KEY_TIME_DST_OFFSET + " INTEGER,"
                + KEY_LASTTIME_FINDING + " INTEGER,"
                + KEY_LASTQUANTITY + " INTEGER,"
                + KEY_VALUTATION_PLACE + " INTEGER,"
                + KEY_LOCATION + " TEXT,"
                + KEY_MUSHROMM_TYPE + " TEXT,"
                + KEY_NOTE + " TEXT,"
                + KEY_GPSTIME + " INTEGER,"
                + KEY_LAT + " REAL,"
                + KEY_LON + " REAL " + ")";


        db.execSQL(CREATE_FINDINGS_TABLE);
        //general.showError(thisContext,"Table: " + TABLE_FINDINGS + " created!");

        String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + "("
                + KEY_USERNAME + " TEXT PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT )";

        db.execSQL(CREATE_LOGIN_TABLE);
        //general.showError(thisContext,"Table: " + TABLE_LOGIN + " created!");
    }


    //----------------------------------------------------------------------------------------------
    // Upgrading database
    //----------------------------------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        //onCreate(db);
    }
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // Verifica che sia presente il primo utente, nel caso non ci sia lo aggiunge
    //----------------------------------------------------------------------------------------------
    public void checkFirstLogin() {
        boolean b = false;
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE username = 'drave' ";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            while (cursor.moveToNext()) {
                b = true;
            }
            cursor.close();

        }
        catch (Exception e) {
            b = false;
        }

        if (!b) insertFirstLogin();

    }
    private void insertFirstLogin() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME,"drave");
        values.put(KEY_PASSWORD, "1977");
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    //  VERIFICA LOGIN
    //----------------------------------------------------------------------------------------------
    public boolean checkLogin(String u,String p) {
        boolean res = false;

        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE username = '" +u+"' and password = '"+p+"'";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            while (cursor.moveToNext()) {
                res = true;
            }
            cursor.close();

        }
        catch (Exception e) {
            res = false;
        }

        return res;
    }
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    //  INSERT A NEW REDING
    //----------------------------------------------------------------------------------------------
    public void addReading(finding r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TIME, r.time);
        values.put(KEY_TYPEFINDIG, r.typefinding);
        values.put(KEY_TIME_ZONE_OFFSET, r.time_zone_offset);
        values.put(KEY_TIME_DST_OFFSET, r.time_dst_offset);
        values.put(KEY_LASTTIME_FINDING, r.lasttime_finding);
        values.put(KEY_LASTQUANTITY, r.last_quantity);
        values.put(KEY_VALUTATION_PLACE, r.valutation_place);
        values.put(KEY_LOCATION, r.location);
        values.put(KEY_MUSHROMM_TYPE, r.mushroom_type);
        values.put(KEY_NOTE, r.note);
        values.put(KEY_GPSTIME, r.gpstime);
        values.put(KEY_LAT, r.latitude);
        values.put(KEY_LON, r.longitude);

        // Inserting Row
        db.insert(TABLE_FINDINGS, null, values);
        db.close(); // Closing database connection
    }
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    //   MODIFICA RECORD FINDING
    //----------------------------------------------------------------------------------------------
    public int updateFinding(int id,finding r) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_LASTTIME_FINDING, r.lasttime_finding);
        values.put(KEY_LASTQUANTITY, r.last_quantity);
        values.put(KEY_VALUTATION_PLACE, r.valutation_place);
        values.put(KEY_MUSHROMM_TYPE, r.mushroom_type);
        values.put(KEY_NOTE, r.note);

        // updating row
        return db.update(TABLE_FINDINGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    //  DELETE FINDING
    //----------------------------------------------------------------------------------------------
    public int deleterecord(String criterio) {
        SQLiteDatabase db = this.getWritableDatabase();

        String c = " 1=1 ";
        if (!criterio.equals("") ) c += criterio;
        return  db.delete(TABLE_FINDINGS, c ,null);

    }
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // fill class FINDING
    //----------------------------------------------------------------------------------------------
    private void fill(finding r,Cursor cursor){
        r.id=cursor.getInt(cursor.getColumnIndex(KEY_ID));
        r.time=cursor.getLong(cursor.getColumnIndex(KEY_TIME));
        r.typefinding=cursor.getInt(cursor.getColumnIndex(KEY_TYPEFINDIG));
        r.time_zone_offset=cursor.getLong(cursor.getColumnIndex(KEY_TIME_ZONE_OFFSET));
        r.time_dst_offset=cursor.getLong(cursor.getColumnIndex(KEY_TIME_DST_OFFSET));
        r.lasttime_finding=cursor.getLong(cursor.getColumnIndex(KEY_LASTTIME_FINDING));
        r.last_quantity=cursor.getInt(cursor.getColumnIndex(KEY_LASTQUANTITY));
        r.valutation_place=cursor.getInt(cursor.getColumnIndex(KEY_VALUTATION_PLACE));
        r.location=cursor.getString(cursor.getColumnIndex(KEY_LOCATION));
        r.mushroom_type=cursor.getString(cursor.getColumnIndex(KEY_MUSHROMM_TYPE));
        r.note=cursor.getString(cursor.getColumnIndex(KEY_NOTE));
        r.gpstime=cursor.getLong(cursor.getColumnIndex(KEY_GPSTIME));
        r.latitude=cursor.getDouble(cursor.getColumnIndex(KEY_LAT));
        r.longitude=cursor.getDouble(cursor.getColumnIndex(KEY_LON));
    }
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // Getting All Readings in ordine di data ritrovamento
    //----------------------------------------------------------------------------------------------
    public List<finding> getAllFindings() {
        List<finding> findingList = new ArrayList<finding>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FINDINGS + " ORDER BY LASTTIME_FINDING DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                finding r = new finding();
                fill(r,cursor);

                // Adding Finding to list
                findingList.add(r);
            } while (cursor.moveToNext());
        }

        // return contact list
        return findingList;
    }
    //----------------------------------------------------------------------------------------------


    // Getting All Readings
    public List<finding> getTypeFindings(Integer tipo) {
        List<finding> findingsList = new ArrayList<finding>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FINDINGS + " WHERE 1=1 ";
        if (tipo > 0) selectQuery += " AND typefinding = " + tipo;
        selectQuery += " ORDER BY LASTTIME_FINDING DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                finding r = new finding();
                fill(r,cursor);

                // Adding finding to list
                findingsList.add(r);
            } while (cursor.moveToNext());
        }

        // return contact list
        return findingsList;
    }

    // Getting Number of readings to send
    /*public int getNRToSend() {

        String selectQuery = "SELECT COUNT(ID) AS NR FROM " + TABLE_FINDINGS + " WHERE "+KEY_SENT + "=0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        else{
            return 0;
        }

    }*/



    public int returnTypeFinding(String s) {
        if (s.equals("") ) return 0;

        if (s.toUpperCase().equals("FUNGAIA")) return 1;
        if (s.toUpperCase().equals("AUTO")) return 2;
        if (s.toUpperCase().equals("PARCHEGGIO")) return 3;
        if (s.toUpperCase().equals("RISTORANTE")) return 4;

        return 0;
    }

    public String returnTypeFinding(int i) {
        if (i == 0) return "";

        if (i == 1) return "FUNGAIA";
        if (i == 2) return "AUTO";
        if (i == 3) return "PARCHEGGIO";
        if (i == 4) return "RISTORANTE";

        return "";
    }


    public int returnValutation(String s) {

        if (s.equals("") || s.toUpperCase().equals("NIENTE")) return 0;

        if (s.toUpperCase().equals("SCARSA")) return 1;
        if (s.toUpperCase().equals("DISCRETA")) return 2;
        if (s.toUpperCase().equals("BUONA")) return 3;
        if (s.toUpperCase().equals("OTTIMA")) return 4;

        return 0;
    }

    public String returnValutation(int i) {
        if (i == 0) return "NIENTE";

        if (i == 1) return "SCARSA";
        if (i == 2) return "DISCRETA";
        if (i == 3) return "BUONA";
        if (i == 4) return "OTTIMA";

        return "";
    }
}
