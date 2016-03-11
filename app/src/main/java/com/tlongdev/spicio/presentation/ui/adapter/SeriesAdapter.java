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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Long
 * @since 2016. 03. 07.
 */
public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    private List<Series> mDataSet;

    private Context mContext;

    private OnItemSelectedListener listener;

    public SeriesAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_series, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataSet != null) {
            final Series series = mDataSet.get(position);

            holder.title.setText(series.getTitle());

            Glide.with(mContext)
                    .load(series.getImages().getThumb().getFull())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_movie)
                    .into(holder.thumb);

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

    public void setDataSet(List<Series> sataSet) {
        this.mDataSet = sataSet;
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
        void onItemSelected(Series series);
    }
}
