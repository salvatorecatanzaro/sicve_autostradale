package com.example.sicve.constants

class ErrorConstants {
    companion object{
        val VEL_MAX_TOO_LOW = "La velocità massima del veicolo creato deve essere >= 80"
        val CREATE_VEHICLE_FIRST = "Crea un veicolo con cui percorrere le tratte disponibili"
        val NON_INT_VEL_MAX_FIELD = "Il campo velocita massima deve essere un intero"
        val ERROR_WHILE_SAVING_DATA: String = "Si è verificato un'errore durante il salvataggio dei dati"
        val PASSWORD_MUST_MATCH: String = "I campi password non sono uguali"
        val NO_AUTOVELOX_ADDED = "Aggiungi un autovelox al tutor"
        val ALL_FIELD_MUST_BE_FILLED = "Non possono esserci campi vuoti"
        val EMPTY_AUTOVELOX_FIELD = "I campi Limite di velocità non possono essere vuoti"
        val NON_INT_AUTOVELOX_FIELD = "I campi Limite di velocità devono essere valori interi"


    }

}