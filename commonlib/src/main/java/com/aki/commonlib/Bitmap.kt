package com.aki.commonlib

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

/*Save returned path from encodeTobase64 in shared pref and next time when you want to get same image use decodeBase64 */
fun encodeTobase64(image: Bitmap): String? {
    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    val imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)
    return imageEncoded
}

fun decodeBase64(input: String?): Bitmap? {
    val decodedByte = Base64.decode(input, 0)
    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.size)
}