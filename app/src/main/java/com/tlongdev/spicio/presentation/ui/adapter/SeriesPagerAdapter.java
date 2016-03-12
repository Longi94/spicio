package com.tlongdev.spicio.presentation.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tlongdev.spicio.presentation.ui.fragment.SeasonsFragment;
import com.tlongdev.spicio.presentation.ui.fragment.SeriesDetailsFragment;

/**
 * @author Long
 * @since 2016. 03. 12.
 */
public class SeriesPagerAdapter extends FragmentPagerAdapter {

    private int mSeriesId;

    public SeriesPagerAdapter(FragmentManager fm, int seriesId) {
        super(fm);
        mSeriesId = seriesId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SeriesDetailsFragment.newInstance(mSeriesId);
            case 1:
                return SeasonsFragment.newInstance(mSeriesId);
            default:
                throw new IllegalArgumentException("Invalid fragment position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
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
