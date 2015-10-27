package com.andryr.musicplayer.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andryr.musicplayer.R;
import com.andryr.musicplayer.fragments.dialog.ThemeDialog;
import com.andryr.musicplayer.utils.ThemeHelper;

/**
 * Created by Andry on 25/09/15.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
    }

    protected void setTheme() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean dark = ThemeHelper.isDarkThemeSelected(this);
        int theme = prefs.getInt(PreferencesActivity.KEY_PREF_THEME, ThemeDialog.BLUE_THEME);

        switch (theme) {
            case ThemeDialog.ORANGE_THEME:
                if(dark)
                {
                    Log.d("theme", "orange dark");
                    setTheme(R.style.AppThemeOrangeDark);
                }
                else {
                    Log.d("theme","orange light");
                    setTheme(R.style.AppThemeOrangeLight);
                }
                break;
            case ThemeDialog.BLUE_THEME:
                if(dark)
                {
                    Log.d("theme","blue dark");
                    setTheme(R.style.AppThemeBlueDark);
                }
                else {
                    Log.d("theme","blue light");
                    setTheme(R.style.AppThemeBlueLight);
                }
                break;
        }
    }
}