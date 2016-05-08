package com.tlongdev.spicio.presentation.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.User;
import com.tlongdev.spicio.domain.model.UserActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tlongdev.spicio.domain.model.ActivityType.ADDED_SERIES;
import static com.tlongdev.spicio.domain.model.ActivityType.BECAME_FRIENDS;
import static com.tlongdev.spicio.domain.model.ActivityType.LIKED;
import static com.tlongdev.spicio.domain.model.ActivityType.SKIPPED;
import static com.tlongdev.spicio.domain.model.ActivityType.WATCHED;

/**
 * @author longi
 * @since 2016.04.17.
 */
public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;

    private List<UserActivity> mDataSet;
    private User mUser;

    private Context mContext;

    private OnItemClickListener mListener;
    private String mButtonText = "";

    public UserHistoryAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                layout = R.layout.list_user_header;
                break;
            default:
                layout = R.layout.list_history;
                break;
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @SuppressWarnings("ConstantConditions")
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_HEADER:
                holder.seriesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onSeriesClick();
                        }
                    }
                });

                holder.friends.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onFriendsClick();
                        }
                    }
                });

                holder.addRemoveFriendText.setText(mButtonText);
                holder.addRemoveFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onAddRemoveFriendClick();
                        }
                    }
                });
                break;
            case VIEW_TYPE_ACTIVITY:
                if (mDataSet != null) {
                    UserActivity activity = mDataSet.get(position - 1);

                    switch (activity.getType()) {
                        case ADDED_SERIES:
                            holder.primary.setText(String.format("%s started watching %s",
                                    mUser.getName(),
                                    activity.getSeries().getTitle()));

                            Glide.with(mContext)
                                    .load(activity.getSeries().getImages().getPoster().getThumb())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.image);
                            break;
                        case BECAME_FRIENDS:
                            holder.primary.setText(String.format("%s added %s",
                                    mUser.getName(),
                                    activity.getVictim().getName()));

                            Glide.with(mContext)
                                    .load(mUser.getAvatarUrl())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.image);
                            break;
                        case LIKED:
                            holder.primary.setText(String.format("%s likes %s S%dE%d (%s)",
                                    mUser.getName(),
                                    activity.getSeries().getTitle(),
                                    activity.getEpisode().getSeason(),
                                    activity.getEpisode().getNumber(),
                                    activity.getEpisode().getTitle()));

                            Glide.with(mContext)
                                    .load(activity.getSeries().getImages().getPoster().getThumb())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.image);
                            break;
                        case SKIPPED:
                            holder.primary.setText(String.format("%s skipped %s S%dE%d (%s)",
                                    mUser.getName(),
                                    activity.getSeries().getTitle(),
                                    activity.getEpisode().getSeason(),
                                    activity.getEpisode().getNumber(),
                                    activity.getEpisode().getTitle()));

                            Glide.with(mContext)
                                    .load(activity.getSeries().getImages().getPoster().getThumb())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.image);
                            break;
                        case WATCHED:
                            holder.primary.setText(String.format("%s watched %s S%dE%d (%s)",
                                    mUser.getName(),
                                    activity.getSeries().getTitle(),
                                    activity.getEpisode().getSeason(),
                                    activity.getEpisode().getNumber(),
                                    activity.getEpisode().getTitle()));

                            Glide.with(mContext)
                                    .load(activity.getSeries().getImages().getPoster().getThumb())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(holder.image);
                            break;
                    }

                    holder.secondary.setText(DateUtils.getRelativeTimeSpanString(activity.getTimestamp()));
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ACTIVITY;
    }

    @Override
    public int getItemCount() {
        return (mDataSet == null ? 0 : mDataSet.size()) + 1;
    }

    public void setDataSet(List<UserActivity> dataSet, User user) {
        mDataSet = dataSet;
        mUser = user;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setButtonText(String buttonText) {
        mButtonText = buttonText;
    }

    public interface OnItemClickListener {
        void onSeriesClick();

        void onFriendsClick();

        void onAddRemoveFriendClick();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        @BindView(R.id.primary_text)
        TextView primary;
        @Nullable
        @BindView(R.id.secondary_text)
        TextView secondary;
        @Nullable
        @BindView(R.id.image)
        ImageView image;

        @Nullable
        @BindView(R.id.series_button)
        LinearLayout seriesButton;
        @Nullable
        @BindView(R.id.friends)
        LinearLayout friends;
        @Nullable
        @BindView(R.id.add_remove_friend)
        LinearLayout addRemoveFriend;
        @Nullable
        @BindView(R.id.add_remove_friend_text)
        TextView addRemoveFriendText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
