package com.jcr.popularmovies.ui.list;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcr.popularmovies.data.MoviesDBLoaderCallbacks;
import com.jcr.popularmovies.data.MoviesRepository;
import com.jcr.popularmovies.data.database.MoviesContract;
import com.jcr.popularmovies.data.network.MovieModel;
import com.jcr.popularmovies.data.sync.MoviesSyncUtils;
import com.jcr.popularmovies.ui.OnLoaderFinishedCallback;
import com.jcr.popularmovies.ui.detail.DetailActivity;
import com.jcr.popularmovies.R;
import com.jcr.popularmovies.ui.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterClickHandler,
        OnLoaderFinishedCallback{

    public static final String MOVIE_DETAILS_KEY = "movie_key";
    private static final String MOVIES_KEY = "movies";

    private RecyclerView mRecyclerGridView;
    private MoviesAdapter mMoviesAdapter;
    private MovieModel[] mMovies;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerGridView = findViewById(R.id.movies_grid);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mRecyclerGridView.setLayoutManager( new GridLayoutManager(this, 2));
        mMoviesAdapter = new MoviesAdapter(this, this);
        mRecyclerGridView.setAdapter(mMoviesAdapter);

        //MoviesSyncUtils.initialize(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMovies == null) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            MoviesRepository.getMovies(this, this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMovies != null) {
            outState.putParcelableArray(MOVIES_KEY, mMovies);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(MOVIES_KEY)) {
            mMovies = (MovieModel[]) savedInstanceState.getParcelableArray(MOVIES_KEY);
            mMoviesAdapter.addMovies(mMovies);
        }
    }

    @Override
    public void onClick(MovieModel movie) {
        Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
        intentToStartDetailActivity.putExtra(MOVIE_DETAILS_KEY, movie);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_criteria_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoaderFinished(MovieModel[] movies) {
        mLoadingIndicator.setVisibility(View.GONE);
        mMovies = movies;
        mMoviesAdapter.addMovies(mMovies);
    }

    @Override
    public void onLoaderError(Throwable t) {
        mLoadingIndicator.setVisibility(View.GONE);
        mErrorMessageDisplay.setText(R.string.error_message);
    }
}
