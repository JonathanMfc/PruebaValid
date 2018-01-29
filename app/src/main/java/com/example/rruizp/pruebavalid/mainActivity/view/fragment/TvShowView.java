package com.example.rruizp.pruebavalid.mainActivity.view.fragment;

import com.example.rruizp.pruebavalid.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public interface TvShowView {

    void getDataTvPopular();
    void getDataTvTopRated();
    void showDataTvPopular(ArrayList<TvShow> tvShowsPopular);
    void showDataTvTopRated(ArrayList<TvShow> tvShowsTopRated);
    void tvError(String error);
    void showProgress();
    void hideProgress();

}
