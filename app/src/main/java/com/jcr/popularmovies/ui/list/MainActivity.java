package com.jcr.popularmovies.ui.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcr.popularmovies.ui.detail.DetailActivity;
import com.jcr.popularmovies.R;
import com.jcr.popularmovies.data.MovieModel;
import com.jcr.popularmovies.ui.settings.SettingsActivity;
import com.jcr.popularmovies.utilities.JsonParserUtils;
import com.jcr.popularmovies.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String MOVIE_DETAILS_KEY = "movie_key";

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String MOVIES_KEY = "movies";
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    private GridView mGridView;
    private MoviesGridAdapter mMoviesAdapter;
    private MovieModel[] mMovies;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = findViewById(R.id.movies_grid);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mMoviesAdapter = new MoviesGridAdapter(MainActivity.this);
        mGridView.setAdapter(mMoviesAdapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMovies == null || PREFERENCES_HAVE_BEEN_UPDATED) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
            String keyForSortCriteria = getString(R.string.pref_sort_criteria_key);
            String defaultSortCriteria = getString(R.string.pref_sort_criteria_rated_value);

            String sortCriteria = prefs.getString(keyForSortCriteria, defaultSortCriteria);
            new FetchMoviesTask().execute(sortCriteria);
            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(MOVIES_KEY, mMovies);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMovies = (MovieModel[]) savedInstanceState.getParcelableArray(MOVIES_KEY);
        mMoviesAdapter.addAll(mMovies);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MovieModel movieModel = mMovies[position];
        Intent intentToStartDetailActivity = new Intent(this, DetailActivity.class);
        intentToStartDetailActivity.putExtra(MOVIE_DETAILS_KEY, movieModel);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our visualizer_menu layout to this menu */
        inflater.inflate(R.menu.sort_criteria_menu, menu);
        /* Return true so that the visualizer_menu is displayed in the Toolbar */
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


    public class FetchMoviesTask extends AsyncTask<String, Void, MovieModel[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieModel[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL moviesRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonMoviesResponse = NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl);

                return JsonParserUtils.parseJson(jsonMoviesResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieModel[] movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                mMovies = movies;
                mMoviesAdapter.addAll(mMovies);
                mErrorMessageDisplay.setVisibility(View.INVISIBLE);
            } else {
                mErrorMessageDisplay.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }
}
