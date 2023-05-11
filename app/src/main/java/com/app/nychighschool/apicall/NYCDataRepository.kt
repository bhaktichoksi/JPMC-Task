package com.app.nychighschool.apicall

import com.app.nychighschool.model.AdditionalData
import com.app.nychighschool.model.NYCData

class NYCDataRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getNYCData(): List<NYCData> {
        return apiService.getNYCData()
    }
    suspend fun getAdditionalData(dbn: String): List<AdditionalData> {
        return apiService.getAdditionalData(dbn)
    }
}
