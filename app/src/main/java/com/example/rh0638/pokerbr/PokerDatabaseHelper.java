package com.example.rh0638.pokerbr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PokerDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PokerBankroll";
    private static final int DB_VERSION = 1;

    PokerDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS Session;");
        db.execSQL("CREATE TABLE Session (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "date DATETIME DEFAULT CURRENT_DATE, "
                + "big_blind INTEGER, "
                + "start_time TEXT, "
                + "end_time TEXT, "
                + "start_chips INTEGER, "
                + "end_chips INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertSession(SQLiteDatabase db, String date, int big_blind, String start_time, String end_time, int start_chips, int end_chips) {
        ContentValues sessionValues = new ContentValues();

        sessionValues.put("date", date);
        sessionValues.put("big_blind", big_blind);
        sessionValues.put("start_time", start_time);
        sessionValues.put("end_time", end_time);
        sessionValues.put("start_chips", start_chips);
        sessionValues.put("end_chips", end_chips);

        db.insert("Session", null, sessionValues);
    }

    public Cursor getAllTuples(SQLiteDatabase db, String table_name) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name, null);
        return cursor;
    }
}
