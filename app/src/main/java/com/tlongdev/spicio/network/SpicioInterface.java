package com.tlongdev.spicio.network;

import com.tlongdev.spicio.network.model.spicio.request.SpicioEpisodeBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioSeriesBody;
import com.tlongdev.spicio.network.model.spicio.request.SpicioUserBody;
import com.tlongdev.spicio.network.model.spicio.response.SpicioActivityResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioSeriesResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserFullResponse;
import com.tlongdev.spicio.network.model.spicio.response.SpicioUserResponse;
import com.tlongdev.spicio.network.model.spicio.response.UserEpisodesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author longi
 * @since 2016.03.28.
 */
public interface SpicioInterface {

    String BASE_URL = "http://spicio-tlongdev.rhcloud.com/api/v1/";

    // User interfaces

    @GET("users")
    Call<List<SpicioUserResponse>> searchUsers(@Query("query") String query);

    @POST("users")
    Call<Void> login(@Body SpicioUserBody user);

    @GET("users/{id}")
    Call<SpicioUserFullResponse> getUser(@Path("id") long id, @Query("full") boolean full);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") long id);

    // Series interfaces

    @GET("users/{id}/series")
    Call<List<SpicioSeriesResponse>> getSeries(@Path("id") long id);

    @POST("users/{id}/series")
    Call<Void> addSeries(@Path("id") long id, @Body SpicioSeriesBody series);

    @DELETE("users/{id}/series/{seriesId}")
    Call<Void> removeSeries(@Path("id") long id, @Path("seriesId") int seriesId);

    @DELETE("users/{id}/series/{seriesId}/episodes")
    Call<UserEpisodesResponse> getEpisodes(@Path("id") long id, @Path("seriesId") int seriesId);

    @POST("users/{id}/series/{seriesId}/episodes/checks")
    Call<Void> checkEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Body SpicioEpisodeBody episode);

    @POST("users/{id}/series/{seriesId}/episodes/skips")
    Call<Void> skipEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Body SpicioEpisodeBody episode);

    @POST("users/{id}/series/{seriesId}/episodes/likes")
    Call<Void> likeEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Body SpicioEpisodeBody episode);

    @DELETE("users/{id}/series/{seriesId}/episodes/checks/{episodeId}")
    Call<Void> unCheckEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Path("episodeId") int episodeId);

    @DELETE("users/{id}/series/{seriesId}/episodes/skips/{episodeId}")
    Call<Void> unSkipEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Path("episodeId") int episodeId);

    @DELETE("users/{id}/series/{seriesId}/episodes/likes/{episodeId}")
    Call<Void> unLikeEpisode(@Path("id") long id, @Path("seriesId") int seriesId, @Path("episodeId") int episodeId);

    // Friend interfaces

    @GET("users/{id}/friends")
    Call<List<SpicioUserResponse>> getFriends(@Path("id") long userId);

    @POST("users/{id}/friends")
    Call<Void> addFriends(@Path("id") long userId, @Field("friend_id") long friendId);

    @DELETE("users/{userId}/friends/{friendId}")
    Call<Void> removeFriend(@Path("userId") long userId, @Path("friendId") long friendId);

    // User data interfaces

    @GET("users/{id}/discover")
    Call<List<SpicioSeriesResponse>> getRecommendations(@Path("id") long userId);

    @GET("users/{id}/discover")
    Call<List<SpicioSeriesResponse>> ignoreRecommendation(@Path("id") long userId, @Field("series_id") int seriesId);

    @GET("users/{id}/feed")
    Call<List<SpicioActivityResponse>> getFeed(@Path("id") long userId);

    @GET("users/{id}/history")
    Call<List<SpicioActivityResponse>> getHistory(@Path("id") long userId);
}
