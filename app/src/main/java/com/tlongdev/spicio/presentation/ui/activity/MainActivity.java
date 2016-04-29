package com.tlongdev.spicio.presentation.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.presentation.presenter.activity.MainPresenter;
import com.tlongdev.spicio.presentation.ui.fragment.FeedFragment;
import com.tlongdev.spicio.presentation.ui.fragment.FriendsFragment;
import com.tlongdev.spicio.presentation.ui.fragment.SearchSeriesFragment;
import com.tlongdev.spicio.presentation.ui.fragment.SeriesFragment;
import com.tlongdev.spicio.presentation.ui.view.activity.MainView;
import com.tlongdev.spicio.util.ProfileManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Outer Layer, UI.
 *
 * @author Long
 * @since 2016. 02. 23.
 */
public class MainActivity extends AppCompatActivity implements MainView,
        NavigationView.OnNavigationItemSelectedListener {

    @Inject ProfileManager mProfileManager;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView mNavigationView;

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    public static final String FRAGMENT_TAG_FEED = "feed";
    public static final String FRAGMENT_TAG_SEARCH_SERIES = "search_series";
    public static final String FRAGMENT_TAG_SERIES = "series";
    public static final String FRAGMENT_TAG_FRIENDS = "friends";

    public static final int NAV_FEED = 0;
    public static final int NAV_SERIES = 1;
    public static final int NAV_SEARCH_SERIES = 2;
    public static final int NAV_FRIENDS = 3;

    /**
     * The index of the current fragment.
     */
    private int mCurrentSelectedPosition = -1;

    private MainPresenter presenter;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((SpicioApplication)getApplication()).getActivityComponent().inject(this);

        presenter = new MainPresenter();
        presenter.attachView(this);

        mNavigationView.setNavigationItemSelectedListener(this);

        switchFragment(NAV_FEED);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!mProfileManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
        //Since each fragment has it's own toolbar we need to re add the drawer toggle everytime we
        //switch fragments
        restoreNavigationIcon(toolbar);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_feed:
                switchFragment(NAV_FEED);
                break;
            case R.id.nav_search_series:
                switchFragment(NAV_SEARCH_SERIES);
                break;
            case R.id.nav_series:
                switchFragment(NAV_SERIES);
                break;
            case R.id.nav_friends:
                switchFragment(NAV_FRIENDS);
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Switches to another fragment.
     *
     * @param position the position of the clicked item in the navigation view
     */
    public void switchFragment(int position) {
        if (mCurrentSelectedPosition == position) {
            return;
        }

        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mNavigationView);
        }

        //Start handling fragment transactions
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment newFragment;

        switch (position) {
            case NAV_FEED:
                newFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG_FEED);
                if (newFragment == null) {
                    newFragment = new FeedFragment();
                }
                transaction.replace(R.id.container, newFragment, FRAGMENT_TAG_FEED);
                break;
            case NAV_SEARCH_SERIES:
                newFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG_SEARCH_SERIES);
                if (newFragment == null) {
                    newFragment = new SearchSeriesFragment();
                }
                transaction.replace(R.id.container, newFragment, FRAGMENT_TAG_SEARCH_SERIES);
                break;
            case NAV_SERIES:
                newFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG_SERIES);
                if (newFragment == null) {
                    newFragment = new SeriesFragment();
                }
                transaction.replace(R.id.container, newFragment, FRAGMENT_TAG_SERIES);
                break;
            case NAV_FRIENDS:
                newFragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG_FRIENDS);
                if (newFragment == null) {
                    newFragment = new FriendsFragment();
                }
                transaction.replace(R.id.container, newFragment, FRAGMENT_TAG_FRIENDS);
                break;
        }

        //Commit the transaction
        transaction.commit();
    }

    /**
     * Restores the navigation icon of the toolbar.
     */
    private void restoreNavigationIcon(Toolbar toolbar) {
        // set up the drawer's list view with items and click listener
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                             /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                toolbar,
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Notify the listeners
                /*if (drawerListener != null) {
                    drawerListener.onDrawerOpened();
                }*/
            }
        };

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
}
