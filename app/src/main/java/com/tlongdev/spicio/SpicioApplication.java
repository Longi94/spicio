package com.tlongdev.spicio;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.facebook.FacebookSdk;
import com.tlongdev.spicio.component.ActivityComponent;
import com.tlongdev.spicio.component.DaggerActivityComponent;
import com.tlongdev.spicio.component.DaggerDrawerManagerComponent;
import com.tlongdev.spicio.component.DaggerInteractorComponent;
import com.tlongdev.spicio.component.DaggerNetworkComponent;
import com.tlongdev.spicio.component.DaggerPresenterComponent;
import com.tlongdev.spicio.component.DaggerProfileManagerComponent;
import com.tlongdev.spicio.component.DaggerStorageComponent;
import com.tlongdev.spicio.component.DrawerManagerComponent;
import com.tlongdev.spicio.component.InteractorComponent;
import com.tlongdev.spicio.component.NetworkComponent;
import com.tlongdev.spicio.component.PresenterComponent;
import com.tlongdev.spicio.component.ProfileManagerComponent;
import com.tlongdev.spicio.component.StorageComponent;
import com.tlongdev.spicio.module.AuthenticationModule;
import com.tlongdev.spicio.module.DaoModule;
import com.tlongdev.spicio.module.NetworkModule;
import com.tlongdev.spicio.module.NetworkRepositoryModule;
import com.tlongdev.spicio.module.SpicioAppModule;
import com.tlongdev.spicio.module.StorageModule;
import com.tlongdev.spicio.module.ThreadingModule;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;

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

    private PresenterComponent mPresenterComponent;

    private ActivityComponent mActivityComponent;

    private ProfileManagerComponent mProfileManagerComponent;

    private DrawerManagerComponent mDrawerManagerComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        FacebookSdk.sdkInitialize(this);

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics(), new Answers());
        }

        SpicioAppModule spicioAppModule = new SpicioAppModule(this);
        NetworkModule networkModule = new NetworkModule();
        StorageModule storageModule = new StorageModule();
        DaoModule daoModule = new DaoModule();
        NetworkRepositoryModule networkRepositoryModule = new NetworkRepositoryModule();
        ThreadingModule threadingModule = new ThreadingModule();
        AuthenticationModule authenticationModule = new AuthenticationModule();

        mNetworkComponent = DaggerNetworkComponent.builder()
                .spicioAppModule(spicioAppModule)
                .networkModule(networkModule)
                .build();

        mStorageComponent = DaggerStorageComponent.builder()
                .spicioAppModule(spicioAppModule)
                .storageModule(storageModule)
                .build();

        mInteractorComponent = DaggerInteractorComponent.builder()
                .spicioAppModule(spicioAppModule)
                .networkRepositoryModule(networkRepositoryModule)
                .daoModule(daoModule)
                .threadingModule(threadingModule)
                .build();

        mPresenterComponent = DaggerPresenterComponent.builder()
                .spicioAppModule(spicioAppModule)
                .authenticationModule(authenticationModule)
                .build();

        mActivityComponent = DaggerActivityComponent.builder()
                .spicioAppModule(spicioAppModule)
                .authenticationModule(authenticationModule)
                .build();

        mProfileManagerComponent = DaggerProfileManagerComponent.builder()
                .spicioAppModule(spicioAppModule)
                .build();

        mDrawerManagerComponent = DaggerDrawerManagerComponent.builder()
                .authenticationModule(authenticationModule)
                .spicioAppModule(spicioAppModule)
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

    public PresenterComponent getPresenterComponent() {
        return mPresenterComponent;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    public ProfileManagerComponent getProfileManagerComponent() {
        return mProfileManagerComponent;
    }

    public void setProfileManagerComponent(ProfileManagerComponent profileManagerComponent) {
        mProfileManagerComponent = profileManagerComponent;
    }

    public DrawerManagerComponent getDrawerManagerComponent() {
        return mDrawerManagerComponent;
    }
}
