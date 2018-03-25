package com.jcr.popularmovies.ui.detail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.binding.ActivityDataBindingComponent;
import com.jcr.popularmovies.data.MoviesRepository;
import com.jcr.popularmovies.data.network.models.MovieModel;
import com.jcr.popularmovies.data.network.models.ReviewModel;
import com.jcr.popularmovies.data.network.models.VideoModel;
import com.jcr.popularmovies.databinding.ActivityDetailBinding;
import com.jcr.popularmovies.ui.OnLoadFromRepositoryCallback;
import com.jcr.popularmovies.ui.list.MainActivity;

import static com.jcr.popularmovies.ui.detail.VideosAdapter.YOUTUBE_APP_PATH;
import static com.jcr.popularmovies.ui.detail.VideosAdapter.YOUTUBE_WEB_PATH;

public class DetailActivity extends AppCompatActivity implements VideosAdapter.VideosAdapterClickHandler {

    private static final String VIDEOS_KEY = "videos";
    private static final String REVIEWS_KEY = "reviews";
    
    private MoviesRepository mRepository;

    android.databinding.DataBindingComponent dataBindingComponent = new ActivityDataBindingComponent(this);
    ActivityDetailBinding mDetailBinding;

    private VideosAdapter videosAdapter;
    private ReviewsAdapter reviewsAdapter;

    private MovieModel mMovie;
    private VideoModel[] mVideos;
    private ReviewModel[] mReviews;
    private Boolean isFavorite;

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
        
        mRepository = new MoviesRepository();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        setFavoriteIcon(menu);
        return true;
    }

    private void setFavoriteIcon(final Menu menu) {
        mRepository.isFavorite(this, mMovie.getId(),
                new OnLoadFromRepositoryCallback<Boolean>() {
                    @Override
                    public void onLoad(Boolean result) {
                        isFavorite = result;
                        menu.findItem(R.id.action_favourite).setIcon(
                                isFavorite ? R.drawable.ic_favorite_24dp :
                                        R.drawable.ic_favorite_border_24dp);
                    }

                    @Override
                    public void onError() {
                        menu.findItem(R.id.action_favourite).setVisible(false);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_favourite) {
            onFavoriteClick(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void onFavoriteClick(MenuItem item) {
        switchIcon(item);
        if (isFavorite) {
            mRepository.deleteMovie(this, mMovie.getId());
        } else {
            mRepository.saveMovie(this, mMovie);
        }
        isFavorite = !isFavorite;
    }

    private void switchIcon(MenuItem item) {
        int iconRes = isFavorite ?
                R.drawable.ic_favorite_24dp :
                R.drawable.ic_favorite_border_24dp;
        item.setIcon(iconRes);
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
    protected void onResume() {
        super.onResume();
        loadVideos();
        loadReviews();
    }

    private void loadVideos() {
        if (mVideos == null) {
            mDetailBinding.videosLoadingIndicator.setVisibility(View.VISIBLE);
            mRepository.getVideos(this, mMovie.getId(),
                    new OnLoadFromRepositoryCallback<VideoModel[]>() {
                        @Override
                        public void onLoad(VideoModel[] result) {
                            onVideosLoaded(result);
                        }

                        @Override
                        public void onError() {
                            mDetailBinding.videosLoadingIndicator.setVisibility(View.GONE);
                        }
                    });
        }
    }

    private void loadReviews() {
        if (mReviews == null) {
            mDetailBinding.reviewsLoadingIndicator.setVisibility(View.VISIBLE);
            mRepository.getReviews(this, mMovie.getId(),
                    new OnLoadFromRepositoryCallback<ReviewModel[]>() {
                        @Override
                        public void onLoad(ReviewModel[] result) {
                            onReviewsLoaded(result);
                        }

                        @Override
                        public void onError() {
                            mDetailBinding.reviewsLoadingIndicator.setVisibility(View.GONE);
                        }
                    });
        }
    }

    private void onVideosLoaded(VideoModel[] videos) {
        mDetailBinding.videosLoadingIndicator.setVisibility(View.GONE);
        if (videos != null && videos.length != 0) {
            showVideos();
            mVideos = videos;
            videosAdapter.addVideos(mVideos);
        }
    }

    private void showVideos() {
        mDetailBinding.separatorVideosView.setVisibility(View.VISIBLE);
        mDetailBinding.videosTitleTv.setVisibility(View.VISIBLE);
        mDetailBinding.videosList.setVisibility(View.VISIBLE);
    }

    private void onReviewsLoaded(ReviewModel[] reviews) {
        mDetailBinding.reviewsLoadingIndicator.setVisibility(View.GONE);
        if (reviews != null && reviews.length != 0) {
            showReviews();
            mReviews = reviews;
            reviewsAdapter.addReviews(mReviews);
        }
    }

    private void showReviews() {
        mDetailBinding.separatorReviewsView.setVisibility(View.VISIBLE);
        mDetailBinding.reviewsTitleTv.setVisibility(View.VISIBLE);
        mDetailBinding.reviewsList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String videoKey) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_PATH + videoKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_WEB_PATH + videoKey));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
