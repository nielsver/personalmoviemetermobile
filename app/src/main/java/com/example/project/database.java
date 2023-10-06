package com.example.project;

import android.provider.BaseColumns;

public final class database {
    private  database(){}

    public static class Feedentry implements BaseColumns{
        public static final String TABLE_NAME = "Movies";
        public static final String COLUMN_NAME_TITLE = "movieName";
        public static final String COLUMN_NAME_SUBTITLE = "Active";
        public static final String COLUMN_NAME_RATING = "rating";


    }
}
