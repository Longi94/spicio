package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.model.Season;

import java.util.List;

import okhttp3.Interceptor;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public interface LoadSeasonsInteractor extends Interceptor {
    interface Callback {
        void onFinish(List<Season> seasons);

        void onFail();
    }
}
