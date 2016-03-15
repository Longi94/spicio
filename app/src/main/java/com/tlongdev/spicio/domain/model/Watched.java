package com.tlongdev.spicio.domain.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Long
 * @since 2016. 03. 08.
 */
public abstract class Watched {

    @IntDef({NONE, WATCHED, SKIPPED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Enum {}

    public static final int NONE = 0;
    public static final int WATCHED = 1;
    public static final int SKIPPED = 2;
}
