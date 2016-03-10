package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.model.Episode;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public interface TraktEpisodeImagesInteractor extends Interactor {
    interface Callback {
        void onFinish(List<Episode> episodes);

        void onFail();
    }
}