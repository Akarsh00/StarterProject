package com.aki.androidbpcode


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.aki.androidbpcode.base.BaseActivity
import com.aki.androidbpcode.customdialog.CustomDialog
import com.aki.androidbpcode.viewmodel.TestViewModel
import com.aki.commonlib.sharedpreferences.Preferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    val viewModel: TestViewModel by viewModels()
    override val view: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        helloWorld.setOnClickListener {
            viewModel.getAllCountries()

//            showToast("Hello World..")
        }
        viewModel.getAllCountries()
        viewModel._data.observe(this, Observer {
            showToast(it?.toString()?:"")
        })

//        openCustomDialog()

        val prefs = Preferences(
            this?.getSharedPreferences(
                "NOP",
                Context.MODE_PRIVATE
            )
        )

        prefs.save("key1", "key1")
        prefs.save("key2", 1)
        prefs.save("key3", 1.0F)
        Log.d("preferences", "onCreate: " + prefs.all)
    }


    fun openCustomDialog() {
        CustomDialog.newInstance()
            .show(
                this.supportFragmentManager,
                CustomDialog.TAG
            )
    }


}

