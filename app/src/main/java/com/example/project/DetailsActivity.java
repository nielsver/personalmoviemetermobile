package com.example.project;

import static com.example.project.movies.EXTRA_MOVIE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.movie_name)
    TextView nameTextView;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.movie_Rating)
    TextView rating;

    dbHelper dbHelper;
    String movieID;


    @SuppressLint("Range")
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
            setTaal(DetailsActivity.this,"en");
            Log.d("SETTINGS", "true");
        }
        else {
            setTaal(DetailsActivity.this,"nl");
            Log.d("SETTINGS", "false");
        }
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        Intent detailsIntent = getIntent();

        dbHelper = new dbHelper(this);

        if(detailsIntent.hasExtra(EXTRA_MOVIE)) {
            movieID = detailsIntent.getStringExtra(EXTRA_MOVIE);

        }
        else {
            Log.d("test","no movieid");
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] ids = { movieID };

        Cursor movieData = db.query(database.Feedentry.TABLE_NAME, null, "_ID = ?", ids , null, null, null);

        if (movieData.moveToFirst()) {

            nameTextView.setText(movieData.getString(movieData.getColumnIndex(database.Feedentry.COLUMN_NAME_TITLE)));
            description.setText(movieData.getString(movieData.getColumnIndex(database.Feedentry.COLUMN_NAME_SUBTITLE)));
            rating.setText(String.valueOf(movieData.getInt(movieData.getColumnIndexOrThrow(database.Feedentry.COLUMN_NAME_RATING))) + " stars");

        } else {
            Log.d("cursor","empty");
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




    @Override
    protected void onDestroy() {
        super.onDestroy();

        dbHelper.close();
    }
    public void delete(View view) {
        dbHelper.deleteRow(movieID);
        Intent intent = new Intent(DetailsActivity.this, movies.class);
        startActivity(intent);
    }
}