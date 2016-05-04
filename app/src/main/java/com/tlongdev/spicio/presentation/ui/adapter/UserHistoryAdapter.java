package com.tlongdev.spicio.presentation.ui.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.UserActivity;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author longi
 * @since 2016.04.17.
 */
public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.ViewHolder> {

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;

    private List<UserActivity> mDataSet;

    private OnItemClickListener mListener;
    private String mButtonText = "";

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

                holder.addRemoveFriend.setText(mButtonText);
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

                    holder.type.setText(String.format("Type: %d", activity.getType()));

                    if (activity.getVictim() != null) {
                        holder.victim.setText(String.format("Victim: %s", activity.getVictim().getName()));
                    }

                    if (activity.getSeries() != null) {
                        holder.series.setText(String.format("Series: %s", activity.getSeries().getTitle()));
                    }

                    if (activity.getEpisode() != null) {
                        holder.episode.setText(String.format("Episode: S%dE%d",
                                activity.getEpisode().getSeason(),
                                activity.getEpisode().getNumber()));
                    }

                    DateTime time = new DateTime(activity.getTimestamp());
                    holder.timestamp.setText(String.format("Timestamp: %s", time.toString()));
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

    public void setDataSet(List<UserActivity> dataSet) {
        mDataSet = dataSet;
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

        @Nullable @BindView(R.id.type) TextView type;
        @Nullable @BindView(R.id.victim) TextView victim;
        @Nullable @BindView(R.id.series) TextView series;
        @Nullable @BindView(R.id.episode) TextView episode;
        @Nullable @BindView(R.id.timestamp) TextView timestamp;

        @Nullable @BindView(R.id.series_button) Button seriesButton;
        @Nullable @BindView(R.id.friends) Button friends;
        @Nullable @BindView(R.id.add_remove_friend) Button addRemoveFriend;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
