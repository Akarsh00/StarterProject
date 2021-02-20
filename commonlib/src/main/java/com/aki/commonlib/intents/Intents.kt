package com.aki.commonlib.intents

import android.annotation.TargetApi
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.provider.MediaStore
import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.aki.commonlib.BuildConfig
import java.util.*

object Intents {




    fun custom(intent: Intent): IntentOperation {
        return IntentOperation(intent)
    }

    fun custom(intent: Intent, requestCode: Int): IntentOperation {
        return IntentOperation(
            intent,
            requestCode
        )
    }

    fun shareLink(url: String?): IntentOperation {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, url)
        intent.type = "text/url"
        return IntentOperation(intent)
    }

    fun shareLink(url: String?, type: String?): IntentOperation {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, url)
        intent.type = type
        return IntentOperation(intent)
    }

    fun sendEmail(
        address: String?,
        subject: String?,
        text: String?
    ): IntentOperation {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse(String.format("mailto:%s", address))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        return IntentOperation(intent)
    }

    fun dialNumber(phoneNumber: String?): IntentOperation {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse(String.format("tel:%s", phoneNumber))
        return IntentOperation(intent)
    }

    fun callNumber(phoneNumber: String?): IntentOperation {
        val intent = Intent(Intent.ACTION_CALL)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse(String.format("tel:%s", phoneNumber))
        return IntentOperation(intent)
    }

    fun takePicture(requestCode: Int): IntentOperation {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        return IntentOperation(intent, requestCode)
    }

    fun takePicture(context: Context, uri: Uri?): IntentOperation {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        if (Build.VERSION.SDK_INT < VERSION_CODES.LOLLIPOP) {
            val clip = ClipData.newUri(context.contentResolver, "picture", uri)
            intent.clipData = clip
        }
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        return IntentOperation(intent)
    }


    fun selectFromGallery(type: String?): IntentOperation {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = type
        return IntentOperation(intent)
    }


    @TargetApi(VERSION_CODES.KITKAT)
    fun pickImageFromGallery(requestCode: Int): IntentOperation {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        return IntentOperation(intent, requestCode)
    }

    fun openUri(uri: Uri?): IntentOperation {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        return IntentOperation(intent)
    }

    fun openFile(uri: Uri?, type: String?): IntentOperation {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, type)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        return IntentOperation(intent)
    }

    fun openFile(uri: Uri?): IntentOperation {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        return IntentOperation(intent)
    }

    fun shareFile(uri: Uri?, type: String?): IntentOperation {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.type = type
        return IntentOperation(intent)
    }

    private fun shareFiles(uris: ArrayList<Uri>): IntentOperation {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND_MULTIPLE
        intent.type = "*/*"
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)
        return IntentOperation(intent)
    }

    fun address(address: String?): IntentOperation {
        val uri = Uri.parse(
            String.format(
                "geo:0,0?q=%s",
                Uri.encode(address)
            )
        )
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return IntentOperation(intent)
    }

    fun coordinates(latitude: Double, longitude: Double): IntentOperation {
        val uri = Uri.parse(
            String.format(
                "geo:%s,%s?q=%s,%s",
                latitude,
                longitude,
                latitude,
                longitude
            )
        )
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return IntentOperation(intent)
    }

    fun openAppPlayStore(packageName: String): IntentOperation {
        val uri = String.format("market://details?id=%s", packageName)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        return IntentOperation(intent)
    }

    fun openAppPlayStoreOnWeb(
        context: Context?,
        packageName: String?,
        @ColorRes color: Int
    ) {
        val uri =
            String.format("http://play.google.com/store/apps/details?id=%s", packageName)
        openWebPage(
            context,
            uri,
            color
        )
    }

    fun openWebPage(
        context: Context?,
        url: String?,
        @ColorRes color: Int
    ) {
        val params = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(context!!, color))
            .build()
        val builder = CustomTabsIntent.Builder()
        builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)
        builder.setDefaultColorSchemeParams(params)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }


    fun shareApp(context: Context, message: String = "Hey check out my app at:",packageName: String): IntentOperation {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "$message https://play.google.com/store/apps/details?id=" + packageName
        )
        intent.type = "text/plain"
        return IntentOperation(intent)
    }


    fun sendWhatsAppMessage(mobNumber: String, message: String = ""): IntentOperation {
        val url = "https://api.whatsapp.com/send?phone=$this"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.apply {
            data = Uri.parse(url)
        }
        if (message != "") {
            intent.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
        }
        return IntentOperation(intent)
    }


    fun sendMessage(number: String, message: String = ""): IntentOperation {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("smsto:")
        intent.type = "vnd.android-dir/mms-sms"
        intent.putExtra("address", number)
        if (message != "") {
            intent.putExtra("sms_body", "your desired message");
        }
        return IntentOperation(intent)
    }

}