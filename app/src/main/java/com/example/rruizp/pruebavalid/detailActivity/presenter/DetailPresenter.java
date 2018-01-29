package com.example.rruizp.pruebavalid.detailActivity.presenter;

import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public interface DetailPresenter {

    void getDataMovieRecommendations(int movie_id);
    void getDataTvRecommendations(int tv_id);
    void showDataMovieRecommendations(ArrayList<Movie> movies);
    void showDataTvRecommendations(ArrayList<TvShow> tvShows);
    void recommendationsError(String error);
}
