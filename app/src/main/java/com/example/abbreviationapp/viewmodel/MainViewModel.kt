package com.example.abbreviationapp.viewmodel

import android.view.View
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abbreviationapp.model.MeaningsData
import com.example.abbreviationapp.repository.MainRepository
import com.example.abbreviationapp.repository.NetworkState
import com.example.abbreviationapp.retrofit.ApiInterface
import com.example.abbreviationapp.utils.ValidationUtil
import kotlinx.coroutines.launch

/**
 *  This is MainViewModel class, which has complete business logic for fetching large forms,
 *  for the sort form provided by user, and display the list on screen.
 */
class MainViewModel : ViewModel(), Observable {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val largeFormList = MutableLiveData<List<String>>()
    val loading = MutableLiveData(View.GONE)
    val rvVisibility = MutableLiveData(View.GONE)
    private val retrofitClient by lazy { ApiInterface.getInstance() }
    private val mainRepository by lazy { MainRepository(retrofitClient) }

    //API call to fetch meanings data for sortForm provided by user.
    fun getMeaningsData(sortForm: String) {
        viewModelScope.launch {
            loading.postValue(View.VISIBLE)
            when (val response = mainRepository.getMeaningsData(sortForm)) {
                is NetworkState.Success -> {
                    getLargeFormsList(response.data)
                    loading.postValue(View.GONE)
                }
                is NetworkState.Error -> {
                    onError(response.toString())
                }
            }
        }
    }

    //Segregating large form list from MeaningsData response.
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
        loading.postValue(View.GONE)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}