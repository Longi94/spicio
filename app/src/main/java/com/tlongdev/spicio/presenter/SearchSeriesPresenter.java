package com.tlongdev.spicio.presenter;

import com.tlongdev.spicio.ui.fragment.SearchSeriesView;

/**
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesPresenter implements Presenter<SearchSeriesView> {

    private SearchSeriesView view;

    @Override
    public void attachView(SearchSeriesView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
