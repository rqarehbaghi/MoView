package com.qarehbaghi.moview.models;

import com.qarehbaghi.moview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Reza on 2016-10-06.
 */

public class Movie {
    private static final String RELATIVE_PATH_PREFIX_POSTER = "https://image.tmdb.org/t/p/w342/";
    private static final String RELATIVE_PATH_PREFIX_BACKDROP = "https://image.tmdb.org/t/p/w780/";
    private String posterPath;
    private String backdropPath;
    private String originalTitle;
    private String overview;
    private static final int POSTER_WIDTH = 342;
    private static final int BACKDROP_WIDTH = 780;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Movie> results = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                results.add(new Movie(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public static int getBackdropWidth() {
        return BACKDROP_WIDTH;
    }

    public static int getPosterWidth() {
        return POSTER_WIDTH;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return RELATIVE_PATH_PREFIX_POSTER + posterPath;
    }

    public String getBackdropPath() {
        return RELATIVE_PATH_PREFIX_BACKDROP + backdropPath;
    }
}
