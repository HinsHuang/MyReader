package com.hins.myreader.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

/**
 * Created by Hins on 2017/8/26.
 */

public class SharedPreferUtil {
    private static final String FAVOURITE_ARTICLE = "favourite_article";

    public static void saveArticle(Context context, String date, String content) {
        SharedPreferences pref = context.getSharedPreferences(FAVOURITE_ARTICLE, Context.MODE_APPEND);
        Log.d("testing", "saveArticle: ");
        pref.edit().putString(date, content).apply();
    }

    public static Map<String, ?> getAllArticles(Context context) {
        SharedPreferences pref = context.getSharedPreferences(FAVOURITE_ARTICLE, Context.MODE_PRIVATE);
        Log.d("testing", "" + pref.getAll().size());
        return pref.getAll();
    }

    public static void removeArticle(Context context, String date) {
        SharedPreferences pref = context.getSharedPreferences(FAVOURITE_ARTICLE, Context.MODE_APPEND);
        pref.edit().remove(date).apply();
    }

    public static boolean containsArticle(Context context, String date) {
        SharedPreferences pref = context.getSharedPreferences(FAVOURITE_ARTICLE, Context.MODE_PRIVATE);
        return pref.contains(date);
    }

}
