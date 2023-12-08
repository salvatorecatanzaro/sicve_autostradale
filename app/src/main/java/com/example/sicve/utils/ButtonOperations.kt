package com.example.sicve.utils

import android.database.sqlite.SQLiteDatabase
import android.view.View

interface ButtonOperations {
    fun saveOperation(view: View, formMap: MutableMap<String, Any?>, dbw: SQLiteDatabase)
    fun deleteOperation(view: View, formMap: MutableMap<String, Any?>, dbw: SQLiteDatabase)
}