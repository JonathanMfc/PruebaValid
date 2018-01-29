package com.example.rruizp.pruebavalid.mainActivity.repository;

import com.example.rruizp.pruebavalid.BuildConfig;
import com.example.rruizp.pruebavalid.mainActivity.presenter.MoviePresenter;
import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.MovieResponse;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Constants;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.RestApiAdapter;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class MovieRepositoryImpl implements MovieRepository {

    private MoviePresenter moviePresenter;
    Service service = RestApiAdapter.createService(Service.class);

    public MovieRepositoryImpl(MoviePresenter moviePresenter) {
        this.moviePresenter = moviePresenter;

    }


    @Override
    public void getDataMoviePopular() {
        Call<MovieResponse> callMoviePopular = service.getMovie("popular", BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES,Constants.POPULAR_MOVIE_PAGE);
        callMoviePopular.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    ArrayList<Movie> moviesDB = new ArrayList<>();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        moviesDB.add(response.body().getResults().get(i));
                        response.body().getResults().get(i).setCategory(0);
                        Constants.pruebaValidDataBase.insertMovie(response.body().getResults().get(i));
                    }
                    moviePresenter.showDataMoviePopular(response.body().getResults());
                    Constants.POPULAR_MOVIE_PAGE++;
                }else {
                    moviePresenter.movieError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                moviePresenter.movieError(t.getMessage());
            }
        });

    }

    @Override
    public void getDataMovieTopRated() {

        Call<MovieResponse> callMovieTopRated = service.getMovie("top_rated",BuildConfig.THE_MOVIE_DATABASE_API,Constants.LANGUAGES,Constants.TOP_RATED_MOVIE_PAGE);
        callMovieTopRated.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    ArrayList<Movie> moviesDB = new ArrayList<>();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        moviesDB.add(response.body().getResults().get(i));
                        response.body().getResults().get(i).setCategory(1);
                        Constants.pruebaValidDataBase.insertMovie(response.body().getResults().get(i));
                    }
                    moviePresenter.showDataMovieTopRated(response.body().getResults());
                    Constants.TOP_RATED_MOVIE_PAGE++;
                }else {
                    moviePresenter.movieError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                moviePresenter.movieError(t.getMessage());
            }
        });
    }

    @Override
    public void getDataMovieUpcoming() {

        Call<MovieResponse> callMovieUpcoming = service.getMovie("upcoming",BuildConfig.THE_MOVIE_DATABASE_API,Constants.LANGUAGES,Constants.UPCOMING_MOVIE_PAGE);
        callMovieUpcoming.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body().getStatus_message()==null){
                    ArrayList<Movie> moviesDB = new ArrayList<>();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        moviesDB.add(response.body().getResults().get(i));
                        response.body().getResults().get(i).setCategory(2);
                        Constants.pruebaValidDataBase.insertMovie(response.body().getResults().get(i));
                    }
                    moviePresenter.showDataMovieUpcoming(response.body().getResults());
                    Constants.UPCOMING_MOVIE_PAGE = response.body().getPage();
                }else {
                    moviePresenter.movieError(response.body().getStatus_message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                moviePresenter.movieError(t.getMessage());
            }
        });

    }
}

