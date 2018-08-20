package com.aralmoro.android.movlancer.movies;

import android.support.annotation.NonNull;

import com.aralmoro.android.movlancer.http.model.MoviesResponse;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by angelaalmoro on 16/08/2018.
 */

public class MovieListPresenter implements MvpPresenter<MovieListView> {
    private CompositeDisposable compositeDisposable;
    private MovieListView view;
    private MovieListModel model;

    public MovieListPresenter(CompositeDisposable compositeDisposable, MovieListModel movieListModel) {
        this.compositeDisposable = compositeDisposable;
        this.model = movieListModel;
    }

    @Override
    public void attachView(@NonNull MovieListView view) {
        this.view = view;
        getMovies(1);
    }

    public void getMovies(int page) {
        compositeDisposable.add(model.getMovies(page)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesResponse>() {
                    @Override
                    public void accept(MoviesResponse moviesResponse) throws Exception {
                        view.displayMovies(moviesResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onFailGetMovies();
                    }
                }));
    }


    @Override
    public void detachView(boolean retainInstance) {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void destroy() {

    }
}
