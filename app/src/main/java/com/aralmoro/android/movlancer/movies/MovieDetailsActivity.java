package com.aralmoro.android.movlancer.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.aralmoro.android.movlancer.R;
import com.aralmoro.android.movlancer.constants.ApiConstants;
import com.aralmoro.android.movlancer.http.model.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by angelaalmoro on 19/08/2018.
 */

public class MovieDetailsActivity extends AppCompatActivity {
    private static final String MOVIE_KEY = "MOVIE_KEY";

    @BindView(R.id.movie_title_tv)
    TextView movieTitleTv;
    @BindView(R.id.movie_release_date_tv)
    TextView movieReleaseDateTv;
    @BindView(R.id.movie_iv)
    ImageView movieIv;
    @BindView(R.id.movie_vote_tv)
    TextView movieVoteTv;
    @BindView(R.id.movie_overview_tv)
    TextView movieOverviewTv;

    private Movie movie;

    public static Intent getInstanceIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_KEY, new Gson().toJson(movie));

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        String movieStr = getIntent().getStringExtra(MOVIE_KEY);
        if (movieStr != null) {
            movie = new Gson().fromJson(movieStr, Movie.class);
            setData();
        }

    }

    private void setData() {
        movieTitleTv.setText(movie.getTitle());
        movieReleaseDateTv.setText(movie.getReleaseDate().split("-")[0]);
        movieVoteTv.setText(getString(R.string.x_rating, Float.toString(movie.getVoteAverage())));
        movieOverviewTv.setText(movie.getOverview());

        Glide.with(this)
                .load(ApiConstants.POSTER_BASE_URL + "w342" + movie.getPosterPath())
                .apply(new RequestOptions().placeholder(R.color.grey)
                        .centerCrop())
                .into(movieIv);
    }
}
