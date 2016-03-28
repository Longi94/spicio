package com.tlongdev.spicio.network.model.tvdb;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Outer layer, Network.
 * A wrapper class required for XML parsing.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
@Root(name = "Data", strict = false)
public class TvdbSeriesPayload {

    @ElementList(name = "Series", inline = true)
    private List<TvdbSeries> series;

    public List<TvdbSeries> getSeries() {
        return series;
    }
}
