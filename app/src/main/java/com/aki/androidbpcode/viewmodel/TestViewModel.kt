package com.aki.androidbpcode.viewmodel

import androidx.lifecycle.*
import com.aki.androidbpcode.datahandeling.data.Country
import com.aki.androidbpcode.datahandeling.network.Repository
import com.aki.androidbpcode.datahandeling.network.ResultWrapper
import com.esuvidha.grower.network.GenericResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    var data: MutableLiveData</*GenericResponse<*/List<Country/*>*/>> = MutableLiveData()
    var _data: LiveData</*GenericResponse<*/List<Country>/*>*/> = data

    fun getAllCountries() {
//        data = MutableLiveData()
        viewModelScope.launch {
            Repository.getAllCountries().let {
                if (it is ResultWrapper.Success) {
                    if (it != null) {
                        it?.let { it1 ->
                            if (data.value?.equals(it.value) != true)
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