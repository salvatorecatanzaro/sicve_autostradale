package com.example.sicve.buttons

import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import com.example.sicve.constants.AlertConstants
import com.example.sicve.constants.ErrorConstants
import com.example.sicve.entities.ConcreteAutoBuilder
import com.example.sicve.entities.ConcreteCamionBuilder
import com.example.sicve.entities.ConcreteMotoBuilder
import com.example.sicve.entities.ErrorDialog
import com.example.sicve.entities.MessageDialog
import com.example.sicve.entities.Veicolo
import com.example.sicve.entities.VeicoloBuilder
import com.example.sicve.utils.DBHelper
import com.example.sicve.utils.Validation

class ButtonSaveMyVehicle {
    fun saveMyVehicle(formMap:MutableMap<String, Any>, dbw: SQLiteDatabase, username: String, view: View) {
        val casaAutomobilisticaViewEdit: EditText?
        val targaEditTextView: EditText?
        val velocitaMassimaEditText: EditText?
        val spinner: Spinner?
        try
        {
            casaAutomobilisticaViewEdit = formMap["casa_automobilistica"] as EditText
            targaEditTextView = formMap["targa"] as EditText
            velocitaMassimaEditText = formMap["velocita_massima"] as EditText
            spinner = formMap["spinner"] as Spinner
        }
        catch(e: Exception)
        {
            ErrorDialog(
                ErrorConstants.ERROR_WHILE_SAVING_DATA,
                view.context
            )
            return  // On error do nothing
        }
        try
        {
            DBHelper.deleteVehicleByFk(dbw, username)
        }
        catch(e: Exception)
        {
            ErrorDialog(
                ErrorConstants.ERROR_WHILE_SAVING_DATA,
                view.context
            )

            return  // On error do nothing
        }

        val casaAutomobilistica = casaAutomobilisticaViewEdit.text.toString()
        val targa = targaEditTextView.text.toString()
        val velocitaMassimaVeicolo: Int
        try
        {
            velocitaMassimaVeicolo = velocitaMassimaEditText.text.toString().toInt()
        }
        catch (e: Exception)
        {
            ErrorDialog(
                ErrorConstants.NON_INT_VEL_MAX_FIELD,
                view.context
            )
            return  // On error do nothing
        }

        val veicolo: Veicolo
        var veicoloBuilder: VeicoloBuilder = ConcreteAutoBuilder()

        val validateForm = Validation()
        if(!validateForm.validFields(mutableListOf(casaAutomobilistica, targa, velocitaMassimaVeicolo.toString(), )))
        {
            ErrorDialog(
                ErrorConstants.ALL_FIELD_MUST_BE_FILLED,
                view.context
            )

            return  // On error do nothing
        }
        if(spinner.selectedItem.toString() == "AUTO"){
            veicoloBuilder
                .numeroRuote(4)
                .casaAutomobilistica(casaAutomobilistica)
                .targa(targa)
                .velocitaMassimaVeicolo(velocitaMassimaVeicolo)
        }
        else if(spinner.selectedItem.toString() == "MOTO"){
            veicoloBuilder = ConcreteMotoBuilder()

            veicoloBuilder
                .numeroRuote(2)
                .casaAutomobilistica(casaAutomobilistica)
                .targa(targa)
                .velocitaMassimaVeicolo(velocitaMassimaVeicolo)
        }
        else if(spinner.selectedItem.toString() == "CAMION"){
            veicoloBuilder = ConcreteCamionBuilder()
            veicoloBuilder
                .numeroRuote(4)
                .casaAutomobilistica(casaAutomobilistica)
                .targa(targa)
                .velocitaMassimaVeicolo(velocitaMassimaVeicolo)
        }
        veicolo = veicoloBuilder.build()
        veicolo.saveVehicle(dbw, username, view)
        val alert = MessageDialog(view.context, AlertConstants.SAVE_SUCCESS)
        alert.showChoiceDialog()
    }

}