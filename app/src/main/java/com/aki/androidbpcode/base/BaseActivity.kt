package com.aki.androidbpcode.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aki.androidbpcode.R
import com.aki.commonlib.languagesupport.LocalisationHelper
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {
    lateinit var loading: AlertDialog
    private var isCancelable = false

    open fun getThemResId(): Int = -1

    @LayoutRes
    open fun getLayoutIdLoading(): Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view)
        initDialog()
    }

    override fun attachBaseContext(base: Context) {
        LocalisationHelper.getLocale(base.resources)
        val context= LocalisationHelper.setLocale(base)
        super.attachBaseContext(context)
    }

    fun showSnackBar(
        view: View,
        message: String,
        duration: Int
    ) =
        Snackbar.make(rootView, message, duration).show()

    fun showSnackBar(message: String, duration: Int) =
        Snackbar.make(rootView, message, duration).show()


    fun showToast(message: String) =
        Toast.makeText(rootView.context, message, Toast.LENGTH_SHORT).show()


    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }


    /**
     * Init dialog loading
     */
    private fun initDialog() {
        val builder: AlertDialog.Builder = if (getThemResId() != -1)
            AlertDialog.Builder(this, getThemResId()) else AlertDialog.Builder(this)

        builder.setCancelable(isCancelable)
        builder.setView(if (getLayoutIdLoading() == -1) R.layout.layout_loading_dialog_default else getLayoutIdLoading())
        loading = builder.create()
    }

    /**
     * Show dialog loading
     */
    open fun showProgressDialog() {
        runOnUiThread {
            if (!loading.isShowing) {
                loading.show()
            }
        }
    }
    open fun hideProgessDialog() {
        runOnUiThread {
            if (loading.isShowing) {
                loading.hide()
            }
        }
    }


    private val rootView: View
        get() =
            findViewById<View>(android.R.id.content).rootView

    protected abstract val view: Int


}
