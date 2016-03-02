package com.tlongdev.spicio;

import android.app.Application;

import com.tlongdev.spicio.component.DaggerNetworkComponent;
import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.module.SpicioAppModule;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public class SpicioApplication extends Application {

    private NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        mNetworkComponent = DaggerNetworkComponent.builder()
                .spicioAppModule(new SpicioAppModule(this))
                .build();
    }

    public NetworkComponent getNetWorkComponent() {
        return mNetworkComponent;
    }
}
