package com.example.sicve.buttons

import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import com.example.sicve.constants.AlertConstants
import com.example.sicve.constants.ErrorConstants
import com.example.sicve.entities.ErrorDialog
import com.example.sicve.entities.MessageDialog
import com.example.sicve.entities.Tutor
import com.example.sicve.utils.DBHelper
import com.example.sicve.utils.Validation

class ButtonTransit {
    fun autoveloxChecks(formMap: MutableMap<String, Any>, dbw: SQLiteDatabase, view: View){
        //TODO Se l'utente non ha un veicolo associato non deve percorrere la tratta

        var multe = mutableListOf<String>()
        var messaggiAttivi: SwitchCompat? = null
        var targa: String? = null
        var tipoVeicolo: String? = null
        var tutor: Tutor? = null
        var velMassima: Int? = null
        try
        {
            messaggiAttivi = (formMap["messaggi_attivi"] as SwitchCompat)
            targa = (formMap["targa"] as String)
            tipoVeicolo = (formMap["tipo_veicolo"] as String)
            tutor = (formMap["tutor"] as Tutor)
            velMassima = (formMap["vel_massima"] as Int)
        }
        catch(e: Exception)
        {
            ErrorDialog(
                ErrorConstants.ERROR_WHILE_SAVING_DATA,
                view.context
            )
        }

        val validation = Validation()
        if(!validation.validFields(mutableListOf(targa!!, velMassima.toString())))
        {
            ErrorDialog(
                ErrorConstants.ERROR_WHILE_SAVING_DATA,
                view.context
            )
        }

        if(messaggiAttivi!!.isChecked)
            DBHelper.insertMessage(targa!!, tipoVeicolo!!, tutor!!, dbw, "")

        var autoveloxCount = 0
        var autoveloxSum = 0
        var velMedia = 0
        for(autovelox in tutor!!.listaAutovelox)
        {
            val velCorrente = (80..velMassima!!).shuffled().last()
            velMedia +=  velCorrente
            if( velCorrente > autovelox.limiteVelocita) {
                val msg = "Multa per aver superato il limite di velocità. velocita corrente: $velCorrente all'autovelox con id ${autovelox.id}"
                multe.add(msg)
                DBHelper.insertMessage(targa!!, tipoVeicolo!!, tutor, dbw, msg)
            }
            autoveloxCount += 1
            autoveloxSum += autovelox.limiteVelocita
            if(tutor.listaAutovelox.size == autoveloxCount){
                val velocitaMediaLimite = autoveloxSum / tutor.listaAutovelox.size
                val velocitaMediaVeicolo = velMedia / tutor.listaAutovelox.size
                if(velocitaMediaLimite < velocitaMediaVeicolo)
                    autovelox.computer.listaMulte.add("Multa per aver superato il limite di velocita media. Velocita media: $velocitaMediaVeicolo velocita media tutor $velocitaMediaLimite")

            }
            autovelox.computer.salvaInfrazioni(autovelox.computer.id, dbw)
        }
        val alert = MessageDialog(view.context, AlertConstants.SAVE_SUCCESS)
        alert.showChoiceDialog()
    }
}