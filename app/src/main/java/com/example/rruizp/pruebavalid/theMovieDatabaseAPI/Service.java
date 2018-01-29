package com.example.rruizp.pruebavalid.theMovieDatabaseAPI;

import com.example.rruizp.pruebavalid.model.MovieResponse;
import com.example.rruizp.pruebavalid.model.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rruizp on 29/01/2018.
 */

public interface Service {

    @GET("movie/{sub_type}")
    Call<MovieResponse> getMovie(@Path("sub_type")String sub_type, @Query("api_key")String api_key, @Query("language")String language, @Query("page")int page);

    @GET("tv/{sub_type}")
    Call<TvShowResponse> getTvShow(@Path("sub_type")String sub_type, @Query("api_key")String api_key, @Query("language")String language, @Query("page")int page);

    @GET("movie/{movie_id}/recommendations")
    Call<MovieResponse> getMovieRecommendations(@Path("movie_id")int movie_id,@Query("api_key")String api_key, @Query("language")String language,@Query("page")int page);

    @GET("tv/{tv_id}/recommendations")
    Call<TvShowResponse> getTvShowRecommendations(@Path("tv_id")int tv_id, @Query("api_key")String api_key, @Query("language")String language, @Query("page")int page);

    @GET("search/movie")
    Call<MovieResponse> getMovieSearch(@Query("query")String query,@Query("api_key")String api_key, @Query("language")String language,@Query("page")int page);

    @GET("search/tv")
    Call<TvShowResponse> getTvShowSearch(@Query("query")String query, @Query("api_key")String api_key, @Query("language")String language, @Query("page")int page);

}
