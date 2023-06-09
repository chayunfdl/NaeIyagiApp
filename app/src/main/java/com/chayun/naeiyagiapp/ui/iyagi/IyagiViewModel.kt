package com.chayun.naeiyagiapp.ui.iyagi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chayun.naeiyagiapp.data.api.ApiConfig
import com.chayun.naeiyagiapp.data.api.ApiConfig.Companion.getApiService
import com.chayun.naeiyagiapp.data.model.UserModel
import com.chayun.naeiyagiapp.data.response.IyagiResponse
import com.chayun.naeiyagiapp.database.repository.NaeIyagiRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class IyagiViewModel(private val naeIyagiRepository: NaeIyagiRepository) : ViewModel() {
    private val _listIyagi = MutableLiveData<IyagiResponse>()
    val listIyagi: LiveData<IyagiResponse> = _listIyagi

    fun getIyagiData(token: String) {
        val client = ApiConfig.getApiService().getIyagiData(token, location=1)
        client.enqueue(object : Callback<IyagiResponse> {
            override fun onResponse(call: Call<IyagiResponse>, response: Response<IyagiResponse>)
            {
                if (response.isSuccessful) {
                    _listIyagi.value = response.body()
                } else {
                    Log.e("IyagiViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<IyagiResponse>, t: Throwable) {
                Log.e("IyagiViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return naeIyagiRepository.getUser()
    }

    fun logout() {
        viewModelScope.launch {
            naeIyagiRepository.userLogout()
        }
    }
}