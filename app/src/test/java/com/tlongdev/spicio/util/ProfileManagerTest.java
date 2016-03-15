package com.tlongdev.spicio.util;

import com.tlongdev.spicio.BuildConfig;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.component.DaggerProfileManagerComponent;
import com.tlongdev.spicio.component.ProfileManagerComponent;
import com.tlongdev.spicio.module.FakeAppModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Long
 * @since 2016. 03. 13.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class ProfileManagerTest {

    private ProfileManager mProfileManager;

    @Before
    public void setUp() {
        SpicioApplication application = (SpicioApplication) RuntimeEnvironment.application;

        ProfileManagerComponent component = DaggerProfileManagerComponent.builder()
                .spicioAppModule(new FakeAppModule(application))
                .build();

        application.setProfileManagerComponent(component);

        mProfileManager = new ProfileManager(application);
    }

    @Test
    public void testFacebookLogin() throws Exception {
        mProfileManager.loginWithFacebook("facebook_id");

        assertEquals("facebook_id", mProfileManager.getFacebookId());
        assertTrue(mProfileManager.isLoggedIn());
    }

    @Test
    public void testGoogleLogin() throws Exception {
        mProfileManager.loginWithGoogle("google_id");

        assertEquals("google_id", mProfileManager.getGoogleId());
        assertTrue(mProfileManager.isLoggedIn());
    }

    @Test
    public void testLogout() throws Exception {
        mProfileManager.loginWithFacebook("facebook_id");

        assertTrue(mProfileManager.isLoggedIn());

        mProfileManager.logout();

        assertFalse(mProfileManager.isLoggedIn());
        assertNull(mProfileManager.getFacebookId());
    }
}
