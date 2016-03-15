package com.tlongdev.spicio.threading;

/**
 * @author Long
 * @since 2016. 02. 28.
 */
public class TestMainThread implements MainThread {

    @Override
    public void post(Runnable runnable) {
        // tests can run on this thread, no need to invoke other threads
        runnable.run();
    }
}
