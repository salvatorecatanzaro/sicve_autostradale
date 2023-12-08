package com.example.sicve.entities

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MessageDialog(var context:Context, var message: String) {
    var result: Boolean
    init{
        result = showChoiceDialog(context, message)
    }
    private fun showChoiceDialog(context: Context, message: String) : Boolean{
        val alertDialog = AlertDialog.Builder(context)
        var choice = false
        alertDialog.apply {
            setTitle("Error")
            setMessage("$message")
            setPositiveButton("Positive") { _: DialogInterface?, _: Int ->
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show()
                choice = true
            }
            setNegativeButton("Negative") { _, _ ->
                Toast.makeText(context, "Negative", Toast.LENGTH_SHORT).show()
            }
            setNeutralButton("Neutral") { _, _ ->
                Toast.makeText(context, "Neutral", Toast.LENGTH_SHORT).show()
            }
            setOnDismissListener {
                Toast.makeText(context, "Hello!!!", Toast.LENGTH_SHORT).show()
            }

        }.create().show()
        return choice
    }
}