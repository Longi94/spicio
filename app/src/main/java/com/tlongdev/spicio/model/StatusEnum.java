package com.tlongdev.spicio.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Long on 2016. 02. 23..
 */
public abstract class StatusEnum {

    @IntDef({NULL, ENDED, CONTINUING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status{}

    public static final int NULL = 0;
    public static final int ENDED = 1;
    public static final int CONTINUING = 2;
}
