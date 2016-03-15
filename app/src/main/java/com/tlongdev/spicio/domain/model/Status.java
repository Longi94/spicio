package com.tlongdev.spicio.domain.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Inner Layer, Model.
 * Series status enumeration.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public abstract class Status {

    @IntDef({NULL, ENDED, CONTINUING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Enum {}

    public static final int NULL = 0;
    public static final int ENDED = 1;
    public static final int CONTINUING = 2;
}
