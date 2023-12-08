package com.example.sicve.entities

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ErrorDialog(var errorMessage: String, var context: Context) {
    init{
        showErrorDialog(context, errorMessage)
    }
    private fun showErrorDialog(context: Context, message: String) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.apply {
            setTitle("Errore")
            setMessage(message)
            setNegativeButton("Ok") { _, _ ->
            }
        }.create().show()

    }
}