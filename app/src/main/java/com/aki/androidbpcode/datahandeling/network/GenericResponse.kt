package com.esuvidha.grower.network

data class GenericResponse<T>(
    var data: T,
    val error: Boolean? = false,
    val message: String? = "",
    val paginationDetail: Int? = 0,
    val success: Boolean? = true
)


