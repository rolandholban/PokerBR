package com.example.rh0638.pokerbr;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddSessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_session);

        // Set the toolbar as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup "UP" button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    // Add any items in the menu_main to the app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the app bar
        getMenuInflater().inflate(R.menu.menu_add_session, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // This method gets called when an action on the app bar is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_sessions:
                Intent intent = new Intent(this, ViewSessionsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onInsertSession(View view) {
        // Get access to the EditTexts
        EditText etDate = findViewById(R.id.etDate);
        EditText etBigBlind = findViewById(R.id.etBigBlind);
        EditText etStartTime = findViewById(R.id.etStartTime);
        EditText etEndTime = findViewById(R.id.etEndTime);
        EditText etStartChips = findViewById(R.id.etStartChips);
        EditText etEndChips = findViewById(R.id.etEndChips);

        // Open a database session
        PokerDatabaseHelper pokerDatabaseHelper = new PokerDatabaseHelper(this);
        SQLiteDatabase db = pokerDatabaseHelper.getWritableDatabase();

        // Insert the session
        pokerDatabaseHelper.insertSession(db,
                etDate.getText().toString(),
                Integer.parseInt(etBigBlind.getText().toString()),
                etStartTime.getText().toString(),
                etEndTime.getText().toString(),
                Integer.parseInt(etStartChips.getText().toString()),
                Integer.parseInt(etEndChips.getText().toString()));

        // Close the connection to the dabase
        db.close();
        pokerDatabaseHelper.close();
    }

}
