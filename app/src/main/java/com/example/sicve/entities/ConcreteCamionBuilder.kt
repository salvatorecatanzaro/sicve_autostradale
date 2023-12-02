package com.example.sicve.entities


class ConcreteCamionBUilder(
    var camion: Camion,
    var targa: String,
    var numeroRuote: Int,
    var velocitaMassimaVeicolo: Int,
    var casaAutomobilistica: String,
): VeicoloBuilder {

    override fun targa(targa: String): VeicoloBuilder {
        this.targa = targa
        return this
    }

    override fun numeroRuote(numeroRuote: Int): VeicoloBuilder {
        this.numeroRuote = numeroRuote
        return this
    }

    override fun casaAutomobilistica(casaAutomobilistica: String): VeicoloBuilder{
        this.casaAutomobilistica = casaAutomobilistica
        return this
    }

    override fun velocitaMassimaVeicolo(velocitaMassimaVeicolo: Int): VeicoloBuilder {
        this.velocitaMassimaVeicolo = velocitaMassimaVeicolo
        return this
    }

    override fun build(): Veicolo {
        if(this.targa == "")
            throw Exception("Il campo targa è obbligatorio")
        camion = Camion(this.targa, this.numeroRuote, this.casaAutomobilistica, 4, this.velocitaMassimaVeicolo)
        return camion
    }
}