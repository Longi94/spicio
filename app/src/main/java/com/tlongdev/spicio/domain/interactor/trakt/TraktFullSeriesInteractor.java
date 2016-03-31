package com.tlongdev.spicio.domain.interactor.trakt;

import com.tlongdev.spicio.domain.interactor.Interactor;
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
        void onTraktFullSeriesFinish(Series series, List<Season> seasons, List<Episode> episodes);

        void onTraktFullSeriesFail();
    }
}