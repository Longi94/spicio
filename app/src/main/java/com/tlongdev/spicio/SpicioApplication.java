package com.tlongdev.spicio;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.DaggerNetworkComponent;
import com.tlongdev.spicio.component.DaggerStorageComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.component.StorageComponent;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.NetworkModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.module.StorageModule;
import com.tlongdev.spicio.module.ThreadingModule;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public class SpicioApplication extends Application {

    private InteractorComponent mInteractorComponent;

    private NetworkComponent mNetworkComponent;

    private StorageComponent mStorageComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        FacebookSdk.sdkInitialize(this);

        mNetworkComponent = DaggerNetworkComponent.builder()
                .spicioAppModule(new SpicioAppModule(this))
                .networkModule(new NetworkModule())
                .build();

        mStorageComponent = DaggerStorageComponent.builder()
                .spicioAppModule(new SpicioAppModule(this))
                .storageModule(new StorageModule())
                .build();

        mInteractorComponent = DaggerInteractorComponent.builder()
                .spicioAppModule(new SpicioAppModule(this))
                .networkRepositoryModule(new NetworkRepositoryModule())
                .daoModule(new DaoModule())
                .threadingModule(new ThreadingModule())
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public StorageComponent getStorageComponent() {
        return mStorageComponent;
    }

    public void setStorageComponent(StorageComponent storageComponent) {
        this.mStorageComponent = storageComponent;
    }

    public InteractorComponent getInteractorComponent() {
        return mInteractorComponent;
    }
}
