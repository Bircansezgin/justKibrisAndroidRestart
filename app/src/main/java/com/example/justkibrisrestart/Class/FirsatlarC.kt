package com.softrestart.justkibrisrestart.Class

import java.io.Serializable
import java.util.Date

data class FirsatlarC(
    var firsatBasligi: String = "",
    var firsatAciklamasi: String = "",
    var firsatSonTarih: String = "",
    var firsatEskiTutar: Int = 0,
    var firsatYeniTutar: Int = 0,
    var firsatSistemKapanisTarih: Date? = null,
    var firsatKullanimSayisi: Int = 0,
    var firsatEklenmeTarihi: Date? = null,
    var imageUrl: String = "",
    var documentID: String = "",
) : Serializable {

}