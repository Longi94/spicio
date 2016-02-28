package com.tlongdev.spicio.network;

import com.tlongdev.spicio.network.model.EpisodePayload;
import com.tlongdev.spicio.network.model.SeriesPayload;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Outer layer, Network.
 * Retrofit interface for using the TVDb api.
 *
 * http://www.thetvdb.com/wiki/index.php/Programmers_API
 * @author Long
 * @since 2016. 02. 24.
 */
public interface TvdbInterface {

    /**
     * Searches for series using the api.
     * http://www.thetvdb.com/wiki/index.php?title=API:GetSeries
     *
     * @param seriesName the name to search for
     * @return the search result
     */
    @GET("api/GetSeries.php")
    Call<SeriesPayload> getSeries(@Query("seriesname") String seriesName);

    /**
     * Gets more information about a series.
     * http://www.thetvdb.com/wiki/index.php?title=API:Base_Series_Record
     *
     * @param apiKey   the TVDb API key
     * @param seriesId the ID of the series
     * @return more information about the series
     */
    @GET("api/{apiKey}/series/{id}")
    Call<SeriesPayload> getSeriesRecord(@Path("apiKey") String apiKey, @Path("id") int seriesId);

    /**
     * Gets information about an episode.
     * http://www.thetvdb.com/wiki/index.php?title=API:Base_Episode_Record
     *
     * @param apiKey   the TVDb API key
     * @param seriesId the ID of the series
     * @param season   the season nmber
     * @param episode  the episode number
     * @return the episode
     */
    @GET("api/{apiKey}/series/{id}/default/{season}/{episode}")
    Call<EpisodePayload> getEpisode(@Path("apiKey") String apiKey, @Path("id") int seriesId,
                                    @Path("season") int season, @Path("episode") int episode);

    /**
     * Gets information about an episode.
     * http://www.thetvdb.com/wiki/index.php?title=API:Base_Episode_Record
     *
     * @param apiKey          the TVDb API key
     * @param seriesId        the ID of the series
     * @param absoluteEpisode the absolute episode number
     * @return the episode
     */
    @GET("api/{apiKey}/series/{id}/absolute/{episode}")
    Call<EpisodePayload> getEpisode(@Path("apiKey") String apiKey, @Path("id") int seriesId,
                                    @Path("episode") int absoluteEpisode);
}
