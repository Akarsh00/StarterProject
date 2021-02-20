package com.aki.androidbpcode.datahandeling.network

import com.aki.androidbpcode.datahandeling.data.Country
import com.esuvidha.grower.network.GenericResponse
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object Repository {
    private val sBuilder = Retrofit.Builder()
        .baseUrl(getBaseURL())
        .addConverterFactory(GsonConverterFactory.create())


    private var api = getApi()

    private const val APP_BASE_URL =
        "https://restcountries.eu/rest/v2/"


    private fun getApi(): Api {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.addInterceptor(OkHttpProfilerInterceptor())
        builder.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()

                val requestBuilder =
                    original.newBuilder()
                        .addHeader("User-Agent", "android")
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        })

        return sBuilder.client(builder.build())
            .baseUrl(APP_BASE_URL)
            .build().create(Api::class.java)
    }


    private fun getBaseURL(): String? {

        return APP_BASE_URL
    }


    suspend fun getAllCountries(
    ): ResultWrapper</*GenericResponse<*/List<Country>/*>*/?> {
        return CoroutineFactory.executeNetworkRequest {
            api.getAllCountries().execute()

        }
    }


}


