package com.example.weatherapp.core.data.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * Created by Mina Shaker on 27-Mar-18.
 */
object PrefsUtil {
    /**
     * Using Builder Pattern to deal with SharedPreferences.
     *
     * @param context The application context.
     * @return Returns the single SharedPreferences instance that can be used to retrieve and modify
     * the preference values.
     */
    @JvmStatic
    fun with(context: Context): Builder {
        return Builder(context)
    }

    class Builder @SuppressLint("CommitPrefEdits") constructor(context: Context) {
        private var gson: Gson? = null
            private get() = if (field == null) {
                Gson().also { field = it }
            } else field
        private val preferences: SharedPreferences
        private val editor: SharedPreferences.Editor

        /**
         * Set an int value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value int: The set of new values for the preference. Passing null for this argument is
         * equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: Int): Builder {
            editor.putInt(key, value)
            return this
        }

        /**
         * Set a long value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value long: The set of new values for the preference. Passing null for this argument
         * is equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: Long): Builder {
            editor.putLong(key, value)
            return this
        }

        /**
         * Set a float value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value float: The set of new values for the preference. Passing null for this argument
         * is equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: Float): Builder {
            editor.putFloat(key, value)
            return this
        }

        /**
         * Set a double value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value double: The set of new values for the preference. Passing null for this argument
         * is equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: Double): Builder {
            editor.putString(key, value.toString())
            return this
        }

        /**
         * Set a boolean value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value boolean: The set of new values for the preference. Passing null for this
         * argument is equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: Boolean): Builder {
            editor.putBoolean(key, value)
            return this
        }

        /**
         * Set a String value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value String: The set of new values for the preference. Passing null for this argument
         * is equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: String?): Builder {
            editor.putString(key, value)
            return this
        }

        /**
         * Set a Set of String value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value Set: The set of new values for the preference. Passing null for this argument is
         * equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: Set<String?>?): Builder {
            editor.putStringSet(key, value)
            return this
        }

        /**
         * Set an Object value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value Object: The new value for the preference. Passing null for this argument is
         * equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: Any?): Builder {
            editor.putString(key, gson!!.toJson(value))
            return this
        }

        /**
         * Set a List value in the preferences, to be written back once apply() is called.
         *
         * @param key String: The name of the preference to modify.
         * @param value List: The new value for the preference. Passing null for this argument is
         * equivalent to calling remove(String) with this key.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun add(key: String?, value: List<*>?): Builder {
            editor.putString(key, gson!!.toJson(value))
            return this
        }

        /**
         * Retrieve an int value from the preferences.
         *
         * @param key String: The name of the preference to retrieve.
         * @param defaultValue int: Value to return if this preference does not exist.
         * @return Returns the preference values if they exist, or defaultValue.
         */
        operator fun get(key: String?, defaultValue: Int): Int {
            return preferences.getInt(key, defaultValue)
        }

        /**
         * Retrieve a long value from the preferences.
         *
         * @param key String: The name of the preference to retrieve.
         * @param defaultValue long: Value to return if this preference does not exist.
         * @return Returns the preference values if they exist, or defaultValue.
         */
        operator fun get(key: String?, defaultValue: Long): Long {
            return preferences.getLong(key, defaultValue)
        }

        /**
         * Retrieve a float value from the preferences.
         *
         * @param key String: The name of the preference to retrieve.
         * @param defaultValue float: Value to return if this preference does not exist.
         * @return Returns the preference values if they exist, or defaultValue.
         */
        operator fun get(key: String?, defaultValue: Float): Float {
            return preferences.getFloat(key, defaultValue)
        }

        /**
         * Retrieve a double value from the preferences.
         *
         * @param key String: The name of the preference to retrieve.
         * @param defaultValue double: Value to return if this preference does not exist.
         * @return Returns the preference values if they exist, or defaultValue.
         */
        operator fun get(key: String?, defaultValue: Double): Double {
            return if (!contains(key)) {
                defaultValue
            } else {
                get(key, defaultValue.toString())!!.toDouble()
            }
        }

