package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.domain.repository.TvdbRepository;

/**
 * @author Long
 * @since 2016. 03. 06.
 */
public class FakeNetworkRepositoryModule extends NetworkRepositoryModule {

    private TvdbRepository mTvdbRepository;
    private TraktRepository mTraktRepository;

    @Override
    public TvdbRepository provideTvdbRepository(Application application) {
        return mTvdbRepository;
    }

    @Override
    public TraktRepository provideTraktRepository(Application application) {
        return mTraktRepository;
    }

    public void setTvdbRepository(TvdbRepository mTvdbRepository) {
        this.mTvdbRepository = mTvdbRepository;
    }

    public void setTraktRepository(TraktRepository mTraktRepository) {
        this.mTraktRepository = mTraktRepository;
    }
}
