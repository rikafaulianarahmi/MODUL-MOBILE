package com.example.modul3xml

import androidx.lifecycle.ViewModel

class LegoViewModel : ViewModel() {
    val legoList = listOf(
        Lego(1, "Millennium Falcon", "2017", "Star Wars", "7541 pcs", "Pesawat tempur legendaris Han Solo.", R.drawable.lego1, "https://www.lego.com"),
        Lego(2, "Hogwarts Castle", "2018", "Harry Potter", "6020 pcs", "Kastil sekolah sihir Hogwarts.", R.drawable.lego2, "https://www.lego.com"),
        Lego(3, "Taj Mahal", "2021", "Architecture", "2022 pcs", "Replika bangunan Taj Mahal.", R.drawable.lego3, "https://www.lego.com"),
        Lego(4, "Porsche 911", "2021", "Creator", "1458 pcs", "Mobil klasik Porsche 911.", R.drawable.lego4, "https://www.lego.com"),
        Lego(5, "Central Perk", "2019", "Ideas", "1070 pcs", "Kafe ikonik dari serial TV Friends.", R.drawable.lego5, "https://www.lego.com")
    )
}