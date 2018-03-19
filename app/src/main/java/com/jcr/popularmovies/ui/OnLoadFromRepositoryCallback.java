package com.jcr.popularmovies.ui;

public interface OnLoadFromRepositoryCallback<T> {
    void onLoad(T result);
    void onError();
}
