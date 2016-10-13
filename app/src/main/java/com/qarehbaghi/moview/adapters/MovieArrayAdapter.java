package com.qarehbaghi.moview.adapters;

import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qarehbaghi.moview.R;
import com.qarehbaghi.moview.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.qarehbaghi.moview.R.id.tvMovieOverview;
import static com.qarehbaghi.moview.R.id.tvMovieTitle;

/**
 * Created by Reza on 2016-10-08.
 */

public class MovieArrayAdapter extends RecyclerView.Adapter<MovieArrayAdapter.MovieViewHolder> {

    ArrayList<Movie> movies = new ArrayList<>();

    public MovieArrayAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void clear() {
        movies.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Movie> newMovies) {
        movies.addAll(newMovies);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);

        int orientation = holder.getItemView().getContext().getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(holder.getItemView().getContext()).load(movie.getPosterPath()).into(holder.getImage());
        } else {
            Picasso.with(holder.getItemView().getContext()).load(movie.getBackdropPath()).into(holder.getImage());
        }
        holder.getTitle().setText(movie.getOriginalTitle());
        holder.getOverview().setText(movie.getOverview());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView overview;
        private View itemView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.ivMovieImage);
            this.title = (TextView) itemView.findViewById(tvMovieTitle);
            this.overview = (TextView) itemView.findViewById(tvMovieOverview);
            this.itemView = itemView;
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getOverview() {
            return overview;
        }

        public View getItemView() {
            return itemView;
        }
    }
}