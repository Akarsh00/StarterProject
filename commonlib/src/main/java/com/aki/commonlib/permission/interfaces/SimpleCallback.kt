package com.aki.commonlib.permission.interfaces


interface SimpleCallback {

    /**
     * @param allPermissionsGranted true if all permissions are granted
     */
    fun result(allPermissionsGranted: Boolean)

}
