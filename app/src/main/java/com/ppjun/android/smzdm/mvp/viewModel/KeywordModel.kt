package com.ppjun.android.smzdm.mvp.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class KeywordModel : ViewModel() {

    var keyword: MutableLiveData<String>? = MutableLiveData()

    fun getKeyWord(): LiveData<String> {

        return keyword!!
    }

    fun setKeyWord(key: String) {
        keyword?.value = key
    }
}