package com.tlongdev.spicio.presentation.ui.view.fragment;

import com.tlongdev.spicio.domain.model.Series;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public interface SeriesDetailsView extends BaseView {
    void showDetails(Series series);
}
