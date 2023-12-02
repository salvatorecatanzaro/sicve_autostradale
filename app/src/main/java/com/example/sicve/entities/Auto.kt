package com.example.sicve.entities

import android.database.sqlite.SQLiteDatabase
import com.example.sicve.utils.DBHelper

class Auto(
    var targa : String,
    var numeroRuote : Int,
    var casaAutomobilistica : String,
    var numeroPorte : Int,
    var velocitaMassimaVeicolo : Int,
) : Veicolo(targa, numeroRuote, casaAutomobilistica, velocitaMassimaVeicolo) {
    override fun entraInAutostrada() {
        print("Il veicolo è entrato in autostrada")
    }

    override fun saveVehicle(dbw: SQLiteDatabase, username: String) {
        DBHelper.insertAuto(dbw, username, this)
    }

}