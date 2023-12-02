package com.example.sicve.entities

interface VeicoloBuilder {

    fun targa(targa: String): VeicoloBuilder;
    fun numeroRuote(numeroRuote: Int): VeicoloBuilder;
    fun casaAutomobilistica(casaAutomobilistica: String): VeicoloBuilder;
    fun velocitaMassimaVeicolo(velocitaMassimaVeicolo: Int): VeicoloBuilder;

    fun build(): Veicolo;
}