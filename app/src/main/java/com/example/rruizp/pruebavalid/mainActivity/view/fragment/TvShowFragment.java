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
import com.example.rruizp.pruebavalid.adapter.TvShowAdapter;
import com.example.rruizp.pruebavalid.mainActivity.presenter.TvShowPresenter;
import com.example.rruizp.pruebavalid.mainActivity.presenter.TvShowPresenterImpl;
import com.example.rruizp.pruebavalid.model.TvShow;
import com.example.rruizp.pruebavalid.model.TvShowResponse;
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
public class TvShowFragment extends Fragment implements TvShowView{

    @BindView(R.id.popular_tv_show_list)
    RecyclerView popularTvShowList;
    @BindView(R.id.top_rated_tv_show_list)
    RecyclerView topRatedTvShowList;
    Unbinder unbinder;

    ProgressLottie progressLottie;

    private TvShowPresenter tvShowPresenter;


    TvShowAdapter tvShowAdapterPopular,tvShowAdapterTopRated;
    TvShowAdapter.OnLoadMoreListener popularOnloadMoreListener,topRatedOnloadMoreListener;
    LinearLayoutManager layoutManagerPopular,layoutManagerTopRated;

    Service service = RestApiAdapter.createService(Service.class);


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressLottie = new ProgressLottie(getActivity());
        tvShowPresenter = new TvShowPresenterImpl(this);

        popularOnloadMoreListener = new TvShowAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(final int pages) {
                tvShowAdapterPopular.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvShowAdapterPopular.setProgressMore(false);
                        Call<TvShowResponse> callTvPopular = service.getTvShow("popular", BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES,Constants.POPULAR_TV_PAGE);
                        callTvPopular.enqueue(new Callback<TvShowResponse>() {
                            @Override
                            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                if (response.isSuccessful() && response.body().getStatus_message()==null){
                                    ArrayList<TvShow> tvShowsDB = new ArrayList<>();
                                    for (int i = 0; i < response.body().getResults().size(); i++) {
                                        tvShowsDB.add(response.body().getResults().get(i));
                                        response.body().getResults().get(i).setCategory(0);
                                        Constants.pruebaValidDataBase.insertTvShow(response.body().getResults().get(i));
                                    }
                                    tvShowAdapterPopular.addItemMore(response.body().getResults());
                                    tvShowAdapterPopular.setMoreLoading(false);
                                    Constants.POPULAR_TV_PAGE++;
                                }else {
                                    Toast.makeText(getActivity(), response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                },2000);
            }
        };
        topRatedOnloadMoreListener = new TvShowAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pages) {
                tvShowAdapterTopRated.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvShowAdapterTopRated.setProgressMore(false);
                        Call<TvShowResponse> callTvTopRated = service.getTvShow("top_rated", BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES,Constants.TOP_RATED_TV_PAGE);
                        callTvTopRated.enqueue(new Callback<TvShowResponse>() {
                            @Override
                            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                if (response.isSuccessful() && response.body().getStatus_message()==null){
                                    ArrayList<TvShow> tvShowsDB = new ArrayList<>();
                                    for (int i = 0; i < response.body().getResults().size(); i++) {
                                        tvShowsDB.add(response.body().getResults().get(i));
                                        response.body().getResults().get(i).setCategory(1);
                                        Constants.pruebaValidDataBase.insertTvShow(response.body().getResults().get(i));
                                    }
                                    tvShowAdapterTopRated.addItemMore(response.body().getResults());
                                    tvShowAdapterTopRated.setMoreLoading(false);
                                    Constants.TOP_RATED_TV_PAGE++;
                                }else {
                                    Toast.makeText(getActivity(), response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                },2000);
            }
        };


        tvShowAdapterPopular = new TvShowAdapter(getActivity(),popularOnloadMoreListener,Constants.POPULAR_MOVIE_PAGE);
        tvShowAdapterTopRated = new TvShowAdapter(getActivity(),topRatedOnloadMoreListener,Constants.TOP_RATED_MOVIE_PAGE);

        layoutManagerPopular = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        layoutManagerTopRated = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        popularTvShowList.setLayoutManager(layoutManagerPopular);
        tvShowAdapterPopular.setLinearLayoutManager(layoutManagerPopular);
        tvShowAdapterPopular.setRecyclerView(popularTvShowList);
        popularTvShowList.setAdapter(tvShowAdapterPopular);


        topRatedTvShowList.setLayoutManager(layoutManagerTopRated);
        tvShowAdapterTopRated.setLinearLayoutManager(layoutManagerTopRated);
        tvShowAdapterTopRated.setRecyclerView(topRatedTvShowList);
        topRatedTvShowList.setAdapter(tvShowAdapterTopRated);



        getDataTvPopular();
        getDataTvTopRated();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getDataTvPopular() {
        tvShowPresenter.getDataTvPopular();
    }

    @Override
    public void getDataTvTopRated() {
        tvShowPresenter.getDataTvTopRated();
    }


    @Override
    public void showDataTvPopular(ArrayList<TvShow> tvShowsPopular) {
        tvShowAdapterPopular.addAll(tvShowsPopular);
    }

    @Override
    public void showDataTvTopRated(ArrayList<TvShow> tvShowsTopRated) {
        tvShowAdapterTopRated.addAll(tvShowsTopRated);
    }

    @Override
    public void tvError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        if (!Constants.OK_NETWORK && Constants.pruebaValidDataBase.allTvShows() != null){

            ArrayList<TvShow> tvShowsPopular = new ArrayList<>();
            ArrayList<TvShow> tvShowsTopRated = new ArrayList<>();
            for (int i = 0; i < Constants.pruebaValidDataBase.allTvShows().size(); i++) {
                switch (Constants.pruebaValidDataBase.allTvShows().get(i).getCategory()){
                    case 0:
                        tvShowsPopular.add(Constants.pruebaValidDataBase.allTvShows().get(i));
                        break;
                    case 1:
                        tvShowsTopRated.add(Constants.pruebaValidDataBase.allTvShows().get(i));
                        break;
                }
            }
            tvShowAdapterPopular.addAll(tvShowsPopular);
            tvShowAdapterTopRated.addAll(tvShowsTopRated);

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
