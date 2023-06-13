package com.dhobi.perfectdhobi.data.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.repository.MainRepository
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class CommonModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CommonViewModel(MainRepository(apiHelper)) as T
}