package com.example.modul3xml

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class LegoViewModel(val namaAplikasi: String) : ViewModel() {

    private val _legoList = MutableStateFlow<List<Lego>>(emptyList())
    val legoList: StateFlow<List<Lego>> = _legoList.asStateFlow()

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
        val list = listOf(
            Lego(1, "Millennium Falcon", "2017", "Star Wars", "7541 pcs", "Pesawat tempur legendaris Han Solo.", R.drawable.lego1, "https://www.lego.com"),
            Lego(2, "Hogwarts Castle", "2018", "Harry Potter", "6020 pcs", "Kastil sekolah sihir Hogwarts.", R.drawable.lego2, "https://www.lego.com"),
            Lego(3, "Taj Mahal", "2021", "Architecture", "2022 pcs", "Replika bangunan Taj Mahal.", R.drawable.lego3, "https://www.lego.com"),
            Lego(4, "Porsche 911", "2021", "Creator", "1458 pcs", "Mobil klasik Porsche 911.", R.drawable.lego4, "https://www.lego.com"),
            Lego(5, "Central Perk", "2019", "Ideas", "1070 pcs", "Kafe ikonik dari serial TV Friends.", R.drawable.lego5, "https://www.lego.com")
        )

        Timber.d("[$namaAplikasi] Data berhasil dimuat: ${list.size} item")
        list.forEach { lego ->
            Timber.d("  → Item masuk: [${lego.id}] ${lego.title} (${lego.year})")
        }

        _legoList.value = list
    }

    fun onDetailClick(lego: Lego) {
        Timber.d("Tombol Detail ditekan → ${lego.title}")
        _clickEvent.value = ClickEvent.DetailClick(lego)
    }

    fun onWebClick(lego: Lego) {
        Timber.d("Tombol Explicit Intent (Situs) ditekan → ${lego.title} | URL: ${lego.webUrl}")
        _clickEvent.value = ClickEvent.WebClick(lego)
    }

    fun resetClickEvent() {
        _clickEvent.value = null
    }
}