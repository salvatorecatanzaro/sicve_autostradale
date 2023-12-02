package com.example.sicve.entities

import android.database.sqlite.SQLiteDatabase

abstract class Veicolo(
    targa: String,
    numeroRuote: Int,
    casaAutomobilistica: String,
    velocitaMassimaVeicolo: Int,
) {
    abstract fun entraInAutostrada() : Unit
    abstract fun saveVehicle(dbw: SQLiteDatabase, username: String) : Unit
}