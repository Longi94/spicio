package com.tlongdev.spicio.presentation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.Series;

import java.util.List;

import butterknife.Bind;
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
            Series series = mDataSet.get(position);

            holder.text.setText(series.getTitle());

            Glide.with(mContext)
                    .load(series.getImages().getPoster().getFull())
                    .error(R.drawable.ic_movie)
                    .into(holder.poster);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text) TextView text;
        @Bind(R.id.poster) ImageView poster;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
