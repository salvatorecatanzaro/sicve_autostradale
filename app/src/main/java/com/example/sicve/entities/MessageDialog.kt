package com.example.sicve.entities

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MessageDialog(var context:Context, var message: String) {

    fun showChoiceDialog(): Boolean{
        val alertDialog = AlertDialog.Builder(context)
        var result = false
        alertDialog.apply {
            setTitle("Messaggio")
            setMessage(message)
            setPositiveButton("Ok") { _: DialogInterface?, _: Int ->
                 result = true
            }

        }.create().show()
        return result
    }
}