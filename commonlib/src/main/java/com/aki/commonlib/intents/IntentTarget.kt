package com.aki.commonlib.intents

import android.content.Context
import android.content.Intent

interface IntentTarget {
    fun context(): Context
    fun startActivity(intent: Intent)
    fun startActivityForResult(intent: Intent, requestCode: Int)
}