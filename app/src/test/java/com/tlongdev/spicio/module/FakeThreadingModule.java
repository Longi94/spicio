package com.tlongdev.spicio.module;

import com.tlongdev.spicio.domain.executor.Executor;
import com.tlongdev.spicio.threading.MainThread;
import com.tlongdev.spicio.threading.TestMainThread;

import org.mockito.Mockito;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
public class FakeThreadingModule extends ThreadingModule {

    @Override
    Executor provideExecutor() {
        return Mockito.mock(Executor.class);
    }

    @Override
    MainThread provideMainThread() {
        return new TestMainThread();
    }
}
