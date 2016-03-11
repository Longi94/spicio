package com.tlongdev.spicio.presentation.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.ui.fragment.EpisodeFragment;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public class EpisodePagerAdapter extends FragmentPagerAdapter {

    private List<Episode> mDataSet;

    private int mSeriesId;
    private int mSeason;

    public EpisodePagerAdapter(FragmentManager fm, int seriesId, int season) {
        super(fm);
        mSeriesId = seriesId;
        mSeason = season;
    }

    @Override
    public Fragment getItem(int position) {
        int episodeNumber = mDataSet.get(position).getNumber();
        return EpisodeFragment.newInstance(mSeriesId, mSeason, episodeNumber);
    }

    @Override
    public int getCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Episode " + (position + 1);
    }

    public void setDataSet(List<Episode> dataSet) {
        mDataSet = dataSet;
    }
}
