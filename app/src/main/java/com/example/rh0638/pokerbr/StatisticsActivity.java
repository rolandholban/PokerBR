package com.example.rh0638.pokerbr;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // Set the toolbar as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup "UP" button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        SQLiteOpenHelper pokerDatabaseHelper = new PokerDatabaseHelper(this);

        // Find fields to populate
        TextView tvTotalProfit = (TextView) findViewById(R.id.tvProfit);
        TextView tvTotalTime = (TextView) findViewById(R.id.tvDate);
        TextView tvROI = (TextView) findViewById(R.id.tvROI);
        TextView tvMoneyPerHour = (TextView) findViewById(R.id.tvMoneyPerHour);

        double totalProfit = 0, roi = 0, moneyPerHour = 0, totalInvested = 0;
        long totalTime = 0;

        try {
            SQLiteDatabase db = pokerDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Session", null);

            while (cursor.moveToNext()) {

                // Extract properties from cursor
                String startTime = cursor.getString(3);
                String endTime = cursor.getString(4);
                int startChips = cursor.getInt(5);
                int endChips = cursor.getInt(6);

                SimpleDateFormat format = new SimpleDateFormat("h:mm a");
                Date date1 = format.parse(startTime);
                Date date2 = format.parse(endTime);
                long timeDiff = date2.getTime() - date1.getTime();

                if (timeDiff < 0) timeDiff += (24 * 3600000);

                double sessProfit = endChips - startChips;

                totalProfit += sessProfit;
                totalTime += timeDiff;
                totalInvested += startChips;
            }

            roi = totalProfit / totalInvested * 100;
            moneyPerHour = totalProfit / (totalTime / 3600000);

            // Populate fields with extracted properties and calculations
            tvTotalProfit.setText("$ " + String.valueOf(totalProfit));
            tvTotalTime.setText((totalTime / 3600000) + " hr/s " + (totalTime % 3600000) / 60000 + " min");
            tvROI.setText(String.format("%.2f", roi) + "%");
            tvMoneyPerHour.setText("$ " + String.format("%.2f", moneyPerHour));

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        } catch (ParseException ex) {
            Toast toast = Toast.makeText(this, "Parse error", Toast.LENGTH_SHORT);
            toast.show();
        }
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
}
