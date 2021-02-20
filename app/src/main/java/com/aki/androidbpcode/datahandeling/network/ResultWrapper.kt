package com.aki.androidbpcode.datahandeling.network

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class HttpError(val code: Int? = null, val error: Boolean? = null) :
        ResultWrapper<Nothing>()

    data class Error(val errorMessage: String?) : ResultWrapper<Nothing>()

}
