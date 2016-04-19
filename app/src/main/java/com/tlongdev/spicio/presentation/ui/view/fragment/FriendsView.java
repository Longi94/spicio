package com.tlongdev.spicio.presentation.ui.view.fragment;

import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author longi
 * @since 2016.04.19.
 */
public interface FriendsView extends BaseView {
    void showFriends(List<User> friends);
}
