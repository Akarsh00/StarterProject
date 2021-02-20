package com.aki.commonlib.permission.interfaces


import com.aki.commonlib.permission.enums.PermissionEnum
import java.util.*

interface FullCallback {

    /**
     * @param permissionsGranted       list of permission granted
     * @param permissionsDenied        list of permission denied
     * @param permissionsDeniedForever list of permission denied forever
     * @param permissionsAsked         list of permission asked
     */
    fun result(permissionsGranted: ArrayList<PermissionEnum>, permissionsDenied: ArrayList<PermissionEnum>, permissionsDeniedForever: ArrayList<PermissionEnum>, permissionsAsked: ArrayList<PermissionEnum>)

}
