package com.chayun.naeiyagiapp.database.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.chayun.naeiyagiapp.data.api.APIService
import com.chayun.naeiyagiapp.data.model.UserPreferences
import com.chayun.naeiyagiapp.data.response.ListIyagiItem
import com.chayun.naeiyagiapp.database.paging.IyagiPagingSource

class IyagiPagingRepository(
    private val apiService: APIService,
    private val preference: UserPreferences
) {
    fun getIyagiPaging(): LiveData<PagingData<ListIyagiItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                IyagiPagingSource(apiService, preference)
            }
        ).liveData
    }
}