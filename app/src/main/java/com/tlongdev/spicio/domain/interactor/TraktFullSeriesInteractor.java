package com.tlongdev.spicio.domain.interactor;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 08.
 */
public interface TraktFullSeriesInteractor extends Interactor {
    interface Callback {
        void onFinish(Series series, List<Season> seasons, List<Episode> episodes);
        void onFail();
    }
}