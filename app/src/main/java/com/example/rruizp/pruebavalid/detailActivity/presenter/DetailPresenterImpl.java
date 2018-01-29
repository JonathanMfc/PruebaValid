package com.example.rruizp.pruebavalid.detailActivity.presenter;

import com.example.rruizp.pruebavalid.detailActivity.interactor.DetailInteractor;
import com.example.rruizp.pruebavalid.detailActivity.interactor.DetailInteractorImpl;
import com.example.rruizp.pruebavalid.detailActivity.view.DetailView;
import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class DetailPresenterImpl implements DetailPresenter {

    private DetailView detailView;
    private DetailInteractor detailInteractor;

    public DetailPresenterImpl(DetailView detailView) {
        this.detailView = detailView;
        detailInteractor = new DetailInteractorImpl(this);
    }

    @Override
    public void getDataMovieRecommendations(int movie_id) {
        detailView.showProgress();
        detailInteractor.getDataMovieRecommendations(movie_id);

    }

    @Override
    public void getDataTvRecommendations(int tv_id) {
        detailView.showProgress();
        detailInteractor.getDataTvRecommendations(tv_id);

    }

    @Override
    public void showDataMovieRecommendations(ArrayList<Movie> movies) {
        detailView.hideProgress();
        detailView.showDataMovieRecommendations(movies);
    }

    @Override
    public void showDataTvRecommendations(ArrayList<TvShow> tvShows) {
        detailView.hideProgress();
        detailView.showDataTvRecommendations(tvShows);
    }

    @Override
    public void recommendationsError(String error) {
        detailView.hideProgress();
        detailView.recommendationsError(error);

    }
}
