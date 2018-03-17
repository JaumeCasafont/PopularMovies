package com.jcr.popularmovies.ui.detail;

import android.content.Intent;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.binding.ActivityDataBindingComponent;
import com.jcr.popularmovies.data.network.MovieModel;
import com.jcr.popularmovies.databinding.ActivityDetailBinding;
import com.jcr.popularmovies.ui.list.MainActivity;
import com.jcr.popularmovies.utilities.ImageUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    android.databinding.DataBindingComponent dataBindingComponent = new ActivityDataBindingComponent(this);
    ActivityDetailBinding mDetailBinding;

    private MovieModel mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail, dataBindingComponent);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(MainActivity.MOVIE_DETAILS_KEY)) {
                mMovie = intentThatStartedThisActivity.getParcelableExtra(MainActivity.MOVIE_DETAILS_KEY);
                mDetailBinding.setMovie(mMovie);
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
}
