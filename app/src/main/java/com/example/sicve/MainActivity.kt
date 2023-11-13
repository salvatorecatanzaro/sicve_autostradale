package com.example.sicve

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getSupportActionBar()?.hide()
        // get reference to all views
        var username = findViewById<EditText>(R.id.username)
        var password = findViewById<EditText>(R.id.password)
        var buttonReset = findViewById<Button>(R.id.button_reset)
        var buttonSubmit = findViewById<Button>(R.id.button_submit)

        buttonReset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            username.setText("")
            password.setText("")
        }
        buttonSubmit.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            val intent = Intent(this, AdminActivity::class.java)
            intent.putExtra("username", username.getText().toString())
            startActivity(intent)
        }
    }
}


