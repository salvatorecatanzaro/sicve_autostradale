package com.example.sicve.entities

import android.database.sqlite.SQLiteDatabase
import com.example.sicve.utils.DBHelper

class Computer(
    var id: Int,
    var listaMulte: MutableList<String>
) {

    fun salvaInfrazioni(computerId: Int, dbw: SQLiteDatabase) {
        DBHelper.insertInfrazioni(listaMulte, computerId, dbw)
    }
}