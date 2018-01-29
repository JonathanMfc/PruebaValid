package com.example.rruizp.pruebavalid.mainActivity.presenter;

import com.example.rruizp.pruebavalid.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public interface TvShowPresenter {

    void getDataTvPopular();
    void getDataTvTopRated();
    void showDataTvPopular(ArrayList<TvShow> tvShowsPopular);
    void showDataTvTopRated(ArrayList<TvShow> tvShowsTopRated);
    void tvError(String error);
}
