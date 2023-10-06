package com.example.project;

import static com.example.project.database.Feedentry.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class dbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movies";
    public static final int DATABASE_VERSION = 9;
    Context context;

    public static final String SQL_CREATE_TABLES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    database.Feedentry._ID + " INTEGER PRIMARY KEY, " +
                    database.Feedentry.COLUMN_NAME_TITLE + " TEXT, " +
                    database.Feedentry.COLUMN_NAME_SUBTITLE + " TEXT," +
                    database.Feedentry.COLUMN_NAME_RATING + " FLOAT)";

    public static final String SQL_DROP_TABLES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public dbHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLES);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_TABLES);
        onCreate(sqLiteDatabase);
    }
    void deleteRow(String rowid){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{rowid});
        if(result == -1)
            Toast.makeText(getContext(), "couldn't delete", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(),"Delete", Toast.LENGTH_LONG).show();
    }
    private Context getContext() {
        return this.context;
    }
}
