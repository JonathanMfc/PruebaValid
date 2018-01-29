package com.example.rruizp.pruebavalid.mainActivity.presenter;

import com.example.rruizp.pruebavalid.mainActivity.interactor.MainInteractor;
import com.example.rruizp.pruebavalid.mainActivity.interactor.MainInteractorImpl;
import com.example.rruizp.pruebavalid.mainActivity.view.MainView;
import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        mainInteractor = new MainInteractorImpl(this);
    }

    @Override
    public void getDataResultSearchMovie(String movie) {
        mainView.showProgress();
        mainInteractor.getDataResultSearchMovie(movie);

    }

    @Override
    public void getDataResultSearchTv(String tvShow) {
        mainView.showProgress();
        mainInteractor.getDataResultSearchTv(tvShow);

    }

    @Override
    public void showDataResultSearchMovie(ArrayList<Movie> movies) {
        mainView.hideProgress();
        mainView.showDataResultSearchMovie(movies);
    }

    @Override
    public void showDataResultSearchTv(ArrayList<TvShow> tvShows) {

        mainView.hideProgress();
        mainView.showDataResultSearchTv(tvShows);
    }

    @Override
    public void resultError(String error) {
        mainView.hideProgress();
        mainView.resultError(error);
    }
}
