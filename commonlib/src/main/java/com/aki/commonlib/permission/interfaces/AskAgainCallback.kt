package com.aki.commonlib.permission.interfaces


interface AskAgainCallback {

    /**
     * @param response user response
     */
    fun showRequestPermission(response: UserResponse)

    interface UserResponse {

        /**
         * @param askAgain the response from the user if allow to ask again a permission
         */
        fun result(askAgain: Boolean)

    }

}
