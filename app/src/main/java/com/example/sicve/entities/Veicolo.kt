package com.example.sicve.entities

abstract class Veicolo(
    targa: String,
    numeroRuote: Int,
    casaAutomobilistica: String,
    velocitaMassimaVeicolo: Int,
    tipoVeicolo: String
) {
    abstract fun entraInAutostrada() : Unit

}