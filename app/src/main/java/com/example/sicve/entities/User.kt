package com.example.sicve.entities

class User(vals: List<String>) {
    var nome : String
    var cognome : String
    var username : String
    var password : String
    var ruolo : String

    init {
        nome = vals[0]
        cognome = vals[1]
        username = vals[2]
        password = vals[3]
        ruolo = vals[4]
    }
}