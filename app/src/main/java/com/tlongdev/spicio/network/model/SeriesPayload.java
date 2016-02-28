package com.tlongdev.spicio.network.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * A wrapper class required for XML parsing.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
@Root(name = "Data", strict = false)
public class SeriesPayload {

    @ElementList(name = "Series", inline = true)
    private List<SeriesApi> series;

    public List<SeriesApi> getSeries() {
        return series;
    }
}
