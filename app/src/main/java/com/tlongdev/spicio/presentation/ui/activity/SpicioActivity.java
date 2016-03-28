package com.tlongdev.spicio.presentation.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.appevents.AppEventsLogger;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;

/**
 * @author Long
 * @since 2016. 03. 16.
 */
public abstract class SpicioActivity extends AppCompatActivity {

    protected SpicioApplication mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (SpicioApplication) getApplication();

        //Set the color of the status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

}
