package com.example.sicve.buttons

import android.database.sqlite.SQLiteDatabase
import android.view.View
import com.example.sicve.entities.HighwayBlock

interface ButtonOperations {
    fun saveOperation(view: View, formMap: MutableMap<String, Any?>, dbw: SQLiteDatabase)
    fun deleteOperation(view: View, formMap: MutableMap<String, Any?>, dbw: SQLiteDatabase)
}