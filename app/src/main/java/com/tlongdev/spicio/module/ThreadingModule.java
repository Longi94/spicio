package com.tlongdev.spicio.module;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.domain.executor.ThreadExecutor;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.threading.MainThreadImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
@Module
public class ThreadingModule {

    @Provides
    @Singleton
    Executor provideExecutor() {
        return ThreadExecutor.getInstance();
    }

    @Provides
    @Singleton
    MainThread provideMainThread() {
        return MainThreadImpl.getInstance();
    }
}
