package com.aki.androidbpcode.datahandeling.network


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object CoroutineFactory {

    suspend fun <T> executeNetworkRequest(action: suspend () -> Response<T>): ResultWrapper<T?> {
        return withContext(Dispatchers.IO) {
            try {
                val req = action.invoke()
                if (req.isSuccessful)
                    ResultWrapper.Success(req.body())
                else {
                    ResultWrapper.HttpError(req.code(), true)
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.Error(throwable.message)
                    is HttpException -> ResultWrapper.HttpError(throwable.code(), true)
                    else -> ResultWrapper.Error(throwable.message)
                }
            }
        }
    }

}