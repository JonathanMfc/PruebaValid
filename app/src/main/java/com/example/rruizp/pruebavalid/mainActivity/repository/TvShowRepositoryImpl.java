package com.example.rruizp.pruebavalid.mainActivity.repository;

import com.example.rruizp.pruebavalid.BuildConfig;
import com.example.rruizp.pruebavalid.mainActivity.presenter.TvShowPresenter;
import com.example.rruizp.pruebavalid.model.TvShow;
import com.example.rruizp.pruebavalid.model.TvShowResponse;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Constants;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.RestApiAdapter;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class TvShowRepositoryImpl implements TvShowRepository {

    private TvShowPresenter tvShowPresenter;
    Service service = RestApiAdapter.createService(Service.class);

    public TvShowRepositoryImpl(TvShowPresenter tvShowPresenter) {
        this.tvShowPresenter = tvShowPresenter;
    }


    @Override
    public void getDataTvPopular() {
        Call<TvShowResponse> callTvPopular = service.getTvShow("popular", BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES,Constants.POPULAR_MOVIE_PAGE);
        callTvPopular.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    ArrayList<TvShow> tvShowsDB = new ArrayList<>();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        tvShowsDB.add(response.body().getResults().get(i));
                        response.body().getResults().get(i).setCategory(0);
                        Constants.pruebaValidDataBase.insertTvShow(response.body().getResults().get(i));
                    }
                    tvShowPresenter.showDataTvPopular(response.body().getResults());
                    Constants.POPULAR_TV_PAGE++;
                }else {
                    tvShowPresenter.tvError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                tvShowPresenter.tvError(t.getMessage());
            }
        });


    }

    @Override
    public void getDataTvTopRated() {
        Call<TvShowResponse> callTvTopRated = service.getTvShow("top_rated",BuildConfig.THE_MOVIE_DATABASE_API,Constants.LANGUAGES,Constants.TOP_RATED_MOVIE_PAGE);
        callTvTopRated.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    ArrayList<TvShow> tvShowsDB = new ArrayList<>();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        tvShowsDB.add(response.body().getResults().get(i));
                        response.body().getResults().get(i).setCategory(1);
                        Constants.pruebaValidDataBase.insertTvShow(response.body().getResults().get(i));
                    }
                    tvShowPresenter.showDataTvTopRated(response.body().getResults());
                    Constants.TOP_RATED_TV_PAGE++;
                }else {
                    tvShowPresenter.tvError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                tvShowPresenter.tvError(t.getMessage());
            }
        });
    }
}
