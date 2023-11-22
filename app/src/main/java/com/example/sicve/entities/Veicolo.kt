package com.example.sicve.entities

abstract class Veicolo(
    targa: String,
    numeroRuote: String,
    casaAutomobilistica: String,
    velocitaMassimaVeicolo: String,
    tipoVeicolo: String
) {
    abstract fun entraInAutostrada() : Unit

}