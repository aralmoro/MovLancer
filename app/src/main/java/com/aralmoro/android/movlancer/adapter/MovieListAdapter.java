package com.aralmoro.android.movlancer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aralmoro.android.movlancer.R;
import com.aralmoro.android.movlancer.constants.ApiConstants;
import com.aralmoro.android.movlancer.http.model.Movie;
import com.aralmoro.android.movlancer.listener.MovieListListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by angelaalmoro on 16/08/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOADER = 0;

    private Context context;
    private List<Movie> movieList;
    private MovieListListener movieListListener;
    private boolean isFooterEnabled = true;

    public MovieListAdapter(Context context, MovieListListener movieListListener, List<Movie> movieList) {
        this.context = context;
        this.movieListListener = movieListListener;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_movie, parent, false);
            return new MovieViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            final MovieViewHolder viewHolder = (MovieViewHolder) holder;
            final Movie movie = movieList.get(position);

            viewHolder.movieTitleTv.setText(movie.getTitle());
            viewHolder.movieVoteTv.setText(context.getString(R.string.x_rating,
                    Float.toString(movie.getVoteAverage())));
            Glide.with(context)
                    .load(ApiConstants.POSTER_BASE_URL + "w342" + movie.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.color.grey)
                            .centerCrop())
                    .into(viewHolder.movieIv);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieListListener.onMovieSelected(movie, viewHolder.movieIv);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (movieList == null) return 0;
        return (isFooterEnabled) ? movieList.size() + 1 : movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (isFooterEnabled && position >= movieList.size()) ? VIEW_TYPE_LOADER : VIEW_TYPE_ITEM;
    }

    /***
     * Shows or hides the loading footer
     * */
    public void enableFooter(boolean isEnabled) {
        this.isFooterEnabled = isEnabled;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_iv)
        ImageView movieIv;
        @BindView(R.id.movie_title_tv)
        TextView movieTitleTv;
        @BindView(R.id.movie_vote_tv)
        TextView movieVoteTv;

        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.loading_bar)
        ProgressBar loadingBar;

        public LoadingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
