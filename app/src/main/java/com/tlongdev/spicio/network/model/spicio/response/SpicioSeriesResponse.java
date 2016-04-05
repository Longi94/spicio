package com.tlongdev.spicio.network.model.spicio.response;

import com.google.gson.annotations.SerializedName;
import com.tlongdev.spicio.domain.model.Day;
import com.tlongdev.spicio.domain.model.Status;

import java.util.List;

/**
 * @author Long
 * @since 2016. 04. 01.
 */
public class SpicioSeriesResponse {
    private String title;

    private Integer year;

    @SerializedName("trakt_id")
    private Integer traktId;

    @SerializedName("slug")
    private String slugName;

    @SerializedName("tvdb_id")
    private Integer tvdbId;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("tmdb_id")
    private Integer tmdbId;

    @SerializedName("tv_rage_id")
    private Integer tvRageId;

    private String overview;

    @SerializedName("first_aired")
    private Long firstAired;

    @SerializedName("day_of_airing")
    private Integer dayOfAiring;

    @SerializedName("time_of_airing")
    private String timeOfAiring;

    @SerializedName("air_time_zone")
    private String airTimeZone;

    private Integer runtime;

    private String certification;

    private String network;

    private String trailer;

    private Integer status;

    @SerializedName("trakt_rating")
    private Double traktRating;

    @SerializedName("trakt_rating_count")
    private Integer traktRatingCount;

    private List<String> genres;

    @SerializedName("poster_full")
    private String posterFull;

    @SerializedName("poster_thumb")
    private String posterThumb;

    private String thumb;

    private UserEpisodesResponse userEpisodes;

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getTraktId() {
        return traktId;
    }

    public String getSlugName() {
        return slugName;
    }

    public Integer getTvdbId() {
        return tvdbId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public Integer getTmdbId() {
        return tmdbId;
    }

    public Integer getTvRageId() {
        return tvRageId;
    }

    public String getOverview() {
        return overview;
    }

    public Long getFirstAired() {
        return firstAired;
    }

    @Day.Enum
    public Integer getDayOfAiring() {
        return dayOfAiring;
    }

    public String getTimeOfAiring() {
        return timeOfAiring;
    }

    public String getAirTimeZone() {
        return airTimeZone;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getCertification() {
        return certification;
    }

    public String getNetwork() {
        return network;
    }

    public String getTrailer() {
        return trailer;
    }

    @Status.Enum
    public Integer getStatus() {
        return status;
    }

    public Double getTraktRating() {
        return traktRating;
    }

    public Integer getTraktRatingCount() {
        return traktRatingCount;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getPosterFull() {
        return posterFull;
    }

    public String getPosterThumb() {
        return posterThumb;
    }

    public String getThumb() {
        return thumb;
    }

    public UserEpisodesResponse getUserEpisodes() {
        return userEpisodes;
    }
}
