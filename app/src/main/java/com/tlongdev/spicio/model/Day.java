package com.tlongdev.spicio.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Inner Layer, Model.
 * Days of the week.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public abstract class Day {

    @IntDef({MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Enum {}

    public static final int MONDAY = 0;
    public static final int TUESDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int THURSDAY = 3;
    public static final int FRIDAY = 4;
    public static final int SATURDAY = 5;
    public static final int SUNDAY = 6;
}
