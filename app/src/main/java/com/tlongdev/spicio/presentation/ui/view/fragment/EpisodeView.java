package com.tlongdev.spicio.presentation.ui.view.fragment;

import com.tlongdev.spicio.domain.model.Episode;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

/**
 * @author Long
 * @since 2016. 03. 11.
 */
public interface EpisodeView extends BaseView {
    void showEpisodeDetails(Episode episode);

    void showError();

    void updateCheckButton(boolean watched);

    void updateSkipButton(boolean skipped);

    void updateLikeButton(boolean liked);
}
