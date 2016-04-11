package com.tlongdev.spicio.domain.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author longi
 * @since 2016.04.02.
 */
public abstract class ActivityType {

    @IntDef({WATCHED, SKIPPED, LIKED, BECAME_FRIENDS, ADDED_SERIES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Enum {}

    public static final int WATCHED = 0;
    public static final int SKIPPED = 1;
    public static final int LIKED = 2;
    public static final int BECAME_FRIENDS = 3;
    public static final int ADDED_SERIES = 4;
}
