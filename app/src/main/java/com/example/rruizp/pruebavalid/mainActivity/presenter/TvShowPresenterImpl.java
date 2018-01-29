package com.example.rruizp.pruebavalid.mainActivity.presenter;

import com.example.rruizp.pruebavalid.mainActivity.interactor.TvShowInteractor;
import com.example.rruizp.pruebavalid.mainActivity.interactor.TvShowInteractorImpl;
import com.example.rruizp.pruebavalid.mainActivity.view.fragment.TvShowView;
import com.example.rruizp.pruebavalid.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class TvShowPresenterImpl implements TvShowPresenter {

    private TvShowView tvShowView;
    private TvShowInteractor tvShowInteractor;

    public TvShowPresenterImpl(TvShowView tvShowView) {
        this.tvShowView = tvShowView;
        tvShowInteractor = new TvShowInteractorImpl(this);
    }


    @Override
    public void getDataTvPopular() {
        tvShowView.showProgress();
        tvShowInteractor.getDataTvPopular();
    }

    @Override
    public void getDataTvTopRated() {
        tvShowView.showProgress();
        tvShowInteractor.getDataTvTopRated();

    }

    @Override
    public void showDataTvPopular(ArrayList<TvShow> tvShowsPopular) {
        tvShowView.hideProgress();
        tvShowView.showDataTvPopular(tvShowsPopular);
    }

    @Override
    public void showDataTvTopRated(ArrayList<TvShow> tvShowsTopRated) {
        tvShowView.hideProgress();
        tvShowView.showDataTvTopRated(tvShowsTopRated);
    }

    @Override
    public void tvError(String error) {
        tvShowView.hideProgress();
        tvShowView.tvError(error);
    }
}