package com.example.rruizp.pruebavalid.mainActivity.view.fragment;

import com.example.rruizp.pruebavalid.model.Movie;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public interface MovieView {

    void getDataMoviePopular();
    void getDataMovieTopRated();
    void getDataMovieUpcoming();
    void showDataMoviePopular(ArrayList<Movie> moviesPopular);
    void showDataMovieTopRated(ArrayList<Movie> moviesTopRated);
    void showDataMovieUpcoming(ArrayList<Movie> moviesUpcoming);
    void movieError(String error);
    void showProgress();
    void hideProgress();
}
