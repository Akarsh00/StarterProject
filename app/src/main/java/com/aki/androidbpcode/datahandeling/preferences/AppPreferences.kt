package com.aki.androidbpcode.datahandeling.preferences

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private val NAME = "APP_PREFERENCE"
    private val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val ISLOGIN = Pair("is_login", false)
    private val DEVICE_ID = Pair("deviceId", "")
    private val APPLICATION_NAME = Pair("appname", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var isLogin: Boolean
        get() = preferences.getBoolean(ISLOGIN.first, ISLOGIN.second)
        set(value) = preferences.edit {
            it.putBoolean(ISLOGIN.first, value)
        }

    var appName: String
        get() = preferences.getString(APPLICATION_NAME.first, APPLICATION_NAME.second) ?: ""
        set(value) = preferences.edit {
            it.putString(APPLICATION_NAME.first, value)
        }

    fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    var deviceIdNot: String
        get() = preferences.getString(DEVICE_ID.first, DEVICE_ID.second) ?: ""
        set(value) = preferences.edit {
            it.putString(DEVICE_ID.first, value)
        }

}