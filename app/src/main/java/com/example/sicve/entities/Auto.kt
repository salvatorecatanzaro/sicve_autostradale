package com.example.sicve.entities

class Auto(
    targa : String,
    numeroRuote : String,
    casaAutomobilistica : String,
    numeroPorte : String,
    velocitaMassimaVeicolo : String,
    tipoVeicolo: String
) : Veicolo(targa, numeroRuote, casaAutomobilistica, velocitaMassimaVeicolo, tipoVeicolo) {
    override fun entraInAutostrada() {
        print("Il veicolo è entrato in autostrada")
    }

}