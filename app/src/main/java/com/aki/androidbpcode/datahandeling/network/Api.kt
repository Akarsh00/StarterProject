package com.aki.androidbpcode.datahandeling.network

import com.aki.androidbpcode.datahandeling.dataclass.FarmerInfo
import com.esuvidha.grower.network.GenericResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {
    @GET("farmers/verifyNumber")
    fun checkFarmerExistWithThisNumber(
        @Query("phNumber") mobNo: String
    ): Call<GenericResponse<Any>>

    @GET("farmers/findById")
    fun getFarmerFramerId(
        @Query("farmerId") farmerId: String,
        @Query("unitCode") unitCode: String
    ): Call<GenericResponse<FarmerInfo>>

}
