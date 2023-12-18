package com.example.sicve.buttons

import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import com.example.sicve.constants.AlertConstants
import com.example.sicve.constants.ErrorConstants
import com.example.sicve.entities.ErrorDialog
import com.example.sicve.entities.MessageDialog
import com.example.sicve.entities.User
import com.example.sicve.utils.DBHelper
import com.example.sicve.utils.Validation

class ButtonRegister: ButtonOperations {
    override fun saveOperation(
        view: View,
        formMap: MutableMap<String, Any?>,
        dbw: SQLiteDatabase
    ) {

        val tmpUsername = (formMap["username"] as EditText).text.toString()
        val tmpName = (formMap["nome"] as EditText).text.toString()
        val tmpSurname = (formMap["cognome"] as EditText).text.toString()
        val tmpPassword = (formMap["password"] as EditText).text.toString()
        val tmpRepeatPassword = (formMap["repeat_password"] as EditText).text.toString()
        val tmpRole = (formMap["ruolo"] as Spinner).selectedItem.toString()
        val validation = Validation()
        if(!validation.validFields(mutableListOf(tmpUsername, tmpName, tmpSurname, tmpPassword, tmpRepeatPassword, tmpRole)))
        {
            ErrorDialog(
                ErrorConstants.ALL_FIELD_MUST_BE_FILLED,
                view.context
            )
            return  // On error do nothing
        }
        if(tmpPassword != tmpRepeatPassword)
        {
            ErrorDialog(
                ErrorConstants.PASSWORD_MUST_MATCH,
                view.context
            )
            return  // On error do nothing
        }
        val user = User(listOf(tmpName, tmpSurname, tmpUsername, tmpPassword, tmpRole))
        try
        {
            DBHelper.insertUser(dbw, user)
        }
        catch (e: Exception)
        {
            ErrorDialog(
                ErrorConstants.ERROR_WHILE_SAVING_DATA,
                view.context
            )
            return
        }
        val alert = MessageDialog(view.context, AlertConstants.SAVE_SUCCESS)
        alert.showChoiceDialog()
    }

    override fun deleteOperation(
        view: View,
        insertTutorMap: MutableMap<String, Any?>,
        dbw: SQLiteDatabase
    ) {
        TODO("Not yet implemented")
    }
}