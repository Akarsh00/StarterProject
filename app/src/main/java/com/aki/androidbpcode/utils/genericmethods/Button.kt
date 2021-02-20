package com.aki.androidbpcode.utils.genericmethods

import android.widget.Button
import com.aki.androidbpcode.R

fun Button.enableButton() {
    this.isEnabled = true
    this.background = getDrawableFromResource(this.context, R.drawable.button_bg_enable)
}

fun Button.disableButton() {
    this.isEnabled = false
    this.background = getDrawableFromResource(this.context, R.drawable.button_bg_disabled)
}
