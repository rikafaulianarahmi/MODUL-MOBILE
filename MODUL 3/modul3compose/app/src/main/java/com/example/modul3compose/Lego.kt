package com.example.modul3compose

data class Lego(
    val id: Int,
    val title: String,
    val year: String,
    val theme: String,
    val description: String,
    val imageRes: Int,
    val webUrl: String
)

fun getDummyLegoList(): List<Lego> {
    return listOf(
        Lego(1, "Millennium Falcon", "2017", "Star Wars", "Pesawat tempur legendaris Han Solo.", R.drawable.lego1, "https://www.lego.com"),
        Lego(2, "Hogwarts Castle", "2018", "Harry Potter", "Kastil sekolah sihir Hogwarts.", R.drawable.lego2, "https://www.lego.com"),
        Lego(3, "Taj Mahal", "2021", "Architecture", "Replika bangunan Taj Mahal di India.", R.drawable.lego3, "https://www.lego.com"),
        Lego(4, "Porsche 911", "2021", "Icons", "Mobil sport klasik Porsche 911.", R.drawable.lego4, "https://www.lego.com"),
        Lego(5, "Eiffel Tower", "2022", "Architecture", "Menara ikonik dari kota Paris.", R.drawable.lego5, "https://www.lego.com")
    )
}