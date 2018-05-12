package com.example.rh0638.pokerbr;

import android.annotation.SuppressLint;
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

public class SessionDetailActivity extends AppCompatActivity {

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_detail);

        // Set the toolbar as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup "UP" button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Create a cursor
        SQLiteOpenHelper pokerDatabaseHelper = new PokerDatabaseHelper(this);
        int sessionId = (int) getIntent().getExtras().get("EXTRA_SESSION_ID");

        try {
            SQLiteDatabase db = pokerDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Session WHERE _id =" + String.valueOf(sessionId), null);

            if (cursor.moveToNext()) {
                // Find fields to populate in inflated template
                TextView tvSessionId = (TextView)findViewById(R.id.tvSessionId);
                TextView tvDate = (TextView)findViewById(R.id.tvDate);
                TextView tvBigBlind = (TextView)findViewById(R.id.tvBigBlind);
                TextView tvStartTime = (TextView)findViewById(R.id.tvStartTime);
                TextView tvEndTime = (TextView)findViewById(R.id.tvEndTime);
                TextView tvStartChips = (TextView)findViewById(R.id.tvStartChips);
                TextView tvEndChips = (TextView)findViewById(R.id.tvEndChips);
                TextView tvProfit = (TextView)findViewById(R.id.tvProfit);
                TextView tvLength = (TextView)findViewById(R.id.tvLength);
                TextView tvROI = (TextView)findViewById(R.id.tvROI);
                TextView tvMoneyPerHour = (TextView)findViewById(R.id.tvMoneyPerHour);

                // Extract properties from cursor
                int id = cursor.getInt(0);
                String date = cursor.getString(1);
                int bigBlind = cursor.getInt(2);
                String startTime = cursor.getString(3);
                String endTime = cursor.getString(4);
                int startChips = cursor.getInt(5);
                int endChips = cursor.getInt(6);

                SimpleDateFormat format = new SimpleDateFormat("h:mm a");
                Date date1 = format.parse(startTime);
                Date date2 = format.parse(endTime);
                long timeDiff = date2.getTime() - date1.getTime();

                if (timeDiff < 0) timeDiff += (24 * 3600000);

                int profit = endChips - startChips;
                double roi = (double) profit / (double) startChips * 100;
                double moneyPerHour = (double) profit / ((double)timeDiff / 3600000);

                // Populate fields with extracted properties and calculations
                tvSessionId.setText(String.valueOf(id));
                tvDate.setText(date);
                tvBigBlind.setText("$ " + String.valueOf(bigBlind));
                tvStartTime.setText(startTime);
                tvEndTime.setText(endTime);
                tvStartChips.setText("$ " + String.valueOf(startChips));
                tvEndChips.setText("$ " + String.valueOf(endChips));
                tvProfit.setText("$ " + String.valueOf(profit));
                tvLength.setText((timeDiff / 3600000) + " hr/s " + (timeDiff % 3600000) / 60000 + " min");
                tvROI.setText(String.format("%.2f", roi) + "%");
                tvMoneyPerHour.setText("$ " + String.format("%.2f", moneyPerHour));
            }

            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        } catch(ParseException ex) {
            Toast toast = Toast.makeText(this, "parse error", Toast.LENGTH_SHORT);
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
