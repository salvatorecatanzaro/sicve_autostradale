package com.example.sicve.login

import com.example.sicve.entities.User
import java.io.InputStream

class HardCodedCredentialsAuthentication(username : String, password : String) :  UserAuthentication(username, password) {
    private var usr: String = username
    private var pwd: String = password


    override fun login(): Map<String, User> {
        val lineList = mutableListOf<String>("admin admin admin admin ADMIN", "utente utente utente utente UTENTE")
        val nonexistinguser = User("not not not not NOT".split(" "))
        for (it: String in lineList) {
            if (it.trim().startsWith("//")) {
                continue  // Skipping comments
            }
            val dati = it.split(" ")
            val user = User(dati)

            if (usr == user.username) {
                var message: String

                if (pwd == user.password)
                    message = "Success"
                else
                    message = "Password errata!"
                return mapOf(message to user)
            }

        }

        return mapOf("Utente non esiste" to nonexistinguser)


    }
}