package com.chayun.naeiyagiapp.ui.addiyagi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chayun.naeiyagiapp.data.api.ApiConfig
import com.chayun.naeiyagiapp.data.model.UserModel
import com.chayun.naeiyagiapp.data.response.AddIyagiResponse
import com.chayun.naeiyagiapp.database.repository.NaeIyagiRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddIyagiViewModel(private val naeIyagiRepository: NaeIyagiRepository) : ViewModel() {
    private val _addIyagi = MutableLiveData<AddIyagiResponse>()
    val addIyagi: LiveData<AddIyagiResponse> = _addIyagi

    fun postIyagiData(token: String, file: MultipartBody.Part, description: RequestBody) {
        val client = ApiConfig.getApiService().postIyagiData(token, file, description)
        client.enqueue(object : Callback<AddIyagiResponse> {
            override fun onResponse(
                call: Call<AddIyagiResponse>,
                response: Response<AddIyagiResponse>
            ) {
                if (response.isSuccessful) {
                    _addIyagi.value = response.body()
                } else {
                    Log.e("AddIyagiViewModel", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<AddIyagiResponse>, t: Throwable) {
                Log.e("AddIyagiViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getUser(): LiveData<UserModel> {
        return naeIyagiRepository.getUser()
    }
}