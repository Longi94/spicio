package com.tlongdev.spicio.presentation.ui.view.fragment;

import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.22.
 */
public interface FeedView extends BaseView {
    void showLoadingAnimation();

    void hideLoadingAnimation();

    void showFeed(List<UserActivity> activities);
}
