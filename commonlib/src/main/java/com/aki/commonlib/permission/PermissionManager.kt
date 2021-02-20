package com.aki.commonlib.permission

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.aki.commonlib.permission.constants.PermissionConstant
import com.aki.commonlib.permission.enums.PermissionEnum
import com.aki.commonlib.permission.interfaces.AskAgainCallback
import com.aki.commonlib.permission.interfaces.FullCallback
import com.aki.commonlib.permission.interfaces.SimpleCallback
import com.aki.commonlib.permission.interfaces.SmartCallback
import java.util.*


class PermissionManager {

    private var fullCallback: FullCallback? = null
    private var simpleCallback: SimpleCallback? = null
    private var askAgainCallback: AskAgainCallback? = null
    private var smartCallback: SmartCallback? = null

    private var askAgain = false

    private var permissions: ArrayList<PermissionEnum>? = null
    private var permissionsGranted: ArrayList<PermissionEnum>? = null
    private var permissionsDenied: ArrayList<PermissionEnum>? = null
    private var permissionsDeniedForever: ArrayList<PermissionEnum>? = null
    private var permissionToAsk: ArrayList<PermissionEnum>? = null

    private var key = PermissionConstant.KEY_PERMISSION

    /**
     * @param permissions an array of permission that you need to ask
     * @return current instance
     */
    fun permissions(permissions: ArrayList<PermissionEnum>): PermissionManager {
        this.permissions = ArrayList()
        this.permissions!!.addAll(permissions)
        return this
    }

    /**
     * @param permission permission you need to ask
     * @return current instance
     */
    fun permission(permission: PermissionEnum): PermissionManager {
        this.permissions = ArrayList()
        this.permissions!!.add(permission)
        return this
    }

    /**
     * @param permissions permission you need to ask
     * @return current instance
     */
    fun permission(vararg permissions: PermissionEnum): PermissionManager {
        this.permissions = ArrayList()
        Collections.addAll(this.permissions, *permissions)
        return this
    }

    /**
     * @param askAgain ask again when permission not granted
     * @return current instance
     */
    fun askAgain(askAgain: Boolean): PermissionManager {
        this.askAgain = askAgain
        return this
    }

    /**
     * @param fullCallback set fullCallback for the request
     * @return current instance
     */
    fun callback(fullCallback: FullCallback): PermissionManager {
        this.simpleCallback = null
        this.smartCallback = null
        this.fullCallback = fullCallback
        return this
    }

    /**
     * @param simpleCallback set simpleCallback for the request
     * @return current instance
     */
    fun callback(simpleCallback: SimpleCallback): PermissionManager {
        this.fullCallback = null
        this.smartCallback = null
        this.simpleCallback = simpleCallback
        return this
    }

    /**
     * @param smartCallback set smartCallback for the request
     * @return current instance
     */
    fun callback(smartCallback: SmartCallback): PermissionManager {
        this.fullCallback = null
        this.simpleCallback = null
        this.smartCallback = smartCallback
        return this
    }

    /**
     * @param askAgainCallback set askAgainCallback for the request
     * @return current instance
     */
    fun askAgainCallback(askAgainCallback: AskAgainCallback): PermissionManager {
        this.askAgainCallback = askAgainCallback
        return this
    }

    /**
     * @param key set a custom request code
     * @return current instance
     */
    fun key(key: Int): PermissionManager {
        this.key = key
        return this
    }

    /**
     * @param activity target activity
     * just start all permission manager
     */
    fun ask(activity: AppCompatActivity) {
        ask(activity, null)
    }

    fun ask(fragment: Fragment) {
        ask(null, fragment)
    }


    private fun ask(
        activity: AppCompatActivity?,
        fragment: Fragment?
    ) {
        initArray()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionToAsk = permissionToAsk(activity, fragment)
            if (permissionToAsk.size == 0) {
                showResult()
            } else {
                if (null != activity) {
                    ActivityCompat.requestPermissions(activity, permissionToAsk, key)
                } else if (fragment != null) {
                    fragment?.requestPermissions(permissionToAsk, key)
                }
            }
        } else {
            permissionsGranted!!.addAll(this.permissions!!)
            showResult()
        }
    }

    /**
     * @return permission that you really need to ask
     */
    private fun permissionToAsk(
        activity: AppCompatActivity?,
        fragment: Fragment?
    ): Array<String> {
        val permissionToAsk = ArrayList<String>()
        for (permission in permissions!!) {
            var isGranted = false
            if (null != activity) {
                isGranted = PermissionUtils.isGranted(activity, permission)
            } else if (fragment != null) {
                isGranted = PermissionUtils.isGranted(fragment.requireContext(), permission)
            }
            if (!isGranted) {
                permissionToAsk.add(permission.toString())
            } else {
                permissionsGranted!!.add(permission)
            }
        }
        return permissionToAsk.toTypedArray()
    }

    /**
     * init permissions ArrayList
     */
    private fun initArray() {
        this.permissionsGranted = ArrayList()
        this.permissionsDenied = ArrayList()
        this.permissionsDeniedForever = ArrayList()
        this.permissionToAsk = ArrayList()
    }

    /**
     * check if one of three types of callback are not null and pass data
     */
    private fun showResult() {
        if (simpleCallback != null)
            simpleCallback!!.result(permissionToAsk!!.size == 0 || permissionToAsk!!.size == permissionsGranted!!.size)
        if (fullCallback != null)
            fullCallback!!.result(
                permissionsGranted!!,
                permissionsDenied!!,
                permissionsDeniedForever!!,
                permissions!!
            )
        if (smartCallback != null)
            smartCallback!!.result(
                permissionToAsk!!.size == 0 || permissionToAsk!!.size == permissionsGranted!!.size,
                !permissionsDeniedForever!!.isEmpty()
            )
        instance = null
    }

    companion object {

        private var instance: PermissionManager? = null

        /**
         * @return current instance
         */
        fun Builder(): PermissionManager {
            if (instance == null) {
                instance = PermissionManager()
            }
            return instance as PermissionManager
        }

        /**
         * @param activity     target activity
         * @param requestCode  requestCode
         * @param permissions  permissions
         * @param grantResults grantResults
         */
        fun handleResult(
            activity: AppCompatActivity,
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            handleResult(activity, null, null, requestCode, permissions, grantResults)
        }

        /**
         * @param v4fragment   target v4 fragment
         * @param requestCode  requestCode
         * @param permissions  permissions
         * @param grantResults grantResults
         */
        fun handleResult(
            v4fragment: Fragment,
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            handleResult(null, v4fragment, null, requestCode, permissions, grantResults)
        }


        private fun handleResult(
            activity: AppCompatActivity?,
            v4fragment: Fragment?,
            fragment: Fragment?,
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            if (instance == null) return
            if (requestCode == instance!!.key) {
                for (i in permissions.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        instance!!.permissionsGranted!!.add(
                            PermissionEnum.fromManifestPermission(
                                permissions[i]
                            )
                        )
                    } else {
                        var permissionsDeniedForever = false
                        if (null != activity) {
                            permissionsDeniedForever =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                    activity,
                                    permissions[i]
                                )
                        } else if (fragment != null) {
                            if (v4fragment != null) {
                                permissionsDeniedForever =
                                    v4fragment.shouldShowRequestPermissionRationale(permissions[i])
                            }
                        } else if (v4fragment != null) {
                            permissionsDeniedForever =
                                v4fragment.shouldShowRequestPermissionRationale(permissions[i])
                        }
                        if (!permissionsDeniedForever) {
                            instance!!.permissionsDeniedForever!!.add(
                                PermissionEnum.fromManifestPermission(
                                    permissions[i]
                                )
                            )
                        }
                        instance!!.permissionsDenied!!.add(
                            PermissionEnum.fromManifestPermission(
                                permissions[i]
                            )
                        )
                        instance!!.permissionToAsk!!.add(
                            PermissionEnum.fromManifestPermission(
                                permissions[i]
                            )
                        )
                    }
                }
                if (instance!!.permissionToAsk!!.size != 0 && instance!!.askAgain) {
                    instance!!.askAgain = false
                    if (instance!!.askAgainCallback != null && instance!!.permissionsDeniedForever!!.size != instance!!.permissionsDenied!!.size) {
                        instance!!.askAgainCallback!!.showRequestPermission(object :
                            AskAgainCallback.UserResponse {
                            override fun result(askAgain: Boolean) {
                                if (askAgain) {
                                    instance!!.ask(activity, fragment)
                                } else {
                                    instance!!.showResult()
                                }
                            }
                        })
                    } else {
                        instance!!.ask(activity, fragment)
                    }
                } else {
                    instance!!.showResult()
                }
            }
        }
    }

}