package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;



import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText searchView;

    @SuppressLint("ResourceAsColor")
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
            setTaal(MainActivity.this,"en");
            Log.d("SETTINGS", "true");
        }
        else {
            setTaal(MainActivity.this,"nl");
            Log.d("SETTINGS", "false");
        }
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.simpleSearchView);
        if (currentTheme) {
            searchView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            searchView.setHintTextColor(ContextCompat.getColor(this, R.color.black));
            searchView.setTextColor(ContextCompat.getColor(this, R.color.black));
        } else {
            searchView.setBackgroundColor(ContextCompat.getColor(this, R.color.black));
            searchView.setHintTextColor(ContextCompat.getColor(this, R.color.white));
            searchView.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(searchView);
                    return true;
                }
                return false;
            }
        });
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

    public void search(View view) {
        String x = searchView.getText().toString();
        Log.d("context", x);
        Intent intent = new Intent(MainActivity.this,movies.class);
        intent.putExtra(Intent.EXTRA_TEXT,x);
        startActivity(intent);

    }

    public void newMovie(View view) {
        Intent intent = new Intent(MainActivity.this,insertactivity.class);
        startActivity(intent);
    }
    public void settings(View view){
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }


}