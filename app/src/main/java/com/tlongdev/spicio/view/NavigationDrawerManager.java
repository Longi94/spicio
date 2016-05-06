package com.tlongdev.spicio.view;

import android.content.Context;
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

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author longi
 * @since 2016.04.29.
 */
public class NavigationDrawerManager implements ProfileManager.OnUserUpdateListener {

    @Inject ProfileManager mProfileManager;

    @BindView(R.id.primary_text) TextView mPrimaryText;
    @BindView(R.id.secondary_text) TextView mSecondaryText;
    @BindView(R.id.avatar) ImageView mAvatar;

    private Context mContext;

    public NavigationDrawerManager(SpicioApplication application) {
        application.getDrawerManagerComponent().inject(this);
        mProfileManager.addOnUserUpdateListener(this);
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
        if (mPrimaryText != null) {
            mPrimaryText.setText(user.getName());
        }

        if (mSecondaryText != null) {
            mSecondaryText.setText(String.format(Locale.ENGLISH,
                    "%d series, %d episodes", user.getSeriesCount(), user.getEpisodeCount()));
        }

        //Download the avatar (if needed) and set it
        if (mAvatar != null) {
            Glide.with(mContext)
                    .load(user.getAvatarUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new CircleTransform(mContext))
                    .into(mAvatar);
        }
    }

    @Override
    public void OnUserUpdate(User user) {
        if (mContext != null) {
            update(user);
        }
    }
}