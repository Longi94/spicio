package com.tlongdev.spicio.presentation.ui.view.fragment;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 06.
 */
public interface SeriesView extends BaseView {
    void showSeries(List<Series> series);
}
