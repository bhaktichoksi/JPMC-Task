package com.app.nychighschool.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.nychighschool.apicall.NYCDataRepository
import com.app.nychighschool.model.AdditionalData
import kotlinx.coroutines.launch

class NYCSchoolDataViewModel : ViewModel() {
    private val nycDataRepository = NYCDataRepository()

    private val _additionalData = MutableLiveData<List<AdditionalData>>()
    val additionalData: LiveData<List<AdditionalData>> get() = _additionalData

    fun fetchAdditionalData(url: String) {
        viewModelScope.launch {
            try {
                val response = nycDataRepository.getAdditionalData(url)
                _additionalData.value = response
                Log.d("responseData", "fetchAdditionalData: " + response)
            } catch (e: Exception) {
                // Handle network error
                Log.d("errorTag", "fetchAdditionalData: " + e.message)
            }
        }
    }
}