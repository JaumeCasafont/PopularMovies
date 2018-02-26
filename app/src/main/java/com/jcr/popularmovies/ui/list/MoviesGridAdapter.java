package com.jcr.popularmovies.ui.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.MovieModel;
import com.jcr.popularmovies.utilities.ImageUtils;
import com.squareup.picasso.Picasso;


public class MoviesGridAdapter extends ArrayAdapter<MovieModel> {
    private static final String LOG_TAG = MoviesGridAdapter.class.getSimpleName();

    public MoviesGridAdapter(Activity context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieModel movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_item, parent, false);
        }

        ImageView moviePoster = convertView.findViewById(R.id.movie_poster_iv);
        Picasso.with(getContext()).load(ImageUtils.generatePosterUrl(movie.getPosterPath())).into(moviePoster);

        return convertView;
    }


}
