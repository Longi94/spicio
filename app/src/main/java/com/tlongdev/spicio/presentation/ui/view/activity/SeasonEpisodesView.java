package com.tlongdev.spicio.presentation.ui.view.activity;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public interface SeasonEpisodesView extends BaseView {
    void showEpisodes(List<Episode> episodes);
}
