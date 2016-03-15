package com.tlongdev.spicio.util;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public interface Logger {
    void verbose(String tag, String msg);
    void debug(String tag, String msg);
    void info(String tag, String msg);
    void warn(String tag, String msg);
    void error(String tag, String msg);
    void wtf(String tag, String msg);
    
    void verbose(String tag, String msg, Throwable tr);
    void debug(String tag, String msg, Throwable tr);
    void info(String tag, String msg, Throwable tr);
    void warn(String tag, String msg, Throwable tr);
    void error(String tag, String msg, Throwable tr);
    void wtf(String tag, String msg, Throwable tr);
}
