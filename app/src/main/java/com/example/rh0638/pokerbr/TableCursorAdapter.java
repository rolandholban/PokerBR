package com.example.rh0638.pokerbr;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TableCursorAdapter extends CursorAdapter {
    TableCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.table_row, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvId = (TextView) view.findViewById(R.id.tvId);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvStartTime = (TextView) view.findViewById(R.id.tvStartTime);
        TextView tvEndTime = (TextView) view.findViewById(R.id.tvEndTime);
        TextView tvStartChips = (TextView) view.findViewById(R.id.tvStartChips);
        TextView tvEndChips = (TextView) view.findViewById(R.id.tvEndChips);

        // Extract properties from cursor
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        String startTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"));
        String endTime = cursor.getString(cursor.getColumnIndexOrThrow("end_time"));
        int startChips = cursor.getInt(cursor.getColumnIndexOrThrow("start_chips"));
        int endChips = cursor.getInt(cursor.getColumnIndexOrThrow("end_chips"));

        // Populate fields with extracted properties
        tvId.setText(String.valueOf(id));
        tvDate.setText(date);
        tvStartTime.setText(startTime);
        tvEndTime.setText(endTime);
        tvStartChips.setText(String.valueOf(startChips));
        tvEndChips.setText(String.valueOf(endChips));
    }
}
