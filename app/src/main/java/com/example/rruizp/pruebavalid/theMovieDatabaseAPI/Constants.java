package com.example.rruizp.pruebavalid.theMovieDatabaseAPI;

import java.util.Locale;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class Constants {

   // public static MovieClickDataBase movieClickDataBase;
    public static final String VERSION_URL = "/3/";
    public static final String BASE_URL = "https://api.themoviedb.org"+VERSION_URL;
    public static final String IMG_URL = "https://image.tmdb.org/t/p/w500";
    public static final String LANGUAGES = Locale.getDefault().getLanguage();
    public static int POPULAR_MOVIE_PAGE = 1;
    public static int RECOMMENDATION_MOVIE_PAGE = 1;
    public static int SEARCH_MOVIE_PAGE = 1;
    public static int TOP_RATED_MOVIE_PAGE = 1;
    public static int UPCOMING_MOVIE_PAGE = 1;
    public static int POPULAR_TV_PAGE = 1;
    public static int RECOMMENDATION_TV_PAGE = 1;
    public static int SEARCH_TV_PAGE = 1;
    public static int TOP_RATED_TV_PAGE = 1;
    public static final int MOVIE = 0;
    public static final int TV = 1;
    public static boolean OK_NETWORK = false;
}
