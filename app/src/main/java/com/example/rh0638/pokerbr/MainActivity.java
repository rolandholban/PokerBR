package com.example.rh0638.pokerbr;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PokerDatabaseHelper pokerDatabaseHelper = new PokerDatabaseHelper(this);

        SQLiteDatabase db = pokerDatabaseHelper.getWritableDatabase();
        pokerDatabaseHelper.insertSession(db, 2, "16:00", "20:00", 300, 421);
        pokerDatabaseHelper.insertSession(db, 2, "22:00", "01:00", 300, 421);
        db.close();

        SQLiteDatabase db2 = pokerDatabaseHelper.getReadableDatabase();
        Cursor cursor = db2.query("Session", new String[] {"start_time", "end_time"}, null, null, null, null, null);
        cursor.moveToLast();


        LocalTime startTime = LocalTime.parse(cursor.getString(0));
        LocalTime endTime = LocalTime.parse(cursor.getString(1));

        System.out.println("");
        System.out.println("PRINTING DEBUGGING INFO");
        System.out.println(endTime.getHour() - startTime.getHour());
        System.out.println("");
    }


}

