package com.example.rruizp.pruebavalid.pruebaValidDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.rruizp.pruebavalid.model.Movie;
import com.example.rruizp.pruebavalid.model.TvShow;

import java.util.ArrayList;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class PruebaValidDataBase extends SQLiteOpenHelper{

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "pruebaValid.db";

    private static final String movie = "CREATE TABLE IF NOT EXISTS movie(" +
            "id integer primary key, title text not null," +
            " release_date text not null, poster_path text not null, backdrop_path text not null," +
            " overview text not null,vote_average double not null,category integer not null);";

    private static final String tvshow = "CREATE TABLE IF NOT EXISTS tvshow(" +
            "id integer primary key, name text not null," +
            " first_air_date text not null, poster_path text not null, backdrop_path text not null," +
            " overview text not null,vote_average double not null,category integer not null);";


    public PruebaValidDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(movie);
        db.execSQL(tvshow);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertMovie(Movie movie){
        long insertedId;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",movie.getId());
        contentValues.put("title",movie.getTitle());
        contentValues.put("release_date",movie.getRelease_date());
        contentValues.put("poster_path",movie.getPoster_path());
        contentValues.put("backdrop_path",movie.getBackdrop_path());
        contentValues.put("overview",movie.getOverview());
        contentValues.put("vote_average",movie.getVote_average());
        contentValues.put("category",movie.getCategory());
        insertedId = sqLiteDatabase.insert("movie",null,contentValues);
        sqLiteDatabase.close();
        return insertedId;
    }

    public long insertTvShow(TvShow tvShow){
        long insertedId;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",tvShow.getId());
        contentValues.put("name",tvShow.getName());
        contentValues.put("first_air_date",tvShow.getFirst_air_date());
        contentValues.put("poster_path",tvShow.getPoster_path());
        contentValues.put("backdrop_path",tvShow.getBackdrop_path());
        contentValues.put("overview",tvShow.getOverview());
        contentValues.put("vote_average",tvShow.getVote_average());
        contentValues.put("category",tvShow.getCategory());
        insertedId = sqLiteDatabase.insert("tvshow",null,contentValues);
        sqLiteDatabase.close();
        return insertedId;
    }

    public ArrayList<Movie> allMovies(){
        ArrayList<Movie> movies = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("movie",null,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            try {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(0));
                movie.setTitle(cursor.getString(1));
                movie.setRelease_date(cursor.getString(2));
                movie.setPoster_path(cursor.getString(3));
                movie.setBackdrop_path(cursor.getString(4));
                movie.setOverview(cursor.getString(5));
                movie.setVote_average(cursor.getDouble(6));
                movie.setCategory(cursor.getInt(7));
                movies.add(movie);
                while (cursor.moveToNext()){
                    movie = new Movie();
                    movie.setId(cursor.getInt(0));
                    movie.setTitle(cursor.getString(1));
                    movie.setRelease_date(cursor.getString(2));
                    movie.setPoster_path(cursor.getString(3));
                    movie.setBackdrop_path(cursor.getString(4));
                    movie.setOverview(cursor.getString(5));
                    movie.setVote_average(cursor.getDouble(6));
                    movie.setCategory(cursor.getInt(7));
                    movies.add(movie);
                }
            }catch (Exception e){
                return null;
            }
        }
        sqLiteDatabase.close();
        return movies;
    }
    public ArrayList<TvShow> allTvShows(){
        ArrayList<TvShow> tvShows = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("tvshow",null,null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            try {
                TvShow tvShow = new TvShow();
                tvShow.setId(cursor.getInt(0));
                tvShow.setName(cursor.getString(1));
                tvShow.setFirst_air_date(cursor.getString(2));
                tvShow.setPoster_path(cursor.getString(3));
                tvShow.setBackdrop_path(cursor.getString(4));
                tvShow.setOverview(cursor.getString(5));
                tvShow.setVote_average(cursor.getDouble(6));
                tvShow.setCategory(cursor.getInt(7));
                tvShows.add(tvShow);
                while (cursor.moveToNext()){
                    tvShow = new TvShow();
                    tvShow.setId(cursor.getInt(0));
                    tvShow.setName(cursor.getString(1));
                    tvShow.setFirst_air_date(cursor.getString(2));
                    tvShow.setPoster_path(cursor.getString(3));
                    tvShow.setBackdrop_path(cursor.getString(4));
                    tvShow.setOverview(cursor.getString(5));
                    tvShow.setVote_average(cursor.getDouble(6));
                    tvShow.setCategory(cursor.getInt(7));
                    tvShows.add(tvShow);
                }
            }catch (Exception e){
                return null;
            }
        }
        sqLiteDatabase.close();
        return tvShows;
    }
}
