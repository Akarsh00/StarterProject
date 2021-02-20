package com.aki.androidbpcode.datahandeling.network

import com.aki.androidbpcode.datahandeling.dataclass.FarmerInfo
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


    private var apiDCM = apiFromDCM()

    private const val APP_DETAILS_FROM_DCM =
        "http://dcm-uat-lb-1966467072.ap-south-1.elb.amazonaws.com:8080/DCM-Oracle/api/"
    private const val APP_DETAILS_FROM_LMS =
        "http://dcm-uat-lb-1966467072.ap-south-1.elb.amazonaws.com:8080/dcmlms/api/"


    /*     private const val APP_DETAILS_FROM_DCM = "http://65.0.103.68:8080/DCM-Oracle/api/"
         private const val APP_DETAILS_FROM_LMS = "http://65.0.103.68:8080/dcmlms/api/"*/


    private fun apiFromDCM(
    ): Api {
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
            .baseUrl(APP_DETAILS_FROM_DCM)
            .build().create(Api::class.java)
    }


    private fun getBaseURL(): String? {

        return APP_DETAILS_FROM_LMS
    }


    suspend fun checkFarmerExistWithThisNumber(mobileNumber: String): ResultWrapper<GenericResponse<Any>?> {
        return CoroutineFactory.executeNetworkRequest {
            apiDCM.checkFarmerExistWithThisNumber(mobileNumber).execute()

        }
    }

    suspend fun getFarmerFramerId(
        farmerId: String,
        unitCode: String
    ): ResultWrapper<GenericResponse<FarmerInfo>?> {
        return CoroutineFactory.executeNetworkRequest {
            apiDCM.getFarmerFramerId(farmerId, unitCode).execute()

        }
    }


}


