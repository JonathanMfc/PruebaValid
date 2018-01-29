package com.example.rruizp.pruebavalid.mainActivity.repository;

import com.example.rruizp.pruebavalid.BuildConfig;
import com.example.rruizp.pruebavalid.mainActivity.presenter.MainPresenter;
import com.example.rruizp.pruebavalid.model.MovieResponse;
import com.example.rruizp.pruebavalid.model.TvShowResponse;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Constants;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.RestApiAdapter;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class MainRepositoryImpl implements MainRepository {

    private MainPresenter mainPresenter;
    Service service = RestApiAdapter.createService(Service.class);

    public MainRepositoryImpl(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }


    @Override
    public void getDataResultSearchMovie(String movie) {
        Call<MovieResponse> callMovieSearch = service.getMovieSearch(movie, BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES,Constants.SEARCH_MOVIE_PAGE);
        callMovieSearch.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    mainPresenter.showDataResultSearchMovie(response.body().getResults());
                    Constants.SEARCH_MOVIE_PAGE++;
                }else {
                    mainPresenter.resultError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mainPresenter.resultError(t.getMessage());
            }
        });
    }

    @Override
    public void getDataResultSearchTv(String tvShow) {
        Call<TvShowResponse> callTvSearch = service.getTvShowSearch(tvShow,BuildConfig.THE_MOVIE_DATABASE_API,Constants.LANGUAGES,Constants.SEARCH_TV_PAGE);
        callTvSearch.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    mainPresenter.showDataResultSearchTv(response.body().getResults());
                    Constants.SEARCH_TV_PAGE++;
                }else {
                    mainPresenter.resultError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                mainPresenter.resultError(t.getMessage());
            }
        });
    }
}
