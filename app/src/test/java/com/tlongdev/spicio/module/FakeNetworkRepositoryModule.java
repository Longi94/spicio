package com.tlongdev.spicio.module;

import android.app.Application;

import com.tlongdev.spicio.domain.repository.SpicioRepository;
import com.tlongdev.spicio.domain.repository.TraktRepository;
import com.tlongdev.spicio.domain.repository.TvdbRepository;

/**
 * @author Long
 * @since 2016. 03. 06.
 */
public class FakeNetworkRepositoryModule extends NetworkRepositoryModule {

    private TvdbRepository mTvdbRepository;
    private TraktRepository mTraktRepository;
    private SpicioRepository mSpicioRepository;

    @Override
    TvdbRepository provideTvdbRepository(Application application) {
        return mTvdbRepository;
    }

    @Override
    TraktRepository provideTraktRepository(Application application) {
        return mTraktRepository;
    }

    @Override
    SpicioRepository provideSpicioRepository(Application application) {
        return mSpicioRepository;
    }

    public void setTvdbRepository(TvdbRepository mTvdbRepository) {
        this.mTvdbRepository = mTvdbRepository;
    }

    public void setTraktRepository(TraktRepository mTraktRepository) {
        this.mTraktRepository = mTraktRepository;
    }

    public void setSpicioRepository(SpicioRepository spicioRepository) {
        mSpicioRepository = spicioRepository;
    }
}
