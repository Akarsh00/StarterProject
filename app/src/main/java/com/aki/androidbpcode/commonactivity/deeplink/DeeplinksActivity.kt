package com.aki.androidbpcode.commonactivity.deeplink

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aki.androidbpcode.MainActivity
import com.aki.androidbpcode.commonactivity.otpactivity.OTPActivity
import com.aki.commonlib.intents.Intents

class DeeplinksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.data

        if (intent.data?.getQueryParameter("type") == "home") {
            Intents.custom(Intent(this, MainActivity::class.java)).send(this)
            finish()
        } else if (intent.data?.getQueryParameter("type") == "otp") {
            Intents.custom(Intent(this, OTPActivity::class.java)).send(this)
            finish()
        }
        else{
             openSplashActivity()
        }


    }

    private fun openSplashActivity() {

    }
}