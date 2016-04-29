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
import com.tlongdev.spicio.domain.model.Season;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Long
 * @since 2016. 03. 09.
 */
public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.ViewHolder> {

    private List<Season> mDataSet;

    private Context mContext;

    private OnItemSelectedListener listener;

    public SeasonsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_seasons, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataSet != null) {
            final Season season = mDataSet.get(position);

            if (season.getNumber() > 0) {
                holder.title.setText(String.format("Season %d", season.getNumber()));
            } else {
                holder.title.setText("Special episodes");
            }

            Glide.with(mContext)
                    .load(season.getImages().getThumb().getFull())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_movie)
                    .into(holder.thumb);

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemSelected(season);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public void setDataSet(List<Season> dataSet) {
        this.mDataSet = dataSet;
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.thumb) ImageView thumb;
        @BindView(R.id.title) TextView title;

        View root;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            root = view;
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Season season);
    }
}
