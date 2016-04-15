package com.tlongdev.spicio.domain.interactor.spicio;

import com.tlongdev.spicio.domain.interactor.Interactor;
import com.tlongdev.spicio.domain.model.UserActivity;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.15.
 */
public interface GetHistoryInteractor extends Interactor {
    interface Callback {
        void onGetHistoryFinish(List<UserActivity> history);

        void onGetHistoryFail();
    }
}