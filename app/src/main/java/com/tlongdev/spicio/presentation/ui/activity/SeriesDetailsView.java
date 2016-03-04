package com.tlongdev.spicio.presentation.ui.activity;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.BaseView;

/**
 * @author Long
 * @since 2016. 03. 04.
 */
public interface SeriesDetailsView extends BaseView {
    void showDetails(Series series);
}
