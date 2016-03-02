package com.tlongdev.spicio.storage;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.tlongdev.spicio.BuildConfig;
import com.tlongdev.spicio.storage.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FeedEntry;
import com.tlongdev.spicio.storage.DatabaseContract.FriendsEntry;
import com.tlongdev.spicio.storage.DatabaseContract.SeriesEntry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Long
 * @since 2016. 02. 26.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DatabaseProviderTest {

    private ContentResolver mContentResolver;
    private ShadowContentResolver mShadowContentResolver;
    private DatabaseProvider mProvider;

    @Before
    public void setUp() throws Exception {
        mProvider = new DatabaseProvider();
        mContentResolver = RuntimeEnvironment.application.getContentResolver();
        ShadowContentResolver.registerProvider(DatabaseContract.CONTENT_AUTHORITY, mProvider);
        mProvider.onCreate();
        mShadowContentResolver = Shadows.shadowOf(mContentResolver);

        deleteAllRecords(SeriesEntry.CONTENT_URI);
        deleteAllRecords(EpisodesEntry.CONTENT_URI);
        deleteAllRecords(FeedEntry.CONTENT_URI);
        deleteAllRecords(FriendsEntry.CONTENT_URI);
    }

    public void deleteAllRecords(Uri contentUri) {
        mShadowContentResolver.delete(contentUri, null, null);

        Cursor cursor = mShadowContentResolver.query(
                contentUri, null, null, null, null
        );

        assertNotNull("Error: Query returned null during delete", cursor);
        assertEquals("Error: Records not deleted from Repos table during delete", 0, cursor.getCount());

        cursor.close();
    }

    @Test
    public void testBasicQuery() {
        assertTrue(true);
    }
}
