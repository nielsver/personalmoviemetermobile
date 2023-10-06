package com.example.project;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;
import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class movies extends AppCompatActivity implements movieadapter.ListItemClickListener {



    @BindView(R.id.gridlayout)
        RecyclerView rvmovie;

    movieadapter mAdapter;
    dbHelper dbhelper;
    String query = "";

    public static final String EXTRA_MOVIE = "movie_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = getIntent().getStringExtra(Intent.EXTRA_TEXT);
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
            setTaal(movies.this,"en");
            Log.d("SETTINGS", "true");
        }
        else {
            setTaal(movies.this,"nl");
            Log.d("SETTINGS", "false");
        }
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        RecyclerView rvMovies = findViewById(R.id.gridlayout);

        dbhelper = new dbHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        String[] columns = {database.Feedentry._ID, database.Feedentry.COLUMN_NAME_TITLE,database.Feedentry.COLUMN_NAME_SUBTITLE, database.Feedentry.COLUMN_NAME_RATING};
        Cursor data;

        if (query != null && !query.equals("")) {
            Log.d("movies",query);
            String selection = database.Feedentry.COLUMN_NAME_TITLE + " LIKE ?";
            String[] selectionArgs = {"%" + query + "%"};
            data = db.query(database.Feedentry.TABLE_NAME, columns, selection, selectionArgs, null, null,  database.Feedentry._ID + " ASC");
        } else {
            Log.d("movies","");
            data = db.query(database.Feedentry.TABLE_NAME, columns, null, null, null, null,  database.Feedentry._ID + " ASC");
        }
        Log.d("Movies", "elements in cursor: " + data.getCount());

        // Create adapter passing in the sample user data
        mAdapter = new movieadapter(data,this);
        // Attach the adapter to the recyclerview to populate items
        rvMovies.setAdapter(mAdapter);
        // Set layout manager to position the items
        rvMovies.setLayoutManager(new GridLayoutManager(this,2));
        rvMovies.hasFixedSize();

        // That's all!
        }
    protected void onDestroy() {
        super.onDestroy();

        dbhelper.close();
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
    public void onListItemClick(int position, RecyclerView.ViewHolder itemView) {
        position = position + 1;
        Intent intent = new Intent(movies.this, DetailsActivity.class);
        String z = String.valueOf(position);
        Log.d("position", z);
        intent.putExtra(EXTRA_MOVIE, z);

        startActivity(intent);
    }
}