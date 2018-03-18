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
import com.jcr.popularmovies.data.network.models.ReviewModel;
import com.jcr.popularmovies.data.network.models.VideoModel;
import com.jcr.popularmovies.databinding.ActivityDetailBinding;
import com.jcr.popularmovies.ui.OnLoadReviewsFinishedCallback;
import com.jcr.popularmovies.ui.OnLoadVideosFinishedCallback;
import com.jcr.popularmovies.ui.list.MainActivity;

public class DetailActivity extends AppCompatActivity implements OnLoadVideosFinishedCallback,
        VideosAdapter.VideosAdapterClickHandler, OnLoadReviewsFinishedCallback {

    private static final String VIDEOS_KEY = "videos";
    private static final String REVIEWS_KEY = "reviews";

    android.databinding.DataBindingComponent dataBindingComponent = new ActivityDataBindingComponent(this);
    ActivityDetailBinding mDetailBinding;

    private VideosAdapter videosAdapter;
    private ReviewsAdapter reviewsAdapter;

    private MovieModel mMovie;
    private VideoModel[] mVideos;
    private ReviewModel[] mReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail, dataBindingComponent);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(MainActivity.MOVIE_DETAILS_KEY)) {
                mMovie = intentThatStartedThisActivity.getParcelableExtra(MainActivity.MOVIE_DETAILS_KEY);
                mDetailBinding.setMovie(mMovie);

                setupVideosList();
                setupReviewsList();
            }
        }
    }

    private void setupVideosList() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDetailBinding.videosList.setLayoutManager(layoutManager);
        videosAdapter = new VideosAdapter(this, this);
        mDetailBinding.videosList.setAdapter(videosAdapter);
    }

    private void setupReviewsList() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDetailBinding.reviewsList.setLayoutManager(layoutManager);
        reviewsAdapter = new ReviewsAdapter(this);
        mDetailBinding.reviewsList.setAdapter(reviewsAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mVideos != null) {
            outState.putParcelableArray(VIDEOS_KEY, mVideos);
        }
        if (mReviews != null) {
            outState.putParcelableArray(REVIEWS_KEY, mReviews);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(VIDEOS_KEY)) {
            mVideos = (VideoModel[]) savedInstanceState.getParcelableArray(VIDEOS_KEY);
            onVideosLoaded(mVideos);
        }
        if (savedInstanceState.containsKey(REVIEWS_KEY)) {
            mReviews = (ReviewModel[]) savedInstanceState.getParcelableArray(REVIEWS_KEY);
            onReviewsLoaded(mReviews);
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
        if (mVideos == null) {
            mDetailBinding.videosLoadingIndicator.setVisibility(View.VISIBLE);
            AppPopularMovies.getRepository().getVideos(this, mMovie.getId(), this);
        }
        if (mReviews == null) {
            mDetailBinding.reviewsLoadingIndicator.setVisibility(View.VISIBLE);
            AppPopularMovies.getRepository().getReviews(this, mMovie.getId(), this);
        }
    }

    @Override
    public void onVideosLoaded(VideoModel[] videos) {
        mDetailBinding.videosLoadingIndicator.setVisibility(View.GONE);
        if (videos != null && videos.length != 0) {
            showVideos();
            mVideos = videos;
            videosAdapter.addVideos(mVideos);
        }
    }

    @Override
    public void onVideosLoadedError() {
        mDetailBinding.videosLoadingIndicator.setVisibility(View.GONE);
    }

    private void showVideos() {
        mDetailBinding.separatorVideosView.setVisibility(View.VISIBLE);
        mDetailBinding.videosTitleTv.setVisibility(View.VISIBLE);
        mDetailBinding.videosList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReviewsLoaded(ReviewModel[] reviews) {
        mDetailBinding.reviewsLoadingIndicator.setVisibility(View.GONE);
        if (reviews != null && reviews.length != 0) {
            showReviews();
            mReviews = reviews;
            reviewsAdapter.addReviews(mReviews);
        }
    }

    @Override
    public void onReviewsLoadedError() {
        mDetailBinding.reviewsLoadingIndicator.setVisibility(View.GONE);
    }

    private void showReviews() {
        mDetailBinding.separatorReviewsView.setVisibility(View.VISIBLE);
        mDetailBinding.reviewsTitleTv.setVisibility(View.VISIBLE);
        mDetailBinding.reviewsList.setVisibility(View.VISIBLE);
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
