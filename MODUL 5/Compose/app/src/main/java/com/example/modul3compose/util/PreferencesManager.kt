package com.example.modul3compose.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("movie_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LANGUAGE = "selected_language"
        const val CACHE_DURATION_MS = 60 * 60 * 1000L
    }

    var selectedLanguage: String
        get() = prefs.getString(KEY_LANGUAGE, "id-ID") ?: "id-ID"
        set(value) = prefs.edit().putString(KEY_LANGUAGE, value).apply()

}