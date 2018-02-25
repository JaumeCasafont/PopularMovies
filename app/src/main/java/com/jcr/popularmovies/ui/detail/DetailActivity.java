package com.jcr.popularmovies.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.MovieModel;
import com.jcr.popularmovies.ui.list.MainActivity;

public class DetailActivity extends AppCompatActivity {

    private MovieModel mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(MainActivity.MOVIE_DETAILS_KEY)) {
                mMovie = intentThatStartedThisActivity.getParcelableExtra(MainActivity.MOVIE_DETAILS_KEY);

            }
        }
    }
}