        /**
         * Retrieve a boolean value from the preferences.
         *
         * @param key String: The name of the preference to retrieve.
         * @param defaultValue boolean: Value to return if this preference does not exist.
         * @return Returns the preference values if they exist, or defaultValue.
         */
        operator fun get(key: String?, defaultValue: Boolean): Boolean {
            return preferences.getBoolean(key, defaultValue)
        }

        /**
         * Retrieve a String value from the preferences.
         *
         * @param key String: The name of the preference to retrieve.
         * @param defaultValue String: Value to return if this preference does not exist.
         * @return Returns the preference values if they exist, or defaultValue.
         */
        operator fun get(key: String?, defaultValue: String?): String? {
            return preferences.getString(key, defaultValue)
        }

        /**
         * Retrieve a set of String values from the preferences.
         *
         * @param key String: The name of the preference to retrieve.
         * @param defaultValue Set: Value to return if this preference does not exist.
         * @return Returns the preference values if they exist, or defaultValue.
         */
        operator fun get(key: String?, defaultValue: Set<String?>?): Set<String>? {
            return preferences.getStringSet(key, defaultValue)
        }

        /**
         * Retrieve a Class value from the preferences.
         *
         * @param key String: The name of the preference to retrieve.
         * @param classType The class type.
         * @return Returns the preference value if it exists, or null.
         */
        operator fun <T> get(key: String?, classType: Class<T>?): T? {
            return if (preferences.contains(key)) {
                gson!!.fromJson(get(key, ""), classType)
            } else {
                null
            }
        }

        /**
         * Retrieve a list value from the preferences.
         *
         * @param classType The class type.
         * @param key String: The name of the preference to retrieve.
         * @return Returns the preference value if it exists, or null.
         */
        operator fun <T> get(classType: Class<T>?, key: String?): List<T>? {
            return if (preferences.contains(key)) {
                val type = TypeToken.getParameterized(MutableList::class.java, classType).type
                gson!!.fromJson(get(key, ""), type)
            } else {
                null
            }
        }

        /**
         * Checks whether the preferences contains a preference.
         *
         * @param key String: The name of the preference to retrieve.
         * @return Returns true if the preference exists in the preferences, otherwise false.
         */
        operator fun contains(key: String?): Boolean {
            return preferences.contains(key)
        }

        /**
         * Mark that a preference(s) value(s) should be removed,
         * which will be done in the actual preferences once apply() is called.
         *
         * @param keys String: The name(s) of the preference(s) to retrieve.
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun remove(vararg keys: String?): Builder {
            for (key in keys) {
                editor.remove(key)
            }
            return this
        }

        /**
         * Remove all values from the preferences. Once apply is called, the only remaining preferences
         * will be any that you have defined in this editor.
         *
         * @return Returns a reference to the same Preferences object, so you can chain calls together.
         */
        fun clearAll(): Builder {
            editor.clear()
            return this
        }

        /**
         * Commit your preferences changes back from this Editor to the SharedPreferences object it is
         * editing. This atomically performs the requested modifications, replacing whatever is
         * currently in the SharedPreferences.
         */
        fun apply() {
            editor.apply()
        }

        /**
         * Registers a callback to be invoked when a change happens to a preference.
         *
         * @param listener The callback that will run.
         * @see .unregisterOnPrefChangeListener
         */
        fun registerOnPrefChangeListener(listener: OnSharedPreferenceChangeListener?) {
            preferences.registerOnSharedPreferenceChangeListener(listener)
        }

        /**
         * Unregisters a previous callback.
         *
         * @param listener The callback that should be unregistered.
         * @see .registerOnPrefChangeListener
         */
        fun unregisterOnPrefChangeListener(listener: OnSharedPreferenceChangeListener?) {
            preferences.unregisterOnSharedPreferenceChangeListener(listener)
        }

        /**
         * Initiate the single SharedPreferences instance that can be used to retrieve and modify the
         * preference values.
         *
         * @param context The application context.
         */
        init {
            preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            editor = preferences.edit()
        }
    }
}