package com.aralmoro.android.movlancer.http.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by angelaalmoro on 16/08/2018.
 */

public class MoviesResponse {
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private Movie[] movieList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Movie[] getMovieList() {
        return movieList;
    }

    public void setMovieList(Movie[] movieList) {
        this.movieList = movieList;
    }
}
