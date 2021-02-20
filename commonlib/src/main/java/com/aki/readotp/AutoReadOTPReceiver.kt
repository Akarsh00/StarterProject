package com.aki.readotp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class AutoReadOTPReceiver : BroadcastReceiver() {

    // Get the object of SmsManager

    override fun onReceive(context: Context?, intent: Intent?) {
        // Retrieves a map of extended data from the intent.
        val bundle : Bundle? = intent?.extras
        try {
            if (bundle != null){
                val pdusObj = bundle["pdus"] as Array<Any>?

                for (i in 0 until pdusObj!!.size) {
                    val currentMessage: SmsMessage =
                        SmsMessage.createFromPdu(pdusObj!![i] as ByteArray)
                    val phoneNumber: String = currentMessage.getDisplayOriginatingAddress()
                    val message: String = currentMessage.getDisplayMessageBody().replace("""\\D""".toRegex(),"")

                    //message = message.substring(0, message.length()-1);
                    Log.i(
                        "SmsReceiver",
                        "senderNum: $phoneNumber; message: $message"
                    )
                    val myIntent = Intent("otp")
                    myIntent.putExtra("message", message)
                    myIntent.putExtra("number", phoneNumber)
                    if (context != null) {
                        LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent)
                    }
                    // Show Alert
                } // end for loop


            }//bundle is null
        }catch (e : Exception){
            Log.e("SmsReceiver", "Exception smsReceiver=$e")
        }

    }
}