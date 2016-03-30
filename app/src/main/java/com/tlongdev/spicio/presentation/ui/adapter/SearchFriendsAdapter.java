package com.tlongdev.spicio.presentation.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tlongdev.spicio.R;
import com.tlongdev.spicio.domain.model.User;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Long
 * @since 2016. 03. 30.
 */
public class SearchFriendsAdapter extends RecyclerView.Adapter<SearchFriendsAdapter.ViewHolder> {

    private List<User> mDataSet;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_friends, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataSet != null) {
            User user = mDataSet.get(position);

            holder.name.setText(user.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public void setDataSet(List<User> dataSet) {
        mDataSet = dataSet;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name) TextView name;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
