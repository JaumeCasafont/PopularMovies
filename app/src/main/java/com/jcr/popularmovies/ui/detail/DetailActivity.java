package com.jcr.popularmovies.ui.detail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.jcr.popularmovies.AppPopularMovies;
import com.jcr.popularmovies.R;
import com.jcr.popularmovies.binding.ActivityDataBindingComponent;
import com.jcr.popularmovies.data.network.models.MovieModel;
import com.jcr.popularmovies.data.network.models.VideoModel;
import com.jcr.popularmovies.databinding.ActivityDetailBinding;
import com.jcr.popularmovies.ui.OnLoadVideosFinishedCallback;
import com.jcr.popularmovies.ui.list.MainActivity;

public class DetailActivity extends AppCompatActivity implements OnLoadVideosFinishedCallback,
        VideosAdapter.VideosAdapterClickHandler {

    android.databinding.DataBindingComponent dataBindingComponent = new ActivityDataBindingComponent(this);
    ActivityDetailBinding mDetailBinding;

    private VideosAdapter videosAdapter;

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

                LinearLayoutManager layoutManager =
                        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mDetailBinding.videosList.setLayoutManager(layoutManager);
                videosAdapter = new VideosAdapter(this, this);
                mDetailBinding.videosList.setAdapter(videosAdapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        mDetailBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        AppPopularMovies.getRepository().getVideos(this, mMovie.getId(), this);
    }

    @Override
    public void onLoaderFinished(VideoModel[] videos) {
        mDetailBinding.pbLoadingIndicator.setVisibility(View.GONE);
        videosAdapter.addVideos(videos);
    }

    @Override
    public void onLoaderError() {
        mDetailBinding.pbLoadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onClick(String videoKey) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + videoKey));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
