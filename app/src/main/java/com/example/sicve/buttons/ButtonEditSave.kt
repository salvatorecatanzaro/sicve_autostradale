package com.example.sicve.buttons

import android.database.sqlite.SQLiteDatabase
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.example.sicve.constants.AlertConstants
import com.example.sicve.constants.ErrorConstants
import com.example.sicve.entities.ErrorDialog
import com.example.sicve.entities.HighwayBlock
import com.example.sicve.entities.MessageDialog
import com.example.sicve.utils.DBHelper
import com.example.sicve.utils.Validation

class ButtonEditSave : ButtonOperations
{
    fun saveOperationWrapper(){

    }
    override fun saveOperation(view: View, formMap: MutableMap<String, Any?>, dbw: SQLiteDatabase) {
        TODO("Not yet implemented")
    }

    override fun deleteOperation(
        view: View,
        formMap: MutableMap<String, Any?>,
        dbw: SQLiteDatabase
    ) {
        TODO("Not yet implemented")
    }

    fun updateTutor(highWayBlock: HighwayBlock, tutorMap: MutableMap<String, Any>?, view: View, dbw: SQLiteDatabase) {
        val stazioneEntrata = (tutorMap!!["stazione_entrata"] as EditText).text.toString()
        val stazioneUscita = (tutorMap["stazione_uscita"] as EditText).text.toString()
        val tutorAttivo = (tutorMap["tutor_attivo"] as SwitchCompat).isChecked()

        val validation = Validation()
        if(!validation.validFields(mutableListOf(stazioneEntrata, stazioneUscita)))
        {
            ErrorDialog(
                ErrorConstants.ALL_FIELD_MUST_BE_FILLED,
                view.context
            )
            return  // On error do nothing
        }
        highWayBlock.tutor!!.stazioneEntrata = stazioneEntrata
        highWayBlock.tutor!!.stazioneUscita = stazioneUscita
        highWayBlock.tutor!!.attivo = tutorAttivo

        for(autovelox in highWayBlock.tutor!!.listaAutovelox){
            if(tutorMap[autovelox.id.toString()] == null)
            {
                ErrorDialog(
                    ErrorConstants.ERROR_WHILE_SAVING_DATA,
                    view.context
                )
                return
            }
            var currentAutoveloxLimit: Int
            try
            {
                currentAutoveloxLimit = (tutorMap[autovelox.id.toString()] as TextView).text.toString().toInt()
            }
            catch (e: Exception)
            {
                ErrorDialog(
                    ErrorConstants.NON_INT_AUTOVELOX_FIELD,
                    view.context
                )
                return  // On error do nothing
            }

            autovelox.limiteVelocita = currentAutoveloxLimit
        }
        try
        {
            DBHelper.updateTutorModifyView(dbw, highWayBlock.tutor!!)
        }
        catch (e: Exception)
        {
            ErrorDialog(
                ErrorConstants.ERROR_WHILE_SAVING_DATA,
                view.context
            )
            return  // On error do nothing
        }
        val alert = MessageDialog(view.context, AlertConstants.SAVE_SUCCESS)
        alert.showChoiceDialog()
    }

}