package com.jcr.popularmovies.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jcr.popularmovies.R;

public final class PopularMoviesPreferences {

    public static void setSortCriteria(Context context, String criteria) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(context.getString(R.string.pref_sort_criteria_key), criteria);
        editor.apply();
    }

    public static String getSortCriteria(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(context.getString(R.string.pref_sort_criteria_key),
                context.getString(R.string.pref_sort_criteria_rated_value));
    }

    public static void setCurrentPage(Context context, int currentPage) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt(context.getString(R.string.pref_current_page_key), currentPage);
        editor.apply();
    }

    public static int updateToNextPage(Context context) {
        int nextPage = getCurrentPage(context) + 1;
        setCurrentPage(context, nextPage);
        return nextPage;
    }

    public static int getCurrentPage(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(context.getString(R.string.pref_current_page_key), context.getResources().getInteger(R.integer.default_current_page));
    }
}
