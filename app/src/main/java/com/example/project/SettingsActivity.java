package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    boolean themeSwitch = false;
    boolean resourceSwitch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean currentTheme = sp.getBoolean("mode", false);
        boolean currentLanguage = sp.getBoolean("lang", false);
        if(currentTheme) {
            setTheme(R.style.Theme_Project);
            Log.d("SETTINGS", "true");
        }
        else {
            setTheme(R.style.Theme_Project_AppWidgetContainer);
            Log.d("SETTINGS", "false");
        }
        if(currentLanguage) {
            setTaal(SettingsActivity.this,"en");
            Log.d("SETTINGS", "true");
        }
        else {
            setTaal(SettingsActivity.this,"nl");
            Log.d("SETTINGS", "false");
        }
        setContentView(R.layout.settings_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sp.registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String s) {
        if(s.equals("mode")) {
            boolean pref = sp.getBoolean("mode", false);
            Log.d("test", pref+"");
            if(pref) {
                setTheme(R.style.Theme_Project);
                // Deze boolean wordt gebruikt in onStop om aan te geven dat het om het veranderen van je theme setting.
                // Daarna wordt finish() opgeroepen om je activity the stoppen.
                // In onStop wordt dan je activity hermaakt.
                themeSwitch = true;
                finish();
            }
            else {
                setTheme(R.style.Theme_Project_AppWidgetContainer);
                themeSwitch = true;
                finish();
            }
        }
        if(s.equals("lang")){
            boolean prefe = sp.getBoolean("lang", false);
            Log.d("test", prefe+"");
            if(prefe) {
                setTaal(SettingsActivity.this,"en");
                resourceSwitch = true;
                finish();
            }
            else {
                setTaal(SettingsActivity.this,"nl");
                resourceSwitch = true;
                finish();
            }
        }

    }

    // onStop oproepen en hierin je activity opnieuw aanmaken om je nieuwe theme te zetten.
    // Dit om je theme te kunnen inladen alvorens je layout te inflaten, wat enkel kan in onCreate();
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(themeSwitch) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(resourceSwitch) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }
    public void setTaal(Activity activity, String taal) {
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