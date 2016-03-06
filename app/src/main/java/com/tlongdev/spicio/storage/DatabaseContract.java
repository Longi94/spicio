package com.tlongdev.spicio.storage;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Outer Layer, Storage.
 *
 * @author Long
 * @since 2016. 02. 26.
 */
public class DatabaseContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.tlongdev.spicio";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_SERIES = "series";
    public static final String PATH_EPISODES = "episodes";
    public static final String PATH_FEED = "feed";
    public static final String PATH_FRIENDS = "friends";
    public static final String PATH_IMAGES = "IMAGES";

    public static final class SeriesEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SERIES).build();

        public static final String TABLE_NAME = "series";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_TRAKT_ID = "trakt_id";
        public static final String COLUMN_TVDB_ID = "tvdb_id";
        public static final String COLUMN_IMDB_ID = "imdb_id";
        public static final String COLUMN_TMDB_ID = "tmdb_id";
        public static final String COLUMN_TV_RAGE_ID = "tv_rage_id";
        public static final String COLUMN_SLUG = "slug";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_FIRST_AIRED = "first_aired";
        public static final String COLUMN_DAY_OF_AIR = "day_of_air";
        public static final String COLUMN_TIME_OF_AIR = "time_of_air";
        public static final String COLUMN_AIR_TIME_ZONE = "air_time_zone";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_CONTENT_RATING = "content_rating";
        public static final String COLUMN_NETWORK = "network";
        public static final String COLUMN_TRAILER = "trailer";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TRAKT_RATING = "trakt_rating";
        public static final String COLUMN_TRAKT_RATING_COUNT = "trakt_rating_count";
        public static final String COLUMN_GENRES = "genres";

        public static final String COLUMN_TVDB_RATING = "tvdb_rating";
        public static final String COLUMN_TVDB_RATING_COUNT = "tvdb_rating_count";

        public static Uri buildUri(long id) {
            return CONTENT_URI.buildUpon().appendPath("id").appendPath("" + id).build();
        }
    }

    public static final class EpisodesEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_EPISODES).build();

        public static final String TABLE_NAME = "episodes";

        public static final String COLUMN_SEASON = "season";
        public static final String COLUMN_EPISODE_NUMBER = "episode_number";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TRAKT_ID = "trakt_id";
        public static final String COLUMN_TVDB_ID = "tvdb_id";
        public static final String COLUMN_IMDB_ID = "imdb_id";
        public static final String COLUMN_TMDB_ID = "tmdb_id";
        public static final String COLUMN_TV_RAGE_ID = "tv_rage_id";
        public static final String COLUMN_SLUG = "slug";
        public static final String COLUMN_ABSOLUTE_NUMBER = "absolute_number";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_TRAKT_RATING = "trakt_rating";
        public static final String COLUMN_TRAKT_RATING_COUNT = "trakt_rating_count";

        public static final String COLUMN_SERIES_TRAKT_ID = "series_trakt_id";
        public static final String COLUMN_TVDB_RATING = "tvdb_rating";

        public static Uri buildUri(long id) {
            return CONTENT_URI.buildUpon().appendPath("id").appendPath("" + id).build();
        }
    }

    public static final class ImagesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_IMAGES).build();

        public static final String TABLE_NAME = "images";

        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_URL = "url";

        public static Uri buildUri(long id) {
            return CONTENT_URI.buildUpon().appendPath("id").appendPath("" + id).build();
        }
    }

    public static final class FeedEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FEED).build();

        public static final String TABLE_NAME = "feed";

        public static final String COLUMN_ITEM_ID = "item_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_CULPRIT_ID = "culprit_id";
        public static final String COLUMN_VICTIM_ID = "victim_id";
        public static final String COLUMN_VICTIM_NAME = "victim_name";
        public static final String COLUMN_SERIES_ID = "series_id";
        public static final String COLUMN_SERIES_NAME = "series_name";
        public static final String COLUMN_EPISODE_ID = "episode_id";
        public static final String COLUMN_EPISODE_NAME = "episode_name";
        public static final String COLUMN_EPISODE_NUMBER = "episode_number";
        public static final String COLUMN_EPISODE_ABSOLUTE_NUMBER = "episode_absolute_number";
        public static final String COLUMN_SEASON_NUMBER = "season_number";

        public static Uri buildUri(long id) {
            return CONTENT_URI.buildUpon().appendPath("id").appendPath("" + id).build();
        }
    }

    public static final class FriendsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FRIENDS).build();

        public static final String TABLE_NAME = "friends";

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AVATAR = "avatar";

        public static Uri buildUri(long id) {
            return CONTENT_URI.buildUpon().appendPath("id").appendPath("" + id).build();
        }
    }
}
