package com.jcr.popularmovies.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.network.MovieModel;
import com.jcr.popularmovies.ui.list.MainActivity;
import com.jcr.popularmovies.utilities.ImageUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private ImageView mPosterImageView;
    private TextView mDateTextView;
    private TextView mVoteAverageTextView;
    private TextView mOverviewTextView;

    private MovieModel mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView = findViewById(R.id.movie_title_tv);
        mPosterImageView = findViewById(R.id.movie_poster_iv);
        mDateTextView = findViewById(R.id.release_date_tv);
        mVoteAverageTextView = findViewById(R.id.vote_average_tv);
        mOverviewTextView = findViewById(R.id.overview_tv);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(MainActivity.MOVIE_DETAILS_KEY)) {
                mMovie = intentThatStartedThisActivity.getParcelableExtra(MainActivity.MOVIE_DETAILS_KEY);
                bindDataToUI();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindDataToUI() {
        mTitleTextView.setText(mMovie.getTitle());
        Picasso.with(this).load(ImageUtils.generatePosterUrl(mMovie.getPosterPath())).into(mPosterImageView);
        mDateTextView.setText(mMovie.getReleaseDate());
        mVoteAverageTextView.setText(String.format(getString(R.string.vote_average), String.valueOf(mMovie.getVoteAverage())));
        mOverviewTextView.setText(mMovie.getOverview());
    }
}
