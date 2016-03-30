package com.tlongdev.spicio.presentation.ui.view.activity;

import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.presentation.ui.view.BaseView;

import java.util.List;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public interface SearchFriendsView extends BaseView {
    void showResult(List<User> users);
}
