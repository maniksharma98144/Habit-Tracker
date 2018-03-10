package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.habittracker.HabitContract.HabitEntry;

/**
 * Created by Manik on 23-04-2017.
 */

public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;

    private EditText mAgeEditText;

    private Spinner mGenderSpinner;


    private String mGender = HabitEntry.GENDER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameEditText = (EditText) findViewById(R.id.edit_name);
        mAgeEditText = (EditText) findViewById(R.id.edit_age);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.male))) {
                        mGender = HabitEntry.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.female))) {
                        mGender = HabitEntry.GENDER_FEMALE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = HabitEntry.GENDER_UNKNOWN;
            }
        });
    }

    private void insertHabit() {
        String nameString = mNameEditText.getText().toString().trim();
        String ageString = mAgeEditText.getText().toString().trim();
        int age = Integer.parseInt(ageString);

        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, nameString);
        values.put(HabitEntry.COLUMN_GENDER, mGender);
        values.put(HabitEntry.COLUMN_AGE, age);
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    public void save(View view) {
        insertHabit();
        finish();
    }
}
