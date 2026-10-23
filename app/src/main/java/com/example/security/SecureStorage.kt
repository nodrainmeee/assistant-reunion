package com.example.security

import android.content.Context
import android.content.SharedPreferences

/**
 * Simple secure-ish storage for API keys and preferences.
 * In production, prefer EncryptedSharedPreferences.
 */
class SecureStorage(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("assistant_reunion_secure", Context.MODE_PRIVATE)

    fun saveGeminiApiKey(key: String) {
        prefs.edit().putString(KEY_GEMINI, key).apply()
    }

    fun getGeminiApiKey(): String? = prefs.getString(KEY_GEMINI, null)

    fun clearGeminiApiKey() {
        prefs.edit().remove(KEY_GEMINI).apply()
    }

    companion object {
        private const val KEY_GEMINI = "gemini_api_key"
    }
}
