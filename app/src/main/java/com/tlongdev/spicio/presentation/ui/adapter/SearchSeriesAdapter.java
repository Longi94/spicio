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
import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Outer Layer, UI.
 *
 * @author Long
 * @since 2016. 02. 25.
 */
public class SearchSeriesAdapter extends RecyclerView.Adapter<SearchSeriesAdapter.ViewHolder> {

    private List<Series> mDataSet;

    private Context mContext;

    private OnItemSelectedListener listener;

    public SearchSeriesAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_series, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataSet != null) {
            final Series series = mDataSet.get(position);

            holder.text.setText(series.getTitle());

            if (series.getImages().getPoster().getFull() != null) {
                holder.poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                holder.poster.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

            Glide.with(mContext)
                    .load(series.getImages().getPoster().getFull())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_movie)
                    .into(holder.poster);

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemSelected(series);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public void setDataSet(List<Series> dataSet) {
        this.mDataSet = dataSet;
        notifyDataSetChanged();
    }

    public void setListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text) TextView text;
        @BindView(R.id.poster) ImageView poster;

        View root;

        public ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Series series);
    }
}
