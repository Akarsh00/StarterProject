package com.aki.commonlib.permission.interfaces


interface SmartCallback {

    /**
     * @param allPermissionsGranted        true if all permissions are granted
     * @param somePermissionsDeniedForever true if one of asked permissions are denied forever
     */
    fun result(allPermissionsGranted: Boolean, somePermissionsDeniedForever: Boolean)

}
