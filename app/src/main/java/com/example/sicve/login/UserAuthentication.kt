package com.example.sicve.login

import android.database.sqlite.SQLiteDatabase
import com.example.sicve.entities.User

abstract class UserAuthentication{
    abstract fun login(dbw: SQLiteDatabase) : Map<String, User>
}