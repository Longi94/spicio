package com.tlongdev.spicio.presentation.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tlongdev.spicio.R;
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
 * @since 2016.04.22.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<UserActivity> mDataSet;

    private OnItemClickListener mOnItemClickListener;

    private Context mContext;

    public FeedAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_feed, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataSet != null) {
            final UserActivity activity = mDataSet.get(position);

            switch (activity.getType()) {
                case ADDED_SERIES:
                    holder.primary.setText(String.format("%s started watching %s",
                            activity.getCulprit().getName(),
                            activity.getSeries().getTitle()));

                    Glide.with(mContext)
                            .load(activity.getSeries().getImages().getPoster().getThumb())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.image);
                    break;
                case BECAME_FRIENDS:
                    holder.primary.setText(String.format("%s became friends with %s",
                            activity.getCulprit().getName(),
                            activity.getVictim().getName()));

                    Glide.with(mContext)
                            .load(activity.getCulprit().getAvatarUrl())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(holder.image);
                    break;
                case LIKED:
                    holder.primary.setText(String.format("%s likes %s S%dE%d (%s)",
                            activity.getCulprit().getName(),
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
                            activity.getCulprit().getName(),
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
                            activity.getCulprit().getName(),
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

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(activity);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public void setDataSet(List<UserActivity> dataSet) {
        mDataSet = dataSet;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.primary_text) TextView primary;
        @BindView(R.id.secondary_text) TextView secondary;
        @BindView(R.id.image) ImageView image;

        View root;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            root = view;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UserActivity activity);
    }
}