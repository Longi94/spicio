package com.tlongdev.spicio.presentation.ui.view.fragment;

import com.tlongdev.spicio.domain.model.Season;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public interface SeasonsView extends BaseView {
    void showSeasons(List<Season> seasons);
}
