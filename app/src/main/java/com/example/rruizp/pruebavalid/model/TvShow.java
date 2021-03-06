package com.example.rruizp.pruebavalid.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rruizp on 29/01/2018.
 */

public class TvShow {

    @SerializedName("poster_path")
    String poster_path;

    @SerializedName("id")
    int id;

    @SerializedName("backdrop_path")
    String backdrop_path;

    @SerializedName("vote_average")
    Double vote_average;

    @SerializedName("overview")
    String overview;

    @SerializedName("first_air_date")
    String first_air_date;

    @SerializedName("name")
    String name;

    int category;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
