package com.example.sicve

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        getSupportActionBar()?.hide()
        // get reference to all views
        var username = findViewById<EditText>(R.id.et_user_name)
        var et_password = findViewById<EditText>(R.id.et_password)
        var btn_reset = findViewById<Button>(R.id.btn_reset)
        var btn_submit = findViewById<Button>(R.id.btn_submit)

        btn_reset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            username.setText("")
            et_password.setText("")
        }
        btn_submit.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            val intent = Intent(this, Admin::class.java)
            intent.putExtra("username", username.getText().toString())
            startActivity(intent)
        }
    }
}


