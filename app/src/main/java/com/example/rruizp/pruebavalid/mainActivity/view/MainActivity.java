package com.example.rruizp.pruebavalid.mainActivity.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rruizp.pruebavalid.BuildConfig;
import com.example.rruizp.pruebavalid.R;
import com.example.rruizp.pruebavalid.mainActivity.adapter.MovieSearchAdapter;
import com.example.rruizp.pruebavalid.mainActivity.adapter.TvShowSearchAdapter;
import com.example.rruizp.pruebavalid.mainActivity.presenter.MainPresenter;
import com.example.rruizp.pruebavalid.mainActivity.presenter.MainPresenterImpl;
import com.example.rruizp.pruebavalid.mainActivity.view.fragment.MovieFragment;
import com.example.rruizp.pruebavalid.mainActivity.view.fragment.TvShowFragment;
import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.MovieResponse;
import com.example.rruizp.pruebavalid.model.TvShow;
import com.example.rruizp.pruebavalid.model.TvShowResponse;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Constants;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.RestApiAdapter;
import com.example.rruizp.pruebavalid.theMovieDatabaseAPI.Service;
import com.example.rruizp.pruebavalid.utils.ProgressLottie;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.searchtoolbar)
    Toolbar searchtoolbar;

    Menu search_menu;
    MenuItem item_search;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.container)
    ViewPager container;

    AlertDialog alertDialog;
    TextView title_alert,no_connection;
    ImageView close_alert;
    RecyclerView listResult;

    private MainPresenter mainPresenter;
    ProgressLottie progressLottie;
    int tab_selected = 0;
    String query_search;

    MovieSearchAdapter movieAdapterSearch;
    TvShowSearchAdapter tvShowAdapterSearch;
    MovieSearchAdapter.OnLoadMoreListener searchMovieOnLoadMoreListener;
    TvShowSearchAdapter.OnLoadMoreListener searchTvOnLoadMoreListener;
    LinearLayoutManager layoutManagerSearch;

    Service service = RestApiAdapter.createService(Service.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    //    Constants.movieClickDataBase = new MovieClickDataBase(this);
        progressLottie = new ProgressLottie(this);
        mainPresenter = new MainPresenterImpl(this);

        setSupportActionBar(toolbar);
        setSearchtoolbar();

        setupViewPager(container);
        tabs.setupWithViewPager(container);

        checkConnection();

        searchMovieOnLoadMoreListener = new MovieSearchAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pages) {
                movieAdapterSearch.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieAdapterSearch.setProgressMore(false);
                        Call<MovieResponse> callMovieSearch = service.getMovieSearch(query_search, BuildConfig.THE_MOVIE_DATABASE_API,Constants.LANGUAGES,Constants.SEARCH_MOVIE_PAGE);
                        callMovieSearch.enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                                if (response.isSuccessful() && response.body().getStatus_message()==null){
                                    movieAdapterSearch.addItemMore(response.body().getResults());
                                    movieAdapterSearch.setMoreLoading(false);
                                    Constants.SEARCH_MOVIE_PAGE++;
                                }else {
                                    Toast.makeText(MainActivity.this, response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                },2000);

            }
        };

        searchTvOnLoadMoreListener = new TvShowSearchAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int pages) {
                tvShowAdapterSearch.setProgressMore(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvShowAdapterSearch.setProgressMore(false);
                        Call<TvShowResponse> callTvSearch = service.getTvShowSearch(query_search, BuildConfig.THE_MOVIE_DATABASE_API, Constants.LANGUAGES,Constants.SEARCH_TV_PAGE);
                        callTvSearch.enqueue(new Callback<TvShowResponse>() {
                            @Override
                            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                if (response.isSuccessful() && response.body().getStatus_message()==null){
                                    tvShowAdapterSearch.addItemMore(response.body().getResults());
                                    tvShowAdapterSearch.setMoreLoading(false);
                                    Constants.SEARCH_TV_PAGE++;
                                }else {
                                    Toast.makeText(MainActivity.this, response.body().getStatus_message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                },2000);
            }
        };

    }

    private void checkConnection() {
       // Constants.OK_NETWORK = ConnectivityReceiver.isConnected();

    }

    private void setupViewPager(final ViewPager container) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MovieFragment(),getString(R.string.tab_movie));
        viewPagerAdapter.addFragment(new TvShowFragment(),getString(R.string.tab_tv_shows));

        container.setAdapter(viewPagerAdapter);
        container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tab_selected = 0;
                        container.setCurrentItem(0);
                        break;
                    case 1:
                        tab_selected = 1;
                        container.setCurrentItem(1);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchtoolbar, 1, true, true);
                else
                    searchtoolbar.setVisibility(View.VISIBLE);

                item_search.expandActionView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSearchtoolbar() {
        searchtoolbar.inflateMenu(R.menu.menu_search);
        search_menu = searchtoolbar.getMenu();
        searchtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(R.id.searchtoolbar, 1, true, false);
                else
                    searchtoolbar.setVisibility(View.GONE);
            }
        });

        item_search = search_menu.findItem(R.id.action_filter_search);

        MenuItemCompat.setOnActionExpandListener(item_search, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    circleReveal(R.id.searchtoolbar, 1, true, false);
                } else
                    searchtoolbar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;
            }
        });

        initSearchView();


    }

    public void initSearchView() {
        final SearchView searchView =
                (SearchView) search_menu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard

        searchView.setSubmitButtonEnabled(false);

        // Change search close button image

        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_close);


        // set hint and the text colors

        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint(getString(R.string.search_ellipse));
        txtSearch.setHintTextColor(Color.DKGRAY);
        txtSearch.setTextColor(getResources().getColor(R.color.colorPrimary));


        // set the cursor

        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //callSearch(newText);
                return true;
            }

            public void callSearch(String query) {
                //Do searching
                Log.i("query", "" + query);

                if (!query.isEmpty()){
                    query_search = query;
                    showAlert(query);

                }

            }

        });

    }

    private void showAlert(String query) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        // ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_results, null);


        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        title_alert = (TextView)dialogView.findViewById(R.id.title_results);
        no_connection = (TextView)dialogView.findViewById(R.id.no_connection);
        listResult = (RecyclerView) dialogView.findViewById(R.id.list_result);
        close_alert = (ImageView) dialogView.findViewById(R.id.close_result);


        close_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        layoutManagerSearch = new LinearLayoutManager(this);
        if (tab_selected == 0){
            title_alert.setText(getString(R.string.tab_movie));
            movieAdapterSearch = new MovieSearchAdapter(this,searchMovieOnLoadMoreListener,Constants.SEARCH_MOVIE_PAGE);
            listResult.setLayoutManager(layoutManagerSearch);
            movieAdapterSearch.setLinearLayoutManager(layoutManagerSearch);
            movieAdapterSearch.setRecyclerView(listResult);
            listResult.setAdapter(movieAdapterSearch);
            getDataResultSearchMovie(query);
        }else {
            title_alert.setText(getString(R.string.tab_tv_shows));
            tvShowAdapterSearch = new TvShowSearchAdapter(this,searchTvOnLoadMoreListener,Constants.SEARCH_TV_PAGE);
            listResult.setLayoutManager(layoutManagerSearch);
            tvShowAdapterSearch.setLinearLayoutManager(layoutManagerSearch);
            tvShowAdapterSearch.setRecyclerView(listResult);
            listResult.setAdapter(tvShowAdapterSearch);
            getDataResultSearchTv(query);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        final View myView = findViewById(viewID);

        int width = myView.getWidth();

        if (posFromRight > 0)
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = myView.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if (isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();

    }

    @Override
    public void getDataResultSearchMovie(String movie) {
        mainPresenter.getDataResultSearchMovie(movie);
    }

    @Override
    public void getDataResultSearchTv(String tvShow) {
        mainPresenter.getDataResultSearchTv(tvShow);
    }

    @Override
    public void showDataResultSearchMovie(ArrayList<Movie> movies) {
        movieAdapterSearch.addAll(movies);

    }

    @Override
    public void showDataResultSearchTv(ArrayList<TvShow> tvShows) {
        tvShowAdapterSearch.addAll(tvShows);
    }

    @Override
    public void resultError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        if (!Constants.OK_NETWORK){
            no_connection.setVisibility(View.VISIBLE);
            listResult.setVisibility(View.GONE);
        }else {
            no_connection.setVisibility(View.GONE);
            listResult.setVisibility(View.VISIBLE);
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
