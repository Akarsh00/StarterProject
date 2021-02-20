package com.aki.androidbpcode.commonactivity.otpactivity

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.aki.androidbpcode.R
import kotlinx.android.synthetic.main.activity_o_t_p.*


class
OTPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)
        addTextWatcher(otp_mEt1)
        addTextWatcher(otp_mEt2)
        addTextWatcher(otp_mEt3)
        addTextWatcher(otp_mEt4)

        otp_mEt1.setOnKeyListener(
            GenericKeyEvent(otp_mEt1,null))
        otp_mEt2.setOnKeyListener(
            GenericKeyEvent(otp_mEt2,otp_mEt1))
        otp_mEt3.setOnKeyListener(
            GenericKeyEvent(otp_mEt3,otp_mEt2))
        otp_mEt4.setOnKeyListener(
            GenericKeyEvent(otp_mEt4,otp_mEt3))
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    private fun addTextWatcher(one: EditText) {
        one.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {


                otp_mEt4.setTextColor(
                    ResourcesCompat.getColor(
                        applicationContext.resources,
                        R.color.white,
                        null
                    )
                )


                when (one.id) {
                    R.id.otp_mEt1 -> if (one.length() == 1) {
                        otp_mEt1.setTextColor(
                            ResourcesCompat.getColor(
                                applicationContext.resources,
                                R.color.white,
                                null
                            )
                        )
                        otp_mEt2.requestFocus()
                        otp_mEt1.background = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.otp_bg,
                            null
                        )
                    } else {
                        otp_mEt1.setBackground(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.otp_inactivty_bg,
                                null
                            )
                        )
                    }
                    R.id.otp_mEt2 -> if (one.length() == 1) {
                        otp_mEt2.setTextColor(
                            ResourcesCompat.getColor(
                                applicationContext.resources,
                                R.color.white,
                                null
                            )
                        )
                        otp_mEt3.requestFocus()
                        otp_mEt2.setBackground(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.otp_bg,
                                null
                            )
                        )
                    } else if (one.length() == 0) {
                        otp_mEt2.background = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.otp_inactivty_bg,
                            null
                        )
                        otp_mEt1.requestFocus()
                    }
                    R.id.otp_mEt3 -> if (one.length() == 1) {
                        otp_mEt3.setTextColor(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.white,
                                null
                            )
                        )
                        otp_mEt4.requestFocus()
                        otp_mEt3.setBackground(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.otp_bg,
                                null
                            )
                        )
                    } else if (one.length() == 0) {
                        otp_mEt3.background = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.otp_inactivty_bg,
                            null
                        )
                        otp_mEt2.requestFocus()
                    }
                    R.id.otp_mEt4 ->
                        if (one.length() == 1) {
                            otp_mEt4.setBackground(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.otp_bg,
                                    null
                                )
                            )
                        } else if (one.length() == 0) {
                            otp_mEt4.background = ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.otp_inactivty_bg,
                                null
                            )
                            otp_mEt3.requestFocus()
                        }

                }
            }
        })
    }

    fun wrongOtp() {
        otp_mEt1.setTextColor(
            ResourcesCompat.getColor(
                applicationContext.resources,
                R.color.g_text_color,
                null
            )
        )
        otp_mEt2.setTextColor(
            ResourcesCompat.getColor(
                applicationContext.resources,
                R.color.g_text_color,
                null
            )
        )
        otp_mEt3.setTextColor(
            ResourcesCompat.getColor(
                applicationContext.resources,
                R.color.g_text_color,
                null
            )
        )
        otp_mEt4.setTextColor(
            ResourcesCompat.getColor(
                applicationContext.resources,
                R.color.g_text_color,
                null
            )
        )

        otp_mEt1.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.otp_error_view,
            null
        )
        otp_mEt2.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.otp_error_view,
            null
        )
        otp_mEt3.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.otp_error_view,
            null
        )
        otp_mEt4.background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.otp_error_view,
            null
        )
    }
    class GenericKeyEvent internal constructor(private val currentView: EditText, private val previousView: EditText?) : View.OnKeyListener{
        override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if(event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL/* && currentView.id != R.id.et_otp_1*/ && currentView.text.isEmpty()) {
                //If current is empty then previous EditText's number will also be deleted
                previousView?.text = null
                previousView?.requestFocus()
                return true
            }
            return false
        }


    }
}