package com.example.weatherapp.core.presentation.common

import android.content.Context
import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
class SharedPrefs(private val context: Context) {
    companion object {
        private const val PREF = "MyAppPrefName"
        private const val PREF_TOKEN = "PREF_TOKEN"
        private const val Language = "lang"
        private const val Unit = "unit"
        private const val ID = "id"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun saveId(id: String) {
        put(ID, id)
    }

    fun getId(): String {
        return get(ID, String::class.java)
    }

    fun saveLang(lang: String) {
        put(Language, lang)
    }

    fun getLang(): String {
        return get(Language, String::class.java)
    }

    fun saveUnit(unit: String) {
        put(Unit, unit)
    }

    fun getUnit(): String {
        return get(Unit, String::class.java)
    }

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    fun clear() {
        sharedPref.edit().run {
            remove(PREF_TOKEN)
        }.apply()
    }

    fun getPreferredLocale(): String {
        return sharedPref.getString("preferred_locale", "en")!!
    }

    fun setPreferredLocale(localeCode: String) {
        sharedPref.edit().putString("preferred_locale", localeCode).apply()
    }
}