package com.tlongdev.spicio.presentation.ui.view.activity;

import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.17.
 */
public interface UserView extends BaseView {
    void showUserData(User user, List<UserActivity> history);

    void showError();
}
