package com.example.abbreviationapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abbreviationapp.model.MeaningsData
import com.example.abbreviationapp.repository.MainRepository
import com.example.abbreviationapp.repository.NetworkState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val largeFormList = MutableLiveData<List<String>>()
    private var job: Job? = null
    val loading = MutableLiveData(false)

    fun getMeaningsData(sortForm: String) {
        viewModelScope.launch {
            loading.value = true
            when (val response = mainRepository.getMeaningsData(sortForm)) {
                is NetworkState.Success -> {
                    getLargeFormsList(response.data)
                    loading.value = false
                }
                is NetworkState.Error -> {
                    onError(response.toString())
                }
            }
        }
    }

    private fun getLargeFormsList(meaningsData: MeaningsData) {
        if ((meaningsData.isNotEmpty()) && (meaningsData[0].lfs.isNotEmpty())) {
            val tempLfArrayList = mutableListOf<String>()
            for (lfItem in meaningsData[0].lfs) {
                tempLfArrayList.add(lfItem.lf)
            }
            largeFormList.postValue(tempLfArrayList)
        } else {
            onError("Response is null or empty")
        }
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}