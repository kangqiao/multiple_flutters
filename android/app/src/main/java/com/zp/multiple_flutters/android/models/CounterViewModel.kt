package com.zp.multiple_flutters.android.models

import androidx.lifecycle.MutableLiveData


/**
 * Created by ZhaoPan on 2024/2/28
 */
class CounterViewModel: BaseViewModel() {

    var count: MutableLiveData<Int> = MutableLiveData()

    init {
        count.value = 0
    }

    fun increment() {
        count.value = count.value?.plus(1)
    }
}