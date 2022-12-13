package  com.example.weatherapp.core.presentation.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi


import java.util.*


class LocaleHelper private constructor() {

    companion object {

        private const val SELECTED_LANGUAGE = "AppliedLanguage"

        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun onAttach(newBase: Context): Context {
            context = newBase
            return onAttach(Locale.getDefault().language)
        }

       /* fun setLangAr() {
            applicationLiveData.value?.prefadd(enumShared.shLanguage.name, "ar")

            setLocale("ar")
        }*/

        /*fun setLangEn() {
            applicationLiveData.value?.prefadd(enumShared.shLanguage.name, "en")
            val locale = Locale("en")
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            applicationLiveData.value?.getResources()?.updateConfiguration(
                config,
                applicationLiveData.value?.getResources()?.getDisplayMetrics()
            )
            setLocale("en")
        }*/

       /* fun isLangAr(): Boolean {
            val lang = applicationLiveData.value?.prefGetString(enumShared.shLanguage.name, "ar")
            return lang.equals("ar")
        }

        fun Context.setDefaultLang() {
            val lang = prefGetString(enumShared.shLanguage.name, "ar")
            onAttach(this)
            LocaleHelper.setLocale(lang)
        }*/

        fun getLocale(): String? {
            return getSelectedLanguage(Locale.getDefault().language)
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        fun setLocale(language: String?): Context {
            saveSelectedLanguage(language)
            return updateResources(language)
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        private fun onAttach(defaultLanguage: String): Context {
            val language = getSelectedLanguage(defaultLanguage)
            return setLocale(language)
        }

        private fun getSelectedLanguage(defaultLanguage: String): String? {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SELECTED_LANGUAGE, defaultLanguage)
        }

        private fun saveSelectedLanguage(language: String?) {
            PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(SELECTED_LANGUAGE, language).apply()
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        private fun updateResources(language: String?): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val configuration = context.resources.configuration
            configuration.setLocale(locale)

            return context.createConfigurationContext(configuration)
        }
    }
}