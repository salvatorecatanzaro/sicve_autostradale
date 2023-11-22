package com.example.sicve.entities

class Tutor(
    var attivo: Boolean,
    var listaAutovelox: MutableList<Autovelox>,
    var stazioneEntrata: String,
    var stazioneUscita: String
) {
}