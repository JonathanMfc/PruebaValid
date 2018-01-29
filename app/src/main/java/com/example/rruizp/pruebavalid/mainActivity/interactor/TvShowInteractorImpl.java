package com.example.rruizp.pruebavalid.mainActivity.interactor;

import com.example.rruizp.pruebavalid.mainActivity.presenter.TvShowPresenter;
import com.example.rruizp.pruebavalid.mainActivity.repository.TvShowRepository;
import com.example.rruizp.pruebavalid.mainActivity.repository.TvShowRepositoryImpl;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class TvShowInteractorImpl implements TvShowInteractor {

    private TvShowPresenter tvShowPresenter;
    private TvShowRepository tvShowRepository;

    public TvShowInteractorImpl(TvShowPresenter tvShowPresenter) {
        this.tvShowPresenter = tvShowPresenter;
        tvShowRepository = new TvShowRepositoryImpl(tvShowPresenter);
    }


    @Override
    public void getDataTvPopular() {
        tvShowRepository.getDataTvPopular();

    }

    @Override
    public void getDataTvTopRated() {
        tvShowRepository.getDataTvTopRated();
    }

}