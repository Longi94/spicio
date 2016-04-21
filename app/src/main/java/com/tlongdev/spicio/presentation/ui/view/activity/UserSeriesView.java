package com.tlongdev.spicio.presentation.ui.view.activity;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.21.
 */
public interface UserSeriesView extends BaseView {
    void showSeries(List<Series> series);

    void showError();
}
