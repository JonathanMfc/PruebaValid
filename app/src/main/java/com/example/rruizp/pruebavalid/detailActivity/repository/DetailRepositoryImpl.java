package com.example.rruizp.pruebavalid.detailActivity.repository;

import com.example.rruizp.pruebavalid.BuildConfig;
import com.example.rruizp.pruebavalid.detailActivity.presenter.DetailPresenter;
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

public class DetailRepositoryImpl implements DetailRepository {


    private DetailPresenter detailPresenter;
    Service service = RestApiAdapter.createService(Service.class);

    public DetailRepositoryImpl(DetailPresenter detailPresenter) {
        this.detailPresenter = detailPresenter;
    }

    @Override
    public void getDataMovieRecommendations(int movie_id) {
        Call<MovieResponse> callMovieRecommendations = service.getMovieRecommendations(movie_id, BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES,Constants.RECOMMENDATION_MOVIE_PAGE);
        callMovieRecommendations.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    detailPresenter.showDataMovieRecommendations(response.body().getResults());
                    Constants.RECOMMENDATION_MOVIE_PAGE++;
                }else {
                    detailPresenter.recommendationsError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                detailPresenter.recommendationsError(t.getMessage());
            }
        });

    }

    @Override
    public void getDataTvRecommendations(int tv_id) {
        Call<TvShowResponse> callTvRecommendations = service.getTvShowRecommendations(tv_id,BuildConfig.THE_MOVIE_DATABASE_API,Constants.LANGUAGES,Constants.RECOMMENDATION_TV_PAGE);
        callTvRecommendations.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    detailPresenter.showDataTvRecommendations(response.body().getResults());
                    Constants.RECOMMENDATION_TV_PAGE++;
                }else {
                    detailPresenter.recommendationsError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                detailPresenter.recommendationsError(t.getMessage());
            }
        });
    }
}
