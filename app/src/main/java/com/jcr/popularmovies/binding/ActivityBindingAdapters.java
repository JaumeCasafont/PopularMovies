package com.jcr.popularmovies.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.jcr.popularmovies.utilities.ImageUtils;
import com.squareup.picasso.Picasso;

public class ActivityBindingAdapters {
    final Activity activity;

    public ActivityBindingAdapters(Activity activity) {
        this.activity = activity;
    }

    @BindingAdapter("posterPath")
    public void bindPoster(ImageView imageView, String posterPath) {
        Picasso.with(activity).load(ImageUtils.generatePosterUrl(posterPath)).into(imageView);
    }
}
