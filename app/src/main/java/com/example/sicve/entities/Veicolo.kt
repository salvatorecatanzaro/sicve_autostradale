package com.example.sicve.entities

import android.database.sqlite.SQLiteDatabase
import android.view.View

abstract class Veicolo{
    abstract fun entraInAutostrada()
    abstract fun saveVehicle(dbw: SQLiteDatabase, username: String, view: View)
}