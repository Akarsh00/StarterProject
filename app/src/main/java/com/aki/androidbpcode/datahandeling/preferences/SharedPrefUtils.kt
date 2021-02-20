package com.aki.androidbpcode.datahandeling.preferences

import android.content.Context
import android.content.SharedPreferences


object SharedPrefsUtils {

    fun saveDataToPreferences(
        context: Context,
        nameOfPref: String,
        map: Map<String, String>
    ) {
        val editor =
            context?.getSharedPreferences(nameOfPref, Context.MODE_PRIVATE)?.edit()
        map.forEach {
            editor?.putString(it.key, it.value)
        }
        editor?.apply()
    }

    fun saveBooleanToPreferences(
        context: Context,
        nameOfPref: String,
        map: Map<String, Boolean>
    ) {
        val editor =
            context?.getSharedPreferences(nameOfPref, Context.MODE_PRIVATE)?.edit()
        map.forEach {
            editor?.putBoolean(it.key, it.value)
        }
        editor?.apply()
    }


    fun getDataFromPreferences(
        context: Context,
        nameOfPref: String,
        nameOfString: String
    ): String? {
        val sharedPreferences =
            context?.getSharedPreferences(nameOfPref, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(nameOfString, "")
    }

    fun getBooleanFromPreferences(
        context: Context,
        nameOfPref: String,
        nameOfString: String
    ): Boolean? {
        val sharedPreferences =
            context?.getSharedPreferences(nameOfPref, Context.MODE_PRIVATE)
        return sharedPreferences?.getBoolean(nameOfString, false)
    }

}