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
        val username = findViewById<EditText>(R.id.stazione_entrata)
        val password = findViewById<EditText>(R.id.password)
        val buttonReset = findViewById<Button>(R.id.button_reset)
        val buttonSubmit = findViewById<Button>(R.id.button_submit)

        buttonReset.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            username.setText("")
            password.setText("")
        }

        buttonSubmit.setOnClickListener {
            // clearing user_name and password edit text views on reset button click
            val intent: Intent

            val auth : UserAuthentication = HardCodedCredentialsAuthentication(username=username.getText().toString(), password=password.getText().toString())
            val result = auth.login()
            val ruolo = result.values.iterator().next().ruolo

            intent = if(ruolo.lowercase() == "admin") Intent(this, AdminActivity::class.java) else Intent(this, UserActivity::class.java)
            intent.putExtra("username", username.getText().toString())

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


