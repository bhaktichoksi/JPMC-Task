package com.app.nychighschool.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.nychighschool.apicall.NYCDataRepository
import com.app.nychighschool.model.NYCData
import kotlinx.coroutines.launch

class NYCDataViewModel : ViewModel() {
    private val nycDataRepository = NYCDataRepository()

    private val _nycData = MutableLiveData<List<NYCData>>()
    val nycData: LiveData<List<NYCData>> get() = _nycData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchNYCData() {
        viewModelScope.launch {
            try {
                val nycDataList = nycDataRepository.getNYCData()
                _nycData.value = nycDataList
                Log.d("responseValue", "fetchNYCData: " + nycDataList.size)
            } catch (e: Exception) {
                // Handle the error
                //nycData.value = e.message
                _errorMessage.value = e.message
                Log.d("fetchNYCData", "fetchNYCData: " + e.message)
            }
        }
    }
}
