package com.jcr.popularmovies.ui.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.network.models.ReviewModel;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {

    private final Context mContext;

    private ReviewModel[] mReviews;

    public ReviewsAdapter(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public ReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_item, parent, false);

        return new ReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapterViewHolder holder, int position) {
        holder.authorName.setText(mReviews[position].getAuthor());
        holder.reviewText.setText(mReviews[position].getContent());
        holder.separatorView.setVisibility(position == mReviews.length-1? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        if (null == mReviews) return 0;
        return mReviews.length;
    }

    public void addReviews(ReviewModel[] reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView authorName;
        final TextView reviewText;
        final View separatorView;

        public ReviewsAdapterViewHolder(View itemView) {
            super(itemView);

            authorName = itemView.findViewById(R.id.review_author);
            reviewText = itemView.findViewById(R.id.review_text);
            separatorView = itemView.findViewById(R.id.review_item_separator);
        }
    }
}
