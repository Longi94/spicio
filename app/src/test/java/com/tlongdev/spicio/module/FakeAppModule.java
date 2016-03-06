package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.util.Logger;

import org.mockito.Mockito;

/**
 * @author Long
 * @since 2016. 03. 06.
 */
public class FakeAppModule extends SpicioAppModule {
    public FakeAppModule(Application application) {
        super(application);
    }

    @Override
    Logger provideLogger() {
        //stub
        return Mockito.mock(Logger.class);
    }
}
