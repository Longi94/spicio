package com.tlongdev.spicio.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Long
 * @since 2016. 03. 01.
 */
public class TraktAirTime {

    @SerializedName("day")
    private String day;

    @SerializedName("time")
    private String time;

    @SerializedName("timezone")
    private String timezone;

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getTimezone() {
        return timezone;
    }
}
