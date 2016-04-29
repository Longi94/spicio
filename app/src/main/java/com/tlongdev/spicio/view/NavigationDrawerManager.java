package com.tlongdev.spicio.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.util.CircleTransform;
import com.tlongdev.spicio.util.ProfileManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author longi
 * @since 2016.04.29.
 */
public class NavigationDrawerManager implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject ProfileManager mProfileManager;
    @Inject SharedPreferences mPrefs;
    @Inject Context mContext;

    @BindView(R.id.primary_text) TextView mPrimaryText;
    @BindView(R.id.secondary_text) TextView mSecondaryText;
    @BindView(R.id.avatar) ImageView mAvatar;

    public NavigationDrawerManager(SpicioApplication application) {
        application.getDrawerManagerComponent().inject(this);
        //mProfileManager.addOnProfileUpdateListener(this);
        mPrefs.registerOnSharedPreferenceChangeListener(this);
    }

    public void attachView(View header) {
        ButterKnife.bind(this, header);

        mContext = header.getContext();
        if (mProfileManager.isLoggedIn()) {
            update(mProfileManager.getUser());
        }
    }

    public void detachView() {
        mPrimaryText = null;
        mSecondaryText = null;
        mAvatar = null;
        mContext = null;
    }

    public void update(User user) {
        //Set the name
        mPrimaryText.setText(user.getName());

        //Download the avatar (if needed) and set it
        Glide.with(mContext)
                .load(user.getAvatarUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CircleTransform(mContext))
                .into(mAvatar);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!key.equals(ProfileManager.PREF_KEY_USER)) {
            return;
        }

        if (mProfileManager.isLoggedIn()) {
            update(mProfileManager.getUser());
        }
    }
}