package com.example.rh0638.pokerbr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the toolbar as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        PokerDatabaseHelper pokerDatabaseHelper = new PokerDatabaseHelper(this);
//
//        SQLiteDatabase db = pokerDatabaseHelper.getWritableDatabase();
//        pokerDatabaseHelper.insertSession(db, 2, "16:00", "20:00", 300, 421);
//        pokerDatabaseHelper.insertSession(db, 2, "22:00", "01:00", 300, 421);
//        db.close();
//
//        SQLiteDatabase db2 = pokerDatabaseHelper.getReadableDatabase();
//        Cursor cursor = db2.query("Session", new String[] {"start_time", "end_time"}, null, null, null, null, null);
//        cursor.moveToLast();
//
//
//        LocalTime startTime = LocalTime.parse(cursor.getString(0));
//        LocalTime endTime = LocalTime.parse(cursor.getString(1));
//
//        System.out.println("");
//        System.out.println("PRINTING DEBUGGING INFO");
//        System.out.println(endTime.getHour() - startTime.getHour());
//        System.out.println("");
    }

    // Add any items in the menu_main to the app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the app bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // This method gets called when an action on the app bar is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_session:
                Intent intent = new Intent(this, AddSessionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onViewSessions(View view) {
        Intent intent = new Intent(this, ViewSessionsActivity.class);
        startActivity(intent);
    }
}

