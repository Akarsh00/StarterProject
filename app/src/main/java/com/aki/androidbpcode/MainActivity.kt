package com.aki.androidbpcode


import android.os.Bundle
import androidx.activity.viewModels
import com.aki.androidbpcode.base.BaseActivity
import com.aki.androidbpcode.customdialog.CustomDialog
import com.aki.androidbpcode.viewmodel.TestViewModel

class MainActivity : BaseActivity() {

    val viewModel: TestViewModel by viewModels()
    override val view: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    fun openCustomDialog() {
        CustomDialog.newInstance()
            .show(
                this.supportFragmentManager,
                CustomDialog.TAG
            )
    }


}

