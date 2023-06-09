package com.chayun.naeiyagiapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chayun.naeiyagiapp.data.api.ApiConfig
import com.chayun.naeiyagiapp.data.model.UserPreferences
import com.chayun.naeiyagiapp.database.paging.IyagiDatabase
import com.chayun.naeiyagiapp.database.repository.IyagiPagingRepository
import com.chayun.naeiyagiapp.database.repository.NaeIyagiRepository

val Context.database: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun provideRepository(context: Context): NaeIyagiRepository {
        val preference = UserPreferences.getInstance(context.database)
        return NaeIyagiRepository.getInstance(preference)
    }

    fun provideRepositoryPaging(context: Context): IyagiPagingRepository {
        IyagiDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        val preference = UserPreferences.getInstance(context.database)
        return IyagiPagingRepository(apiService, preference)
    }
}
