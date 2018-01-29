package com.example.rruizp.pruebavalid.detailActivity.view;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rruizp.pruebavalid.BuildConfig;
import com.example.rruizp.pruebavalid.R;
import com.example.rruizp.pruebavalid.adapter.MovieAdapter;
import com.example.rruizp.pruebavalid.adapter.TvShowAdapter;
import com.example.rruizp.pruebavalid.detailActivity.presenter.DetailPresenter;
import com.example.rruizp.pruebavalid.detailActivity.presenter.DetailPresenterImpl;
import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.MovieResponse;
import com.example.rruizp.pruebavalid.model.TvShow;
import com.example.rruizp.pruebavalid.model.TvShowResponse;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Constants;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.RestApiAdapter;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Service;
import com.example.rruizp.pruebavalid.utils.ProgressLottie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @BindView(R.id.imageHeader)
    ImageView imageHeader;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.recommendations_list)
    RecyclerView recommendationsList;

    ProgressLottie progressLottie;

    MovieAdapter movieAdapterRecommendations;
    TvShowAdapter tvShowAdapterRecommendations;
    MovieAdapter.OnLoadMoreListener recommendationsMovieOnloadMoreListener;
    TvShowAdapter.OnLoadMoreListener recommendationsTvOnloadMoreListener;
    LinearLayoutManager layoutManagerRecommendations;

    Service service = RestApiAdapter.createService(Service.class);
    @BindView(R.id.no_connection)
    TextView noConnection;

    private DetailPresenter detailPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        progressLottie = new ProgressLottie(this);
        detailPresenter = new DetailPresenterImpl(this);

        title.setText(getIntent().getStringExtra("title"));
        score.setText(getIntent().getStringExtra("score"));
        releaseDate.setText(getIntent().getStringExtra("date"));
        overview.setText(getIntent().getStringExtra("overview"));
        Glide.with(this).load(Constants.IMG_URL + getIntent().getStringExtra("img")).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(imageHeader);


        recommendationsMovieOnloadMoreListener = new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pages) {
                movieAdapterRecommendations.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapterRecommendations.setProgressMore(false);
                        Call<MovieResponse> callMovieRecommendations = service.getMovieRecommendations(getIntent().getIntExtra("id", 0), BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES, Constants.RECOMMENDATION_MOVIE_PAGE);
                        callMovieRecommendations.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                if (response.isSuccessful() && response.body().getStatus_message() == null) {
                                    movieAdapterRecommendations.addItemMore(response.body().getResults());
                                    movieAdapterRecommendations.setMoreLoading(false);
                                    Constants.RECOMMENDATION_MOVIE_PAGE++;
                                } else {
                                    Toast.makeText(DetailActivity.this, response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {
                                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, 2000);
            }
        };

        recommendationsTvOnloadMoreListener = new TvShowAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pages) {
                tvShowAdapterRecommendations.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvShowAdapterRecommendations.setProgressMore(false);
                        Call<TvShowResponse> callTvRecommendations = service.getTvShowRecommendations(getIntent().getIntExtra("id", 0), BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES, Constants.RECOMMENDATION_TV_PAGE);
                        callTvRecommendations.enqueue(new Callback<TvShowResponse>() {
                            @Override
                            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                if (response.isSuccessful() && response.body().getStatus_message() == null) {
                                    tvShowAdapterRecommendations.addItemMore(response.body().getResults());
                                    tvShowAdapterRecommendations.setMoreLoading(false);
                                    Constants.RECOMMENDATION_TV_PAGE++;
                                } else {
                                    Toast.makeText(DetailActivity.this, response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }, 2000);
            }
        };

        layoutManagerRecommendations = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        if (getIntent().getIntExtra("type", 0) == 0) {
            movieAdapterRecommendations = new MovieAdapter(this, recommendationsMovieOnloadMoreListener, Constants.RECOMMENDATION_MOVIE_PAGE);
            recommendationsList.setLayoutManager(layoutManagerRecommendations);
            movieAdapterRecommendations.setLinearLayoutManager(layoutManagerRecommendations);
            movieAdapterRecommendations.setRecyclerView(recommendationsList);
            recommendationsList.setAdapter(movieAdapterRecommendations);
            getDataMovieRecommendations(getIntent().getIntExtra("id", 0));
        } else {
            tvShowAdapterRecommendations = new TvShowAdapter(this, recommendationsTvOnloadMoreListener, Constants.RECOMMENDATION_TV_PAGE);
            recommendationsList.setLayoutManager(layoutManagerRecommendations);
            tvShowAdapterRecommendations.setLinearLayoutManager(layoutManagerRecommendations);
            tvShowAdapterRecommendations.setRecyclerView(recommendationsList);
            recommendationsList.setAdapter(tvShowAdapterRecommendations);
            getDataTvRecommendations(getIntent().getIntExtra("id", 0));
        }
    }

    @Override
    public void getDataMovieRecommendations(int movie_id) {
        detailPresenter.getDataMovieRecommendations(movie_id);
    }

    @Override
    public void getDataTvRecommendations(int tv_id) {
        detailPresenter.getDataTvRecommendations(tv_id);
    }

    @Override
    public void showDataMovieRecommendations(ArrayList<Movie> movies) {
        movieAdapterRecommendations.addAll(movies);
    }

    @Override
    public void showDataTvRecommendations(ArrayList<TvShow> tvShows) {
        tvShowAdapterRecommendations.addAll(tvShows);
    }

    @Override
    public void recommendationsError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        if (!Constants.OK_NETWORK) {
            noConnection.setVisibility(View.VISIBLE);
            recommendationsList.setVisibility(View.GONE);
        } else {
            noConnection.setVisibility(View.GONE);
            recommendationsList.setVisibility(View.VISIBLE);
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
