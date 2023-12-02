package com.example.sicve.entities

import android.database.sqlite.SQLiteDatabase
import com.example.sicve.utils.DBHelper

class Moto(
    var targa : String,
    var numeroRuote : Int,
    var casaAutomobilistica : String,
    var velocitaMassimaVeicolo : Int,
) : Veicolo(targa, numeroRuote, casaAutomobilistica, velocitaMassimaVeicolo) {
    override fun entraInAutostrada() {
        print("Il veicolo è entrato in autostrada")
    }

    override fun saveVehicle(dbw: SQLiteDatabase, username: String) {
        DBHelper.insertMoto(dbw, username, this)
    }

}