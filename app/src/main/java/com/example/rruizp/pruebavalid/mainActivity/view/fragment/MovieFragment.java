package com.example.rruizp.pruebavalid.mainActivity.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rruizp.pruebavalid.BuildConfig;
import com.example.rruizp.pruebavalid.R;
import com.example.rruizp.pruebavalid.adapter.MovieAdapter;
import com.example.rruizp.pruebavalid.mainActivity.presenter.MoviePresenter;
import com.example.rruizp.pruebavalid.mainActivity.presenter.MoviePresenterImpl;
import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.MovieResponse;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Constants;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.RestApiAdapter;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Service;
import com.example.rruizp.pruebavalid.utils.ProgressLottie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieView {

    @BindView(R.id.popular_movie_list)
    RecyclerView popularMovieList;
    @BindView(R.id.top_rated_movie_list)
    RecyclerView topRatedMovieList;
    @BindView(R.id.upcoming_movie_list)
    RecyclerView upcomingMovieList;
    Unbinder unbinder;

    ProgressLottie progressLottie;

    Service service = RestApiAdapter.createService(Service.class);
    private MoviePresenter moviePresenter;

    MovieAdapter movieAdapterPopular,movieAdapterTopRated,movieAdapterUpcoming;
    MovieAdapter.OnLoadMoreListener popularOnloadMoreListener,topRatedOnloadMoreListener,upcomingOnLoadMoreListener;
    LinearLayoutManager layoutManagerPopular,layoutManagerTopRated,layoutManagerUpcoming;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressLottie = new ProgressLottie(getActivity());
        moviePresenter = new MoviePresenterImpl(this);

        popularOnloadMoreListener = new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(final int pages) {
                movieAdapterPopular.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapterPopular.setProgressMore(false);
                        Call<MovieResponse> callMoviePopular = service.getMovie("popular", BuildConfig.THE_MOVIE_DATABASE_API,Constants.LANGUAGES,Constants.POPULAR_MOVIE_PAGE);
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
                                    movieAdapterPopular.addItemMore(response.body().getResults());
                                    movieAdapterPopular.setMoreLoading(false);
                                    Constants.POPULAR_MOVIE_PAGE++;
                                }else {
                                    Toast.makeText(getActivity(), response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },2000);
            }
        };
        topRatedOnloadMoreListener = new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pages) {
                movieAdapterTopRated.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapterTopRated.setProgressMore(false);
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
                                    movieAdapterTopRated.addItemMore(response.body().getResults());
                                    movieAdapterTopRated.setMoreLoading(false);
                                    Constants.TOP_RATED_MOVIE_PAGE++;
                                }else {
                                    Toast.makeText(getActivity(), response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                },2000);
            }
        };
        upcomingOnLoadMoreListener = new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pages) {
                movieAdapterUpcoming.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapterPopular.setProgressMore(false);
                        Call<MovieResponse> callMovieUcoming = service.getMovie("upcoming",BuildConfig.THE_MOVIE_DATABASE_API,Constants.LANGUAGES,Constants.UPCOMING_MOVIE_PAGE);
                        callMovieUcoming.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                if (response.isSuccessful() && response.body().getStatus_message()==null){
                                    ArrayList<Movie> moviesDB = new ArrayList<>();
                                    for (int i = 0; i < response.body().getResults().size(); i++) {
                                        moviesDB.add(response.body().getResults().get(i));
                                        response.body().getResults().get(i).setCategory(2);
                                        Constants.pruebaValidDataBase.insertMovie(response.body().getResults().get(i));
                                    }
                                    movieAdapterUpcoming.addItemMore(response.body().getResults());
                                    movieAdapterUpcoming.setMoreLoading(false);
                                    Constants.UPCOMING_MOVIE_PAGE++;
                                }else {
                                    Toast.makeText(getActivity(), response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                },2000);
            }
        };


        movieAdapterPopular = new MovieAdapter(getActivity(),popularOnloadMoreListener, Constants.POPULAR_MOVIE_PAGE);
        movieAdapterTopRated = new MovieAdapter(getActivity(),topRatedOnloadMoreListener,Constants.TOP_RATED_MOVIE_PAGE);
        movieAdapterUpcoming = new MovieAdapter(getActivity(),upcomingOnLoadMoreListener,Constants.UPCOMING_MOVIE_PAGE);

        layoutManagerPopular = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManagerTopRated = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManagerUpcoming = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        popularMovieList.setLayoutManager(layoutManagerPopular);
        movieAdapterPopular.setLinearLayoutManager(layoutManagerPopular);
        movieAdapterPopular.setRecyclerView(popularMovieList);
        popularMovieList.setAdapter(movieAdapterPopular);


        topRatedMovieList.setLayoutManager(layoutManagerTopRated);
        movieAdapterTopRated.setLinearLayoutManager(layoutManagerTopRated);
        movieAdapterTopRated.setRecyclerView(topRatedMovieList);
        topRatedMovieList.setAdapter(movieAdapterTopRated);

        upcomingMovieList.setLayoutManager(layoutManagerUpcoming);
        movieAdapterUpcoming.setLinearLayoutManager(layoutManagerUpcoming);
        movieAdapterUpcoming.setRecyclerView(upcomingMovieList);
        upcomingMovieList.setAdapter(movieAdapterUpcoming);


        getDataMoviePopular();
        getDataMovieTopRated();
        getDataMovieUpcoming();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getDataMoviePopular() {
        moviePresenter.getDataMoviePopular();

    }

    @Override
    public void getDataMovieTopRated() {
        moviePresenter.getDataMovieTopRated();
    }

    @Override
    public void getDataMovieUpcoming() {
        moviePresenter.getDataMovieUpcoming();
    }

    @Override
    public void showDataMoviePopular(ArrayList<Movie> moviesPopular) {
        movieAdapterPopular.addAll(moviesPopular);
    }

    @Override
    public void showDataMovieTopRated(ArrayList<Movie> moviesTopRated) {
        movieAdapterTopRated.addAll(moviesTopRated);
    }

    @Override
    public void showDataMovieUpcoming(ArrayList<Movie> moviesUpcoming) {
        movieAdapterUpcoming.addAll(moviesUpcoming);
    }


    @Override
    public void movieError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        if (!Constants.OK_NETWORK && Constants.pruebaValidDataBase.allMovies() != null){

            ArrayList<Movie> moviePopular = new ArrayList<>();
            ArrayList<Movie> movieTopRated = new ArrayList<>();
            ArrayList<Movie> movieUpcoming = new ArrayList<>();
            for (int i = 0; i < Constants.pruebaValidDataBase.allMovies().size(); i++) {
                switch (Constants.pruebaValidDataBase.allMovies().get(i).getCategory()){
                    case 0:
                        moviePopular.add(Constants.pruebaValidDataBase.allMovies().get(i));
                        break;
                    case 1:
                        movieTopRated.add(Constants.pruebaValidDataBase.allMovies().get(i));
                        break;
                    case 2:
                        movieUpcoming.add(Constants.pruebaValidDataBase.allMovies().get(i));
                        break;
                }
            }
            movieAdapterPopular.addAll(moviePopular);
            movieAdapterTopRated.addAll(movieTopRated);
            movieAdapterUpcoming.addAll(movieUpcoming);

        }
    }

    @Override
    public void showProgress() {
        progressLottie.show();
    }

    @Override
    public void hideProgress() {
        progressLottie.dismiss();
    }
}
