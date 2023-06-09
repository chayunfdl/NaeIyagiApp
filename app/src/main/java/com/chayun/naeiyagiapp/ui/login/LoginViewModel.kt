package com.chayun.naeiyagiapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chayun.naeiyagiapp.data.api.ApiConfig
import com.chayun.naeiyagiapp.data.model.UserModel
import com.chayun.naeiyagiapp.data.response.LoginResponse
import com.chayun.naeiyagiapp.database.repository.NaeIyagiRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val naeIyagiRepository: NaeIyagiRepository) : ViewModel() {
    private val _login = MutableLiveData<LoginResponse>()
    val login: LiveData<LoginResponse> = _login

    fun postLoginData(email:String, password:String) {
        val client = ApiConfig.getApiService().postLoginData(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _login.value = response.body()
                } else {
                    Log.e("LoginViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun postUser(session: UserModel) {
        viewModelScope.launch {
            naeIyagiRepository.postUser(session)
        }
    }

    fun userLogin() {
        viewModelScope.launch {
            naeIyagiRepository.userLogin()
        }
    }
}