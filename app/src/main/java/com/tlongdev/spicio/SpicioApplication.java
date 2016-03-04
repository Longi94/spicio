package com.tlongdev.spicio;

import android.app.Application;

import com.tlongdev.spicio.component.DaggerNetworkComponent;
import com.tlongdev.spicio.component.DaggerNetworkRepositoryComponent;
import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.component.NetworkRepositoryComponent;
import com.tlongdev.spicio.module.NetworkModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
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

    private NetworkRepositoryComponent mNetworkRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);

        mNetworkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule())
                .build();

        mNetworkRepositoryComponent = DaggerNetworkRepositoryComponent.builder()
                .spicioAppModule(new SpicioAppModule(this))
                .networkRepositoryModule(new NetworkRepositoryModule())
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public NetworkRepositoryComponent getNetworkRepositoryComponent() {
        return mNetworkRepositoryComponent;
    }
}
