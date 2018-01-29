package com.example.rruizp.pruebavalid.detailActivity.interactor;

import com.example.rruizp.pruebavalid.detailActivity.presenter.DetailPresenter;
import com.example.rruizp.pruebavalid.detailActivity.repository.DetailRepository;
import com.example.rruizp.pruebavalid.detailActivity.repository.DetailRepositoryImpl;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class DetailInteractorImpl implements DetailInteractor {

    private DetailPresenter detailPresenter;
    private DetailRepository detailRepository;

    public DetailInteractorImpl(DetailPresenter detailPresenter) {
        this.detailPresenter = detailPresenter;
        detailRepository = new DetailRepositoryImpl(detailPresenter);
    }

    @Override
    public void getDataMovieRecommendations(int movie_id) {
        detailRepository.getDataMovieRecommendations(movie_id);
    }

    @Override
    public void getDataTvRecommendations(int tv_id) {
        detailRepository.getDataTvRecommendations(tv_id);
    }
}
