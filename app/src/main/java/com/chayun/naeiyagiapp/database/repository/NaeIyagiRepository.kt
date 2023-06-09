package com.chayun.naeiyagiapp.database.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.chayun.naeiyagiapp.data.model.UserModel
import com.chayun.naeiyagiapp.data.model.UserPreferences

class NaeIyagiRepository private constructor(
    private val pref: UserPreferences,
) {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    suspend fun postUser(session: UserModel) {
        pref.postUser(session)
    }

    suspend fun userLogin() {
        pref.login()
    }

    suspend fun userLogout() {
        pref.logout()
    }

    companion object {
        @Volatile
        private var instance: NaeIyagiRepository? = null
        fun getInstance(preference: UserPreferences): NaeIyagiRepository =
            instance ?: synchronized(this) {
                instance ?: NaeIyagiRepository(preference)
            }.also { instance = it }
    }
}