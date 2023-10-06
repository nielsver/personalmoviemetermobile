package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class insertactivity extends AppCompatActivity {

    @BindView(R.id.et_insert_first)
    EditText mInsertFirst;

    @BindView(R.id.et_insert_last)
    EditText mInsertLast;

    @BindView(R.id.sb_insert_rating)
    RatingBar rating;

    dbHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean currentTheme = sp.getBoolean("mode", false);
        boolean currentLanguage = sp.getBoolean("lang", false);
        if(currentTheme) {
            setTheme(R.style.Theme_Project);
            Log.d("MAIN", "true");
        }
        else {
            setTheme(R.style.Theme_Project_AppWidgetContainer);
            Log.d("MAIN", "false");
        }
        if(currentLanguage) {
            setTaal(insertactivity.this,"en");
            Log.d("SETTINGS", "true");
        }
        else {
            setTaal(insertactivity.this,"nl");
            Log.d("SETTINGS", "false");
        }
        setContentView(R.layout.activity_insertactivity);
        ButterKnife.bind(this);

        dbhelper = new dbHelper(this);

    }
    public void insertMovie(View view) {
        String moviename = mInsertFirst.getText().toString();
        String moviepicture = mInsertLast.getText().toString();
        float Rating = rating.getRating();

        if (moviename.equals("") || moviepicture.equals("") || rating == null) {
            Toast.makeText(this, "Please enter valid names", Toast.LENGTH_LONG).show();
            if (moviename.equals("")) {
                mInsertFirst.requestFocus();
            } else if (moviepicture.equals("")) {
                mInsertLast.requestFocus();
            } else {
                rating.requestFocus();
            }
        } else {
            ContentValues values = new ContentValues();
            values.put("movieName", moviename);
            values.put("Active", moviepicture);
            values.put("Rating", Rating);

            SQLiteDatabase db = dbhelper.getWritableDatabase();

            long id = db.insert(database.Feedentry.TABLE_NAME, null, values);

            if (id != -1) {
                setResult(RESULT_OK);
                Toast.makeText(this, "Succesfull Creation", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void setTaal(Activity activity, String taal) {
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("langCode",taal);
        editor.commit();

        Locale locale = new Locale(taal);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

}