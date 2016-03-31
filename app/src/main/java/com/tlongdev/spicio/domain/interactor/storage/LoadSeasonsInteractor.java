package com.tlongdev.spicio.domain.interactor.storage;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.Season;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public interface LoadSeasonsInteractor extends Interactor {
    interface Callback {
        void onLoadSeasonsFinish(List<Season> seasons);

        void onLoadSeasonsFail();
    }
}
