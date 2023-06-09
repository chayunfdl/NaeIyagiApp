package com.chayun.naeiyagiapp.database.paging

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chayun.naeiyagiapp.database.repository.IyagiPagingRepository
import com.chayun.naeiyagiapp.data.response.ListIyagiItem
import com.chayun.naeiyagiapp.di.Injection

class IyagiPagingViewModel(iyagiPagingRepository: IyagiPagingRepository) : ViewModel() {
    val iyagiPaging: LiveData<PagingData<ListIyagiItem>> =
        iyagiPagingRepository.getIyagiPaging().cachedIn(viewModelScope)
}

class ViewModelFactoryPaging(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IyagiPagingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IyagiPagingViewModel(Injection.provideRepositoryPaging(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}