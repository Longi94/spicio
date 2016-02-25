package com.tlongdev.spicio.presenter;

import com.tlongdev.spicio.SpicioApplication;
import com.tlongdev.spicio.api.TvdbInterface;
import com.tlongdev.spicio.model.SeriesData;
import com.tlongdev.spicio.ui.activity.MainActivity;
import com.tlongdev.spicio.ui.fragment.SearchSeriesView;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Long
 * @since 2016. 02. 24.
 */
public class SearchSeriesPresenter implements Presenter<SearchSeriesView> {

    @Inject TvdbInterface tvdbInterface;

    private SearchSeriesView view;

    @Override
    public void attachView(SearchSeriesView view) {
        this.view = view;
        ((SpicioApplication)((MainActivity)view.getContext()).getApplication())
                .getNetWorkComponent().inject(this);
    }

    @Override
    public void detachView() {
        view = null;
    }

    public void searchForSeries(String query) {
        tvdbInterface.getSeries(query).enqueue(new Callback<SeriesData>() {
            @Override
            public void onResponse(Call<SeriesData> call, Response<SeriesData> response) {
                if (view != null) {
                    view.showSearchResult(response.body().getSeries());
                }
            }

            @Override
            public void onFailure(Call<SeriesData> call, Throwable t) {
                if (view != null) {
                    view.showErrorMessage();
                }
            }
        });
    }
}
