package com.example.sicve.entities

class Moto(
    targa : String,
    numeroRuote : String,
    casaAutomobilistica : String,
    velocitaMassimaVeicolo : String,
    tipoVeicolo: String
) : Veicolo(targa, numeroRuote, casaAutomobilistica, velocitaMassimaVeicolo, tipoVeicolo) {
    override fun entraInAutostrada() {
        print("Il veicolo è entrato in autostrada")
    }

}