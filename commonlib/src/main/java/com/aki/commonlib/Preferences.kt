package com.aki.commonlib

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.util.Base64

class Preferences protected constructor(private val preferences: SharedPreferences) {

    // =============================================================================================
    val all: Map<String, *>
        get() = preferences.all

    operator fun contains(key: String?): Boolean {
        return preferences.contains(key)
    }

    fun registerOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener?) {
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnSharedPreferenceChangeListener(listener: OnSharedPreferenceChangeListener?) {
        preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    // =============================================================================================
    fun clear() {
        preferences.edit().clear().commit()
    }

    fun remove(key: String?) {
        preferences.edit().remove(key).commit()
    }

    // =============================================================================================
    fun save(key: String?, value: String?) {
        preferences.edit().putString(key, value).apply()
    }

    fun save(key: String?, value: Set<String?>?) {
        preferences.edit().putStringSet(key, value).apply()
    }

    fun save(key: String?, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun save(key: String?, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun save(key: String?, value: Float) {
        preferences.edit().putFloat(key, value).apply()
    }

    fun save(key: String?, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun save(key: String?, value: ByteArray?) {
        save(key, Base64.encodeToString(value, Base64.DEFAULT))
    }
/*
    fun save(key: String?, json: Any?) {
        save(key, Json.json(json))
    }*/

    // =============================================================================================
    fun load(key: String?, defaultValue: String?): String? {
        return preferences.getString(key, defaultValue)
    }

    fun load(
        key: String?,
        defaultValue: Set<String?>?
    ): Set<String>? {
        return preferences.getStringSet(key, defaultValue)
    }

    fun load(key: String?, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun load(key: String?, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    fun load(key: String?, defaultValue: Float): Float {
        return preferences.getFloat(key, defaultValue)
    }

    fun load(key: String?, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun load(key: String?, defaultValue: ByteArray?): ByteArray {
        return if (contains(key)) Base64.decode(
            load(key, ""),
            Base64.DEFAULT
        ) else defaultValue!!
    }
/*
    fun <T> load(key: String?, clazz: Class<T>?, defaultValue: String?): T {
        return Json.`object`(preferences.getString(key, defaultValue), clazz)
    }*/

}