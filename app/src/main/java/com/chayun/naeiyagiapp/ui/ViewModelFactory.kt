package com.chayun.naeiyagiapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chayun.naeiyagiapp.database.repository.NaeIyagiRepository
import com.chayun.naeiyagiapp.di.Injection
import com.chayun.naeiyagiapp.ui.addiyagi.AddIyagiViewModel
import com.chayun.naeiyagiapp.ui.iyagi.IyagiViewModel
import com.chayun.naeiyagiapp.ui.login.LoginViewModel
import com.chayun.naeiyagiapp.ui.register.RegisterViewModel

class ViewModelFactory(private val naeIyagiRepository: NaeIyagiRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(IyagiViewModel::class.java) -> {
               IyagiViewModel(naeIyagiRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel() as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(naeIyagiRepository) as T
            }
            modelClass.isAssignableFrom(AddIyagiViewModel::class.java) -> {
                AddIyagiViewModel(naeIyagiRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown Model class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }
}