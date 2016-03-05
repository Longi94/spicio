package com.tlongdev.spicio.module;

import android.app.Application;
import android.content.ContentResolver;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public class FakeStorageModule extends StorageModule {

    @Override
    ContentResolver provideContentResolver(Application application) {
        return application.getContentResolver();
    }
}
