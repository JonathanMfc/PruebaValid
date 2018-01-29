package com.example.rruizp.pruebavalid.mainActivity.interactor;

import com.example.rruizp.pruebavalid.mainActivity.presenter.MainPresenter;
import com.example.rruizp.pruebavalid.mainActivity.repository.MainRepository;
import com.example.rruizp.pruebavalid.mainActivity.repository.MainRepositoryImpl;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class MainInteractorImpl implements MainInteractor {

    private MainPresenter mainPresenter;
    private MainRepository mainRepository;

    public MainInteractorImpl(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
        mainRepository = new MainRepositoryImpl(mainPresenter);
    }

    @Override
    public void getDataResultSearchMovie(String movie) {
        mainRepository.getDataResultSearchMovie(movie);

    }

    @Override
    public void getDataResultSearchTv(String tvShow) {
        mainRepository.getDataResultSearchTv(tvShow);
    }
}
