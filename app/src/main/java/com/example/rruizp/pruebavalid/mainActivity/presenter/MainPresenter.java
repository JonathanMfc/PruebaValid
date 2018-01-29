package com.example.rruizp.pruebavalid.mainActivity.presenter;

import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public interface MainPresenter {

    void getDataResultSearchMovie(String movie);
    void getDataResultSearchTv(String tvShow);
    void showDataResultSearchMovie(ArrayList<Movie> movies);
    void showDataResultSearchTv(ArrayList<TvShow> tvShows);
    void resultError(String error);
}
