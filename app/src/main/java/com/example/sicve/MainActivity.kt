package com.example.sicve

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sicve.login.HardCodedCredentialsAuthentication
import com.example.sicve.login.UserAuthentication

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getSupportActionBar()?.hide()
        // get reference to all views
        var username = findViewById<EditText>(R.id.stazione_entrata)
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
            val auth : UserAuthentication = HardCodedCredentialsAuthentication(username=username.getText().toString(), password=password.getText().toString())
            val result = auth.login()
            val errorText = result.entries.iterator().next().key
            if(result.contains("Success")) {
                startActivity(intent)
            }
            else {
                val errorTextview = findViewById<TextView>(R.id.error_message)
                errorTextview.setText(errorText)
                errorTextview.setVisibility(View.VISIBLE)

            }
        }
    }
}


