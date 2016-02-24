package com.tlongdev.spicio.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Long on 2016. 02. 24..
 */
@Root(name = "Data", strict = false)
public class SeriesData {

    @ElementList(name = "Series", inline = true)
    private List<Series> series;

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
