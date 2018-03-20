package com.jcr.popularmovies.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcr.popularmovies.AppPopularMovies;
import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.PopularMoviesPreferences;
import com.jcr.popularmovies.data.network.models.MovieModel;
import com.jcr.popularmovies.ui.OnLoadFromRepositoryCallback;
import com.jcr.popularmovies.ui.detail.DetailActivity;
import com.jcr.popularmovies.ui.settings.SettingsActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterClickHandler,
        OnLoadFromRepositoryCallback<ArrayList<MovieModel>> {

    public static final String MOVIE_DETAILS_KEY = "movie_key";
    private static final String MOVIES_KEY = "movies";

    private RecyclerView mRecyclerGridView;
    private MoviesAdapter mMoviesAdapter;
    private ArrayList<MovieModel> mMovies = new ArrayList<>();

    private GridLayoutManager gridLayoutManager;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private Button mGoTopButton;

    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerGridView = findViewById(R.id.movies_grid);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mGoTopButton = findViewById(R.id.go_top_btn);

        gridLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.grid_columns));
        mRecyclerGridView.setLayoutManager(gridLayoutManager);
        mMoviesAdapter = new MoviesAdapter(this, this);
        mRecyclerGridView.setAdapter(mMoviesAdapter);
        mRecyclerGridView.addOnScrollListener(recyclerViewOnScrollListener);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = gridLayoutManager.getChildCount();
            int totalItemCount = gridLayoutManager.getItemCount();
            int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

            if (!isLoading) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 20) {
                    PopularMoviesPreferences.updateToNextPage(MainActivity.this);
                    loadData();
                }
            }
            int currentPage = PopularMoviesPreferences.getCurrentPage(MainActivity.this);
            mGoTopButton.setVisibility((dy < 0) && (currentPage > 1)? View.VISIBLE : View.GONE);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mMovies.size() == 0) {
            loadData();
        }
    }

    private void loadData() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        isLoading = true;
        AppPopularMovies.getRepository().getMovies(this, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMovies != null) {
            outState.putParcelableArrayList(MOVIES_KEY, mMovies);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(MOVIES_KEY)) {
            mMovies = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
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
    public void onLoad(ArrayList<MovieModel> movies) {
        mLoadingIndicator.setVisibility(View.GONE);
        isLoading = false;
        mMovies.addAll(movies);
        mMoviesAdapter.addMovies(mMovies);
    }

    @Override
    public void onError() {
        mLoadingIndicator.setVisibility(View.GONE);
        isLoading = false;
        mErrorMessageDisplay.setText(R.string.error_message);
    }

    public void goTop(View view){
        mGoTopButton.setVisibility(View.GONE);
        mMovies = new ArrayList<>();
        PopularMoviesPreferences.setCurrentPage(this, 1);
        loadData();
    }
}
