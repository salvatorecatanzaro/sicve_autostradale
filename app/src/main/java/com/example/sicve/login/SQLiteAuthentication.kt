package com.example.sicve.login

import android.database.sqlite.SQLiteDatabase
import com.example.sicve.entities.User
import com.example.sicve.utils.DBHelper

class SQLiteAuthentication(
    var username : String,
    var password : String
):
    UserAuthentication() {
    override fun login(dbw: SQLiteDatabase): Map<String, User> {
        val cursor =  dbw.query("USER", null, "USERNAME='${this.username}'", null, null, null, null)
        val nonexistinguser = User("not not not not NOT".split(" "))

        if(cursor.count == 0)
        {
            cursor.close()
            return mapOf("Utente non esiste" to nonexistinguser)
        }

        cursor.moveToNext()
        var dbUsername = cursor.getString(0)
        var dbNome = cursor.getString(1)
        var dbCognome = cursor.getString(2)
        var dbPassword = cursor.getString(3)
        var dbRuolo = cursor.getString(4)

        if(this.password != dbPassword)
            return mapOf("Password errata" to nonexistinguser)

        return mapOf("Success" to User(listOf(dbNome, dbCognome, dbUsername, dbPassword, dbRuolo)))
    }
}