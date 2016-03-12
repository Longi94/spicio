package com.tlongdev.spicio.presentation.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.presentation.ui.fragment.SeasonsFragment;
import com.tlongdev.spicio.presentation.ui.fragment.SeriesDetailsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SeriesActivity extends AppCompatActivity {

    public static final String EXTRA_SERIES_ID = "series_id";
    public static final String EXTRA_SERIES_TITLE = "series_title";

    @Bind(R.id.container) ViewPager mViewPager;
    @Bind(R.id.tabs) TabLayout mTabLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        ButterKnife.bind(this);

        //Set the color of the status bar
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getIntent().getStringExtra(EXTRA_SERIES_TITLE));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(sectionsPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SeriesDetailsFragment.newInstance(getIntent().getIntExtra(EXTRA_SERIES_ID, -1));
                case 1:
                    return SeasonsFragment.newInstance(getIntent().getIntExtra(EXTRA_SERIES_ID, -1));
                default:
                    throw new IllegalArgumentException("Invalid fragment position: " + position);
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DETAILS";
                case 1:
                    return "SEASONS";
            }
            return null;
        }
    }
}
