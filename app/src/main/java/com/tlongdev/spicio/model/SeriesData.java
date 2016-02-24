package com.tlongdev.spicio.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Long on 2016. 02. 24..
 */
@Root(name = "Data", strict = false)
public class SeriesData {

    @Element(name = "Series")
    private Series series;

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
