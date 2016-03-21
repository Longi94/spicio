package com.tlongdev.spicio.presentation.ui.activity;

import android.support.v7.app.AppCompatActivity;

import com.facebook.appevents.AppEventsLogger;

/**
 * @author Long
 * @since 2016. 03. 16.
 */
public abstract class SpicioActivity extends AppCompatActivity {

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
