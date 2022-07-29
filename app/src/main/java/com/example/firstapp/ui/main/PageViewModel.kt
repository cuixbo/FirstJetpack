package com.example.firstapp.ui.main

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    private val _content = MutableLiveData<String>()
    val text: LiveData<String> = Transformations.map(_content) {
        it
    }


    fun setIndex(index: Int) {
        _index.value = index
    }

    fun setContent(content: String) {
        _content.value = content
    }

}
