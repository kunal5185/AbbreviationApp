package com.example.abbreviationapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abbreviationapp.model.MeaningsData
import com.example.abbreviationapp.repository.MainRepository
import com.example.abbreviationapp.repository.NetworkState
import com.example.abbreviationapp.retrofit.ApiInterface
import com.example.abbreviationapp.utils.ValidationUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 *  This is MainViewModel class, which has complete business logic for fetching large forms,
 *  for the sort form provided by user, and display the list on screen.
 */
class MainViewModel : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val largeFormList = MutableLiveData<List<String>>()
    private var job: Job? = null
    val loading = MutableLiveData(false)
    private val retrofitClient = ApiInterface.getInstance()
    private val mainRepository = MainRepository(retrofitClient)

    fun getMeaningsData(sortForm: String) {
        viewModelScope.launch {
            loading.postValue(true)
            when (val response = mainRepository.getMeaningsData(sortForm)) {
                is NetworkState.Success -> {
                    getLargeFormsList(response.data)
                    loading.postValue(false)
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
            onError(ValidationUtil.RESPONSE_ERROR_MESSAGE)
        }
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}