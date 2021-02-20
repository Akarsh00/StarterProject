package com.aki.androidbpcode.utils.genericmethods

import android.content.Context
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.aki.androidbpcode.R
import com.bumptech.glide.Glide


fun getDrawableFromResource(context: Context, resourceId: Int) =
    ResourcesCompat.getDrawable(context.resources, resourceId, null)


fun String.loadImage(imageView: ImageView) =
    Glide
    .with(imageView.context)
    .load(this)
    .centerCrop()
    .placeholder(R.drawable.otp_inactivty_bg)
    .into(imageView);

