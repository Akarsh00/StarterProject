package com.aki.androidbpcode.utils.genericmethods

import android.widget.Button
import com.aki.androidbpcode.R

fun Button.enableButton() {
    this.background =
        getDrawableFromResource(
            this.context,
            R.drawable.button_bg_enable
        )
}

fun Button.disableButton() {
    this.background =
        getDrawableFromResource(
            this.context,
            R.drawable.button_bg_disabled
        )
}
