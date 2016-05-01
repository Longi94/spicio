package com.tlongdev.spicio.presentation.ui.view.activity;

import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author lngtr
 * @since 2016. 05. 01.
 */
public interface UserFriendsView extends BaseView {
    void showFriends(List<User> friends);

    void showError();
}