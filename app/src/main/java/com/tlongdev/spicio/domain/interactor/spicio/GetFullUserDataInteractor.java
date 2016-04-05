package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.SeriesActivities;

import java.util.List;
import java.util.Map;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public interface GetFullUserDataInteractor extends Interactor {
    interface Callback {
        void onGetFullUserDataFinished(User user, List<Series> series, Map<Integer, SeriesActivities> activities);

        void onGetFullUserDataFail();
    }
}
