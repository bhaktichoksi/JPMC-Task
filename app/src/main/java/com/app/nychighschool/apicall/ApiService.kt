package com.app.nychighschool.apicall

import com.app.nychighschool.model.AdditionalData
import com.app.nychighschool.model.NYCData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("s3k6-pzi2.json")
    suspend fun getNYCData(): List<NYCData>

    @GET("f9bf-2cp4.json")
    suspend fun getAdditionalData(@Query("dbn") dbn: String): List<AdditionalData>
}
