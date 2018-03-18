package com.jcr.popularmovies.ui;

import com.jcr.popularmovies.data.network.models.VideoModel;

public interface OnLoadVideosFinishedCallback {
    void onLoaderFinished(VideoModel[] movies);
    void onLoaderError();
}
