package com.example.rruizp.pruebavalid.mainActivity.presenter;

import com.example.rruizp.pruebavalid.mainActivity.interactor.MovieInteractor;
import com.example.rruizp.pruebavalid.mainActivity.interactor.MovieInteractorImpl;
import com.example.rruizp.pruebavalid.mainActivity.view.fragment.MovieView;
import com.example.rruizp.pruebavalid.model.Movie;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class MoviePresenterImpl implements MoviePresenter {

    private MovieView movieView;
    private MovieInteractor movieInteractor;

    public MoviePresenterImpl(MovieView movieView) {
        this.movieView = movieView;
        movieInteractor = new MovieInteractorImpl(this);
    }



    @Override
    public void getDataMoviePopular() {
        movieView.showProgress();
        movieInteractor.getDataMoviePopular();
    }

    @Override
    public void getDataMovieTopRated() {
        movieView.showProgress();
        movieInteractor.getDataMovieTopRated();
    }

    @Override
    public void getDataMovieUpcoming() {
        movieView.showProgress();
        movieInteractor.getDataMovieUpcoming();
    }


    @Override
    public void showDataMoviePopular(ArrayList<Movie> moviesPopular) {
        movieView.hideProgress();
        movieView.showDataMoviePopular(moviesPopular);
    }

    @Override
    public void showDataMovieTopRated(ArrayList<Movie> moviesTopRated) {
        movieView.hideProgress();
        movieView.showDataMovieTopRated(moviesTopRated);
    }

    @Override
    public void showDataMovieUpcoming(ArrayList<Movie> moviesUpcoming) {
        movieView.hideProgress();
        movieView.showDataMovieUpcoming(moviesUpcoming);
    }



    @Override
    public void movieError(String error) {
        movieView.hideProgress();
        movieView.movieError(error);
    }
}
