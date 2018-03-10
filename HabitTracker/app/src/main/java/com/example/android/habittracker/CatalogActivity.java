package com.example.android.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.habittracker.HabitContract.HabitEntry;

public class CatalogActivity extends AppCompatActivity {
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        Button fab = (Button) findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_GENDER,
                HabitEntry.COLUMN_AGE};
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        query(cursor);
    }

    private void query(Cursor cursor) {
        TextView displayView = (TextView) findViewById(R.id.text_view);

        try {
            displayView.setText("The habit table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_NAME + " - " +
                    HabitEntry.COLUMN_GENDER + " - " +
                    HabitEntry.COLUMN_AGE + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
            int genderColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_GENDER);
            int ageColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_AGE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentGender + " - " +
                        currentAge));
            }
        } finally {
            cursor.close();
        }
    }
}
