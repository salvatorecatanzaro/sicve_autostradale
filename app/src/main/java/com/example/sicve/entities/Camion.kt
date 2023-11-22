package com.example.sicve.entities

class Camion(
    var targa : String,
    var numeroRuote : String,
    var casaAutomobilistica : String,
    var numeroPorte : String,
    var velocitaMassimaVeicolo : String,
    var tipoVeicolo: String
) : Veicolo(targa, numeroRuote, casaAutomobilistica, velocitaMassimaVeicolo, tipoVeicolo) {
    override fun entraInAutostrada() {
        print("Il veicolo è entrato in autostrada")
    }

}