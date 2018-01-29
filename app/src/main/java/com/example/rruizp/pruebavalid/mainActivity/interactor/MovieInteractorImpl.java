package com.example.rruizp.pruebavalid.mainActivity.interactor;

import com.example.rruizp.pruebavalid.mainActivity.presenter.MoviePresenter;
import com.example.rruizp.pruebavalid.mainActivity.repository.MovieRepository;
import com.example.rruizp.pruebavalid.mainActivity.repository.MovieRepositoryImpl;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class MovieInteractorImpl implements MovieInteractor {

    private MoviePresenter moviePresenter;
    private MovieRepository movieRepository;

    public MovieInteractorImpl(MoviePresenter moviePresenter) {
        this.moviePresenter = moviePresenter;
        movieRepository = new MovieRepositoryImpl(moviePresenter);
    }


    @Override
    public void getDataMoviePopular() {
        movieRepository.getDataMoviePopular();
    }

    @Override
    public void getDataMovieTopRated() {
        movieRepository.getDataMovieTopRated();
    }

    @Override
    public void getDataMovieUpcoming() {
        movieRepository.getDataMovieUpcoming();
    }
}

