package com.example.rh0638.pokerbr;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ViewSessionsActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions);

        // Set the toolbar as the action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup "UP" button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        PokerDatabaseHelper pokerDatabaseHelper = new PokerDatabaseHelper(this);
        ListView listSessions = (ListView) findViewById(R.id.list_sessions);
        Toast toast;

        try {
            // Open a connection to the database
            db = pokerDatabaseHelper.getReadableDatabase();
            // Get all tuples
            cursor = pokerDatabaseHelper.getAllTuples(db, "Session");

            if (cursor.getCount() == 0) {
                toast = Toast.makeText(this, "No sessions in the database.", Toast.LENGTH_LONG);
                toast.show();
            } else {
                // Setup cursor adapter using cursor from last step
                TableCursorAdapter cursorAdapter = new TableCursorAdapter(this, cursor);
                // Attach cursor adapter to the ListView
                listSessions.setAdapter(cursorAdapter);
            }
        } catch (SQLException e) {
            toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

//        // Create the listener
//        AdapterView.OnItemClickListener itemClickListener =
//                new AdapterView.OnItemClickListener(){
//                    @Override
//                    public void onItemClick(AdapterView<?> listContacts,
//                                            View itemView,
//                                            int position,
//                                            long id) {
//                        Intent intent = new Intent(ViewSessionsActivity.this,
//                                SessionDetailActivity.class);
//                        intent.putExtra(SessionDetailActivity.EXTRA_CONTACTID, (int) id);
//                        startActivity(intent);
//                    }
//                };
//
//        // Assign the listener to the list view
//        listSessions.setOnItemClickListener(itemClickListener);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
