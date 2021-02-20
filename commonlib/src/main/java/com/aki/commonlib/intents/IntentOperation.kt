package com.aki.commonlib.intents

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent

class IntentOperation @JvmOverloads constructor(
    private val intent: Intent,
    private val requestCode: Int? = null
) {
    fun requestCode(requestCode: Int): IntentOperation {
        return IntentOperation(
            intent,
            requestCode
        )
    }

    fun send(activity: Activity) {
        send(object : IntentTarget {
            override fun context(): Context {
                return activity
            }

            override fun startActivity(intent: Intent) {
                activity.startActivity(intent)
            }

            override fun startActivityForResult(
                intent: Intent,
                requestCode: Int
            ) {
                activity.startActivityForResult(intent, requestCode)
            }
        })
    }

    /*
  fun send(fragment: Fragment) {
         send(object : IntentTarget {
             override fun context(): Context {
                 return fragment.requireContext()
             }

             override fun startActivity(intent: Intent) {
                 fragment.startActivity(intent)
             }

             override fun startActivityForResult(
                 intent: Intent,
                 requestCode: Int
             ) {
                 fragment.startActivityForResult(intent, requestCode)
             }
         })
     }*/

    fun send(target: IntentTarget) {
        if (requestCode == null) {
            startActivity(target, intent)
        } else {
            startActivityForResult(target, intent, requestCode)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startActivity(target: IntentTarget, intent: Intent) {
        target.startActivity(intent)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun startActivityForResult(
        target: IntentTarget,
        intent: Intent,
        requestCode: Int
    ) {
        target.startActivityForResult(intent, requestCode)
    }

}