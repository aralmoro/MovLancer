package com.aralmoro.android.movlancer.movies;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.aralmoro.android.movlancer.R;
import com.aralmoro.android.movlancer.adapter.MovieListAdapter;
import com.aralmoro.android.movlancer.http.model.Movie;
import com.aralmoro.android.movlancer.http.model.MoviesResponse;
import com.aralmoro.android.movlancer.listener.EndlessRecyclerViewScrollListener;
import com.aralmoro.android.movlancer.listener.MovieListListener;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MovieListActivity extends MvpActivity<MovieListView, MovieListPresenter>
        implements MovieListView, SwipeRefreshLayout.OnRefreshListener, MovieListListener {
    @BindView(R.id.movies_rv)
    RecyclerView moviesReyclerview;
    @BindView(R.id.failed_view)
    LinearLayout failedView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private MovieListAdapter movieListAdapter;
    private List<Movie> movieList;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupViews();
        setupListeners();
        displayLoading();
    }

    @NonNull
    @Override
    public MovieListPresenter createPresenter() {
        return new MovieListPresenter(new CompositeDisposable(), new MovieListModel());
    }

    private void setupViews() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        moviesReyclerview.setLayoutManager(layoutManager);

        movieList = new ArrayList<>();
        movieListAdapter = new MovieListAdapter(this, this, movieList);

        moviesReyclerview.setAdapter(movieListAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getPresenter().getMovies(page + 1);
            }
        };
    }

    private void setupListeners() {
        moviesReyclerview.addOnScrollListener(scrollListener);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void displayMovies(MoviesResponse moviesResponse) {
        failedView.setVisibility(View.GONE);
        moviesReyclerview.setVisibility(View.VISIBLE);

        movieList.addAll(Arrays.asList(moviesResponse.getMovieList()));
        movieListAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        movieListAdapter.enableFooter(true);
    }

    @Override
    public void onFailGetMovies() {
        failedView.setVisibility(View.VISIBLE);
        moviesReyclerview.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        movieListAdapter.enableFooter(false);
        movieList.clear();
        getPresenter().getMovies(1);
    }

    public void displayLoading() {
        movieListAdapter.enableFooter(false);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onMovieSelected(Movie movie, View sharedView) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                sharedView, getString(R.string.image_transition));
        Intent intent = MovieDetailsActivity.getInstanceIntent(this, movie);
        startActivity(intent, options.toBundle());
    }
}
