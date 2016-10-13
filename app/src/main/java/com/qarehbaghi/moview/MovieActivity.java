package com.qarehbaghi.moview;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.qarehbaghi.moview.adapters.MovieArrayAdapter;
import com.qarehbaghi.moview.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private MovieArrayAdapter movieAdapter;
    private RecyclerView rvItems;
    private SwipeRefreshLayout swipeContainer;
    private AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        client = new AsyncHttpClient();
        rvItems = (RecyclerView) findViewById(R.id.rvMovies);
        rvItems.setHasFixedSize(false);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        rvItems.setItemAnimator(new DefaultItemAnimator());
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.activity_movie);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshMovieList(true);
            }
        });
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(movies);
        rvItems.setAdapter(movieAdapter);
        refreshMovieList(false);
    }

    private void refreshMovieList(boolean isSwipeRefreshing) {
        movieAdapter.clear();
        Toast refreshingToast = null;
        if(isSwipeRefreshing) {
            refreshingToast = Toast.makeText(this, "Refreshing List", Toast.LENGTH_SHORT);
            refreshingToast.show();
        }
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    movieAdapter.addAll(Movie.fromJSONArray(movieJsonResults));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        if(isSwipeRefreshing) {
            if (refreshingToast != null) {
                refreshingToast.cancel();
            }
            Toast.makeText(this, "List Refreshed", Toast.LENGTH_SHORT).show();
            swipeContainer.setRefreshing(false);
        }
    }
}
