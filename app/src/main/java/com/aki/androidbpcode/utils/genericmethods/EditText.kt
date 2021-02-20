package com.aki.androidbpcode.utils.genericmethods

import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.Selection
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.text.HtmlCompat


/*  Add prefix as +91 in case of EditText mobile number  */

fun addEditTextPrefix(etMobileNumber: EditText, mobileNoPrefix: String = "+91") {
    var perfix = mobileNoPrefix
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        etMobileNumber.text =
            Html.fromHtml(perfix, HtmlCompat.FROM_HTML_MODE_LEGACY) as Editable?
    } else {
        etMobileNumber.text = Html.fromHtml(perfix) as Editable?
    }


    etMobileNumber.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (!s.toString().startsWith(perfix)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    etMobileNumber.text =
                        Html.fromHtml(perfix, HtmlCompat.FROM_HTML_MODE_LEGACY) as Editable?
                } else {
                    etMobileNumber.text = Html.fromHtml(perfix) as Editable?
                }
                Selection.setSelection(etMobileNumber.text, etMobileNumber.text.length)

            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    })


}
