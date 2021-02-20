package com.aki.androidbpcode

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.aki.androidbpcode.base.BaseFragment
import com.aki.commonlib.encodeTobase64
import com.aki.commonlib.intents.IntentTarget
import com.aki.commonlib.intents.Intents
import com.aki.commonlib.languagesupport.LocalisationHelper
import com.aki.commonlib.permission.PermissionManager
import com.aki.commonlib.permission.PermissionUtils
import com.aki.commonlib.permission.enums.PermissionEnum
import com.aki.commonlib.permission.interfaces.FullCallback
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.ArrayList

class IntentFragment : BaseFragment(), FullCallback, IntentTarget {
    private val REQUEST_PERMISSIONS = 1
    private val PICK_IMAGE_REQUEST_CODE = 101
    private val TAKE_IMAGE_REQUEST_CODE = 102

    override val attachToRoot: Boolean
        get() = false
    override val layout: Int
        get() = R.layout.fragment_main

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requestPermission.setOnClickListener {
            if (isPermissionGranted()) {

            } else {
                requestPermissions()
            }

        }

        pickImageFromGalleryIntent.setOnClickListener {
            Intents.pickImageFromGallery(PICK_IMAGE_REQUEST_CODE).send(this)
        }

        pickImageFromCamera.setOnClickListener {
            Intents.takePicture(TAKE_IMAGE_REQUEST_CODE).send(this)
        }
        changeLanguage.setOnClickListener {
            context?.let {
                showToast("" + "Language change is commented")
                //it1 -> LocalisationHelper.setNewLocale(it1, LocalisationHelper.LANGUAGE_KEY_HINDI) }
            }
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        activity?.let {
            PermissionManager.handleResult(
                activity as AppCompatActivity,
                requestCode,
                permissions,
                grantResults
            )
        }
    }

    override fun result(
        permissionsGranted: ArrayList<PermissionEnum>,
        permissionsDenied: ArrayList<PermissionEnum>,
        permissionsDeniedForever: ArrayList<PermissionEnum>,
        permissionsAsked: ArrayList<PermissionEnum>
    ) {
        if (permissionsGranted.size == permissionsAsked.size) {
            //Do some action

        } else if (permissionsDeniedForever.size > 0) {
            //If user answer "Never ask again" to a request for permission, you can redirect user to app settings, with an utils
            showDialog(true)
        } else {
            showDialog(false)
        }
    }

    private fun isPermissionGranted(): Boolean {
        var flag = false

        if (PermissionUtils.isGranted(requireContext(), PermissionEnum.WRITE_EXTERNAL_STORAGE)
            && PermissionUtils.isGranted(requireContext(), PermissionEnum.READ_EXTERNAL_STORAGE)
            && PermissionUtils.isGranted(requireContext(), PermissionEnum.ACCESS_COARSE_LOCATION)
        ) {
            flag = true
        }
        return flag
    }

    private fun requestPermissions() {
        PermissionManager.Builder()
            .key(REQUEST_PERMISSIONS)
            .permission(
                PermissionEnum.READ_EXTERNAL_STORAGE,
                PermissionEnum.WRITE_EXTERNAL_STORAGE,
                PermissionEnum.ACCESS_COARSE_LOCATION,
                PermissionEnum.CAMERA,
                PermissionEnum.CALL_PHONE
            )
            .callback(this)
            .ask(this)
    }

    private fun showDialog(isNeverAskAgainChecked: Boolean) {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission needed")
            .setMessage(R.string.permission_confirmation)
            .setPositiveButton(android.R.string.ok) { dialogInterface, i ->

                if (!isNeverAskAgainChecked) {
                    requestPermissions()
                } else {
                    PermissionUtils.openApplicationSettings(
                        requireContext(),
                        R::class.java.getPackage().name
                    )
                }
            }
            .setNegativeButton(android.R.string.cancel) { dialogInterface, i -> dialogInterface.dismiss() }
            .show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE_REQUEST_CODE && data != null) {
                showToast("" + data)
            } else if (requestCode == TAKE_IMAGE_REQUEST_CODE && data != null && data.extras?.get("data") != null) {
                val imageBitMap = data.extras?.get("data") as Bitmap
                imgUserPic.setImageBitmap(imageBitMap)
                encodeTobase64(imageBitMap)
            }
        }
    }

    override fun context(): Context {
        return context()
    }


}