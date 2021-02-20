package com.aki.androidbpcode.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aki.androidbpcode.datahandeling.dataclass.FarmerInfo
import com.aki.androidbpcode.datahandeling.network.Repository
import com.aki.androidbpcode.datahandeling.network.ResultWrapper
import com.esuvidha.grower.network.GenericResponse
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    var data: MutableLiveData<GenericResponse<FarmerInfo>> = MutableLiveData()

    fun getFarmerData(farmerId: String, unitCode: String) {
        data = MutableLiveData()
        viewModelScope.launch {
            Repository.getFarmerFramerId(farmerId, unitCode).let {
                if (it is ResultWrapper.Success) {
                    if (it.value?.data != null) {
                        it?.let { it1 ->
                            data.postValue(it.value)

                        }
                    } else {
//                        it.handelServerError()
                    }

                }
            }
        }
    }



}