package com.tlongdev.spicio.module;

import android.app.Application;
import android.content.ContentResolver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tlongdev.spicio.storage.DatabaseHelper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
@Module
public class StorageModule {

    @Provides
    @Singleton
    ContentResolver provideContentResolver(Application application) {
        return application.getContentResolver();
    }

    @Provides
    @Singleton
    SQLiteOpenHelper provideSQLiteOpenHelper(Application application) {
        return new DatabaseHelper(application);
    }

    @Provides
    @Singleton
    @Named("readable")
    SQLiteDatabase provideReadableDatabase(SQLiteOpenHelper helper) {
        return helper.getReadableDatabase();
    }

    @Provides
    @Singleton
    @Named("writable")
    SQLiteDatabase provideWritableDatabase(SQLiteOpenHelper helper) {
        return helper.getWritableDatabase();
    }
}
