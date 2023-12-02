package com.example.sicve.entities

class Moto(
    var targa : String,
    var numeroRuote : Int,
    var casaAutomobilistica : String,
    var velocitaMassimaVeicolo : Int,
    var tipoVeicolo: String
) : Veicolo(targa, numeroRuote, casaAutomobilistica, velocitaMassimaVeicolo, tipoVeicolo) {
    override fun entraInAutostrada() {
        print("Il veicolo è entrato in autostrada")
    }

}