package com.tlongdev.spicio.presentation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.Episode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Long
 * @since 2016. 03. 10.
 */
public class SeasonEpisodesAdapter extends RecyclerView.Adapter<SeasonEpisodesAdapter.ViewHolder> {

    private Context mContext;

    private List<Episode> mDataSet;
    private OnItemSelectedListener listener;

    public SeasonEpisodesAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_seasons, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataSet != null) {
            final Episode episode = mDataSet.get(position);

            holder.title.setText(String.format("Episode %d", episode.getNumber()));

            Glide.with(mContext)
                    .load(episode.getImages().getScreenshot().getThumb())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumb);

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemSelected(episode);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public void setDataSet(List<Episode> dataSet) {
        mDataSet = dataSet;
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.thumb) ImageView thumb;
        @Bind(R.id.title) TextView title;

        View root;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            root = view;
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Episode episode);
    }
}
