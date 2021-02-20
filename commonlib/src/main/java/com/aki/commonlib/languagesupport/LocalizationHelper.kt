package com.aki.commonlib.languagesupport

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*

object LocalisationHelper {
    const val LANGUAGE_KEY_ENGLISH = "en"
    const val LANGUAGE_KEY_HINDI = "hi"
    const val LANGUAGE_KEY_ARABIC = "ar"
    private const val LANGUAGE_CODE = "language_key"


    fun setLocale(context: Context): Context =
        updateAppResources(context, getAppLocalePreferences(context))

    @JvmStatic
    fun setNewLocale(
        context: Context,
        localeKey: String
    ): Context {
        saveAppLocalePrefs(context, localeKey)
        return updateAppResources(context, localeKey)
    }

    private fun getAppLocalePreferences(context: Context?): String? {
        val sharedPreference =
            context?.getSharedPreferences("LOCALE_PREFERENCE", Context.MODE_PRIVATE)
        return sharedPreference?.getString(LANGUAGE_CODE, "")
    }

    private fun saveAppLocalePrefs(
        context: Context,
        languageCode: String
    ) {
        val editor = context.getSharedPreferences("LOCALE_PREFERENCE", Context.MODE_PRIVATE).edit()
        editor.putString(LANGUAGE_CODE, languageCode)
        editor.apply()
    }

    private fun updateAppResources(
        context: Context,
        languageCode: String?
    ): Context {
        var baseContext = context
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val res = baseContext.resources
        val config =
            Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale)
            baseContext = baseContext.createConfigurationContext(config)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return baseContext
    }

    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (Build.VERSION.SDK_INT >= 24) config.locales[0] else config.locale
    }
}