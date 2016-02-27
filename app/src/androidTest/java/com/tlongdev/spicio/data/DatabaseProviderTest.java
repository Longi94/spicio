package com.tlongdev.spicio.data;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.tlongdev.spicio.data.DatabaseContract.EpisodesEntry;
import com.tlongdev.spicio.data.DatabaseContract.FeedEntry;
import com.tlongdev.spicio.data.DatabaseContract.FriendsEntry;
import com.tlongdev.spicio.data.DatabaseContract.SeriesEntry;

/**
 * @author Long
 * @since 2016. 02. 26.
 */
public class DatabaseProviderTest extends AndroidTestCase {

    // Since we want each test to start with a clean slate, run deleteAllRecords
    // in setUp (called by the test runner before each test).
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        deleteAllRecords(SeriesEntry.CONTENT_URI);
        deleteAllRecords(EpisodesEntry.CONTENT_URI);
        deleteAllRecords(FeedEntry.CONTENT_URI);
        deleteAllRecords(FriendsEntry.CONTENT_URI);
    }

    public void deleteAllRecords(Uri contentUri) {
        mContext.getContentResolver().delete(contentUri, null, null);

        Cursor cursor = mContext.getContentResolver().query(
                contentUri, null, null, null, null
        );

        assertNotNull("Error: Query returned null during delete", cursor);
        assertEquals("Error: Records not deleted from Repos table during delete", 0, cursor.getCount());

        cursor.close();
    }

    /**
     * This test checks to make sure that the content provider is registered correctly.
     */
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // DatabaseProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                DatabaseProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: DatabaseProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + DatabaseContract.CONTENT_AUTHORITY,
                    providerInfo.authority, DatabaseContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: DatabaseProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }
}
