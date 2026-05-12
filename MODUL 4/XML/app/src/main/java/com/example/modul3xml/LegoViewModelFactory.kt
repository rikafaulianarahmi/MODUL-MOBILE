package com.example.modul3xml

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LegoViewModelFactory(private val legoCollection: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LegoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LegoViewModel(legoCollection) as T
        }
        throw IllegalArgumentException("ViewModel tidak dikenal")
    }
}