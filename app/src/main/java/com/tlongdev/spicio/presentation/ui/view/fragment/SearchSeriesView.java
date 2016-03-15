package com.tlongdev.spicio.presentation.ui.view.fragment;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * Outer Layer, UI.
 *
 * @author Long
 * @since 2016. 02. 24.
 */
public interface SearchSeriesView extends BaseView {
    void showSearchResult(List<Series> series);
    void showErrorMessage();
}
