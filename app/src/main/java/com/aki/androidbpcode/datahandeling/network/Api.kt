package com.aki.androidbpcode.datahandeling.network

import com.aki.androidbpcode.datahandeling.data.Country
import com.esuvidha.grower.network.GenericResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET("all")
    fun getAllCountries(): Call</*GenericResponse<*/List<Country/*>*/>>

}
