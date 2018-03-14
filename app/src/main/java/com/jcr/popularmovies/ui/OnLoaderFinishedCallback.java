package com.jcr.popularmovies.ui;

import android.database.Cursor;

public interface OnLoaderFinishedCallback {
    void onLoaderFinished(Cursor data);
}
