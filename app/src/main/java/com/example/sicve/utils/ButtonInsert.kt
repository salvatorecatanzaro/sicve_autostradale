package com.example.sicve.utils

import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import com.example.sicve.constants.AlertConstants
import com.example.sicve.constants.ErrorConstants
import com.example.sicve.entities.ErrorDialog
import com.example.sicve.entities.MessageDialog

class ButtonInsert: ButtonOperations {
    override fun saveOperation(view: View, formMap: MutableMap<String, Any?>, dbw: SQLiteDatabase) {
        val tmpList = mutableListOf<Int>()
        val tmpStazioneEntrata = (formMap["stazione_entrata"] as EditText).text.toString()
        val tmpStazioneUscita = (formMap["stazione_uscita"] as EditText).text.toString()
        val isChecked = (formMap["tutor_attivo"] as SwitchCompat).isChecked()
        val validation = Validation()
        if(!validation.validFields(mutableListOf(tmpStazioneEntrata, tmpStazioneUscita))){
            ErrorDialog(
                ErrorConstants.ALL_FIELD_MUST_BE_FILLED,
                view.context
            )
            return  // On error do nothing

        }
        val autoveloxList = formMap["limite_autovelox"] as MutableList<EditText>
        if(autoveloxList.size == 0)
        {
            ErrorDialog(
                ErrorConstants.NO_AUTOVELOX_ADDED,
                view.context,
            )
            return  // On error do nothing
        }
        for(autoveloxView in (formMap["limite_autovelox"] as MutableList<EditText>))
        {
            try {
                val autoveloxTmpTxtVal = autoveloxView.text.toString()
                if(autoveloxTmpTxtVal == "") {
                    ErrorDialog(
                        ErrorConstants.EMPTY_AUTOVELOX_FIELD,
                        view.context
                    )
                    return  // On error do nothing
                }
                tmpList.add(autoveloxTmpTxtVal.toInt())
            }catch(e: Exception){
                ErrorDialog(
                    ErrorConstants.NON_INT_AUTOVELOX_FIELD,
                    view.context
                )
                return  // On error do nothing
            }
        }
        try {
            DBHelper.insertTutor(
                dbw,
                tmpStazioneEntrata,
                tmpStazioneUscita,
                isChecked,
                tmpList
            )
        }
        catch (e: Exception)
        {
            ErrorDialog(
                ErrorConstants.ERROR_WHILE_SAVING_DATA,
                view.context
            )
            return  // On error do nothing
        }

        val message = MessageDialog(view.context, AlertConstants.SAVE_SUCCESS)
        message.showChoiceDialog()
    }

    override fun deleteOperation(view: View, formMap: MutableMap<String, Any?>, dbw: SQLiteDatabase) {
        TODO("Not yet implemented")
    }
}