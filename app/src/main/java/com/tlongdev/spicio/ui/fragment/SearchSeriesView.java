package com.tlongdev.spicio.ui.fragment;

import com.tlongdev.spicio.model.Series;
import com.tlongdev.spicio.ui.BaseView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 02. 24.
 */
public interface SearchSeriesView extends BaseView {
    void showSearchResult(List<Series> series);
    void showErrorMessage();
}
