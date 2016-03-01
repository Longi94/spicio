package com.tlongdev.spicio.presentation.ui.fragment;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.BaseView;

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