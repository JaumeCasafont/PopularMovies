package com.jcr.popularmovies.binding;

import android.app.Activity;
import android.databinding.DataBindingComponent;

public class ActivityDataBindingComponent implements DataBindingComponent {
    private final ActivityBindingAdapters adapter;

    public ActivityDataBindingComponent(Activity activity) {
        this.adapter = new ActivityBindingAdapters(activity);
    }

    @Override
    public ActivityBindingAdapters getActivityBindingAdapters() {
        return adapter;
    }
}
