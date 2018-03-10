package com.example.android.habittracker;

import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;

import static android.text.style.TtsSpan.GENDER_MALE;


/**
 * Created by Manik on 25-04-2017.
 */

public class HabitContract {

    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "Habits";
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_NAME = "name";

        public final static String COLUMN_GENDER = "gender";

        public final static String COLUMN_AGE = "age";
        public static final String GENDER_UNKNOWN = "UNKNOWN";
        public static final String GENDER_MALE = "MALE";
        public static final String GENDER_FEMALE = "FEMALE";
    }
}
