package com.example.abbreviationapp.repository

import com.example.abbreviationapp.model.MeaningsData
import com.example.abbreviationapp.retrofit.ApiInterface
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MainRepositoryTest {

    private lateinit var mainRepository: MainRepository

    @Mock
    lateinit var apiInterface: ApiInterface

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mainRepository = MainRepository(apiInterface)
    }

    @Test
    fun `get meanings data test`() {
        runBlocking {
            val meaningsData = MeaningsData()
            Mockito.`when`(apiInterface.getMeaningsData("sf")).thenReturn(
                Response.success(meaningsData)
            )

            val response = mainRepository.getMeaningsData("sf")
            assertEquals(NetworkState.Success(meaningsData), response)
        }
    }
}