package com.tlongdev.spicio.network;

import com.tlongdev.spicio.network.model.trakt.TraktEpisode;
import com.tlongdev.spicio.network.model.trakt.TraktSearchResult;
import com.tlongdev.spicio.network.model.trakt.TraktSeason;
import com.tlongdev.spicio.network.model.trakt.TraktSeries;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Outer Layer, Network.
 *
 * @author Long
 * @since 2016. 03. 01.
 */
public interface TraktApiInterface {

    String BASE_URL = "https://api-v2launch.trakt.tv/";

    /**
     * Search trakt.tv database by text. Narrow down search result with the type paramater.
     *
     * @param query the query string to search for
     * @param type  can be movie, show, episode, person, list
     * @return the search result
     */
    @GET("search")
    Call<List<TraktSearchResult>> searchByText(@Query("query") String query, @Query("type") String type);

    /**
     * Search trakt.tv database by id. Narrow down search result with the type parameter.
     *
     * @param idType type of the ID, can be trakt-movie, trakt-show, trakt-episode, imdb, tmdb, tvdb, tvrage
     * @param id     the id of the item
     * @return the search result
     */
    @GET("search")
    Call<List<TraktSearchResult>> searchById(@Query("id_type") String idType, @Query("id") String id);

    /**
     * Get the details of a series.
     *
     * @param id the id of the series, can be trakt.tv ID, trakt.tv slug or IMDB ID
     * @return the details of a series
     */
    @GET("shows/{id}?extended=full")
    Call<TraktSeries> getSeriesDetails(@Path("id") String id);

    /**
     * Get the images for a series.
     *
     * @param id the id of the series, can be trakt.tv ID, trakt.tv slug or IMDB ID
     * @return the images for a series
     */
    @GET("shows/{id}?extended=images")
    Call<TraktSeries> getSeriesImages(@Path("id") String id);

    /**
     * Get the details of a season of a series.
     *
     * @param id the id of the series, can be trakt.tv ID, trakt.tv slug or IMDB ID
     * @return the details of a season
     */
    @GET("shows/{id}/seasons?extended=full")
    Call<List<TraktSeason>> getSeasonsDetails(@Path("id") String id);

    /**
     * Get the images for a season of a series.
     *
     * @param id the id of the series, can be trakt.tv ID, trakt.tv slug or IMDB ID
     * @return the images for a season
     */
    @GET("shows/{id}/seasons?extended=images")
    Call<List<TraktSeason>> getSeasonsImages(@Path("id") String id);

    @GET("shows/{id}/seasons?extended=episodes")
    Call<List<TraktSeason>> getSeasonsEpisodes(@Path("id") String id);

    /**
     * Get the episodes of a season of a series
     *
     * @param id     the id of the series, can be trakt.tv ID, trakt.tv slug or IMDB ID
     * @param season the season number (0 for special episodes)
     * @return the episodes of a season
     */
    @GET("shows/{id}/seasons/{season}?extended=full")
    Call<List<TraktEpisode>> getSeasonEpisodes(@Path("id") String id, @Path("season") int season);

    /**
     * Get the episodes of a season of a series
     *
     * @param id     the id of the series, can be trakt.tv ID, trakt.tv slug or IMDB ID
     * @param season the season number (0 for special episodes)
     * @return the episodes of a season
     */
    @GET("shows/{id}/seasons/{season}?extended=images")
    Call<List<TraktEpisode>> getSeasonEpisodesImages(@Path("id") String id, @Path("season") int season);

    /**
     * Get a single episode of a season of a series.
     *
     * @param id      the id of the series, can be trakt.tv ID, trakt.tv slug or IMDB ID
     * @param season  the season number
     * @param episode the episode number
     * @return the single episode
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}?extended=full")
    Call<TraktEpisode> getSingleEpisodeDetails(@Path("id") String id, @Path("season") int season, @Path("episode") int episode);

    /**
     * Get a images for an episode of a season of a series.
     *
     * @param id      the id of the series, can be trakt.tv ID, trakt.tv slug or IMDB ID
     * @param season  the season number
     * @param episode the episode number
     * @return the images for the episode
     */
    @GET("shows/{id}/seasons/{season}/episodes/{episode}?extended=full")
    Call<TraktEpisode> getSingleEpisodeImages(@Path("id") String id, @Path("season") int season, @Path("episode") int episode);
}
