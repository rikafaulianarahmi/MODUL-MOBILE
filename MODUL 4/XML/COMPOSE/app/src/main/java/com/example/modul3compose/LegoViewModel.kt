package com.example.modul3compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class LegoViewModel(val legoCollection: String) : ViewModel() {

    // StateFlow data list
    private val _legoList = MutableStateFlow<List<Lego>>(emptyList())
    val legoList: StateFlow<List<Lego>> = _legoList.asStateFlow()

    // StateFlow event klik
    private val _clickEvent = MutableStateFlow<ClickEvent?>(null)
    val clickEvent: StateFlow<ClickEvent?> = _clickEvent.asStateFlow()

    sealed class ClickEvent {
        data class DetailClick(val lego: Lego) : ClickEvent()
        data class WebClick(val lego: Lego) : ClickEvent()
    }

    init {
        loadLegoData()
    }

    private fun loadLegoData() {
        val list = getDummyLegoList()

        Timber.d("[$legoCollection] Data dimuat: ${list.size} item")
        list.forEach { lego ->
            Timber.d("  → Item masuk: [${lego.id}] ${lego.title}")
        }

        _legoList.value = list
    }

    fun onDetailClick(lego: Lego) {
        Timber.d("Tombol Detail ditekan → ${lego.title}")
        _clickEvent.value = ClickEvent.DetailClick(lego)
    }

    fun onWebClick(lego: Lego) {
        Timber.d("Tombol Web/Explicit Intent ditekan → ${lego.title}")
        _clickEvent.value = ClickEvent.WebClick(lego)
    }

    fun resetClickEvent() {
        _clickEvent.value = null
    }
}

class LegoViewModelFactory(private val namaAplikasi: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LegoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LegoViewModel(namaAplikasi) as T
        }
        throw IllegalArgumentException("ViewModel tidak dikenal")
    }
}