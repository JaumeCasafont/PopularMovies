package com.jcr.popularmovies.ui;


import com.jcr.popularmovies.data.network.models.ReviewModel;

public interface OnLoadReviewsFinishedCallback {
    void onReviewsLoaded(ReviewModel[] movies);
    void onReviewsLoadedError();
}
