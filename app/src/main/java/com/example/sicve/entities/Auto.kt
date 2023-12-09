package com.example.sicve.entities

import android.database.sqlite.SQLiteDatabase
import android.view.View
import com.example.sicve.constants.ErrorConstants
import com.example.sicve.utils.DBHelper

class Auto(
    var targa : String,
    var numeroRuote : Int,
    var casaAutomobilistica : String,
    var numeroPorte : Int,
    var velocitaMassimaVeicolo : Int,
) : Veicolo() {
    override fun entraInAutostrada() {
        print("Il veicolo è entrato in autostrada")
    }

    override fun saveVehicle(dbw: SQLiteDatabase, username: String, view: View) {
        try
        {
            DBHelper.insertAuto(dbw, username, this)
        }
        catch (e: Exception)
        {
            ErrorDialog(
                ErrorConstants.ERROR_WHILE_SAVING_DATA,
                view.context
            )
        }
    }

}