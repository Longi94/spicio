package com.tlongdev.spicio.presentation.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.UserActivity;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author longi
 * @since 2016.04.17.
 */
public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.ViewHolder> {

    private List<UserActivity> mDataSet;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_history, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataSet != null) {
            UserActivity activity = mDataSet.get(position);

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
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public void setDataSet(List<UserActivity> dataSet) {
        mDataSet = dataSet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.type) TextView type;
        @Bind(R.id.victim) TextView victim;
        @Bind(R.id.series) TextView series;
        @Bind(R.id.episode) TextView episode;
        @Bind(R.id.timestamp) TextView timestamp;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
