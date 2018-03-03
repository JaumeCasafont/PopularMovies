package com.jcr.popularmovies;


public interface AsyncTaskListener<T> {

    void onTaskStarted();

    void onTaskComplete(T result);
}
