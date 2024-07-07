package com.example.firstandroidproject.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.firstandroidproject.Utilities.Score;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SharedPreferencesHelper {
    private static final String PREFS_NAME = "score_prefs";
    private static final String KEY_SCORES = "scores";

    public static void saveScores(Context context, List<Score> scores) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scores);
        editor.putString(KEY_SCORES, json);
        editor.apply();
    }

    public static List<Score> loadScores(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_SCORES, null);
        Type type = new TypeToken<ArrayList<Score>>() {}.getType();
        List<Score> scores = gson.fromJson(json, type);
        return scores != null ? scores : new ArrayList<>();
    }

    public static void addScore(Context context, Score newScore) {
        List<Score> scores = loadScores(context);
        scores.add(newScore);
        Collections.sort(scores, Comparator.comparingInt(Score::getScore).reversed());
        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
        }
        saveScores(context, scores);
    }
}
