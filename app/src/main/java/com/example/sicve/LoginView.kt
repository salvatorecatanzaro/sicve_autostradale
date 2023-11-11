package com.example.sicve

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.sicve.login.HardCodedCredentialsAuthentication
import com.example.sicve.login.UserAuthentication
import com.example.sicve.ui.theme.SicveTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponent(navigationController: NavController) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var loginError by remember {
        mutableStateOf(false)
    }
    var loginErrorMessage by remember {
        mutableStateOf("false")
    }


    Column (
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        TextField(
            value = username,
            onValueChange = { userinput ->
                username = userinput
            },
            label = { Text(text = "Username") },
        )
        TextField(
            value = password,
            onValueChange = { userinput ->
                password = userinput
            },
            label = { Text(text = "Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            val auth : UserAuthentication = HardCodedCredentialsAuthentication(username, password)
            val result = auth.login()
            if(result.contains("Success")) {
                navigationController.navigate(route = ApplicationView.AdminView.route + "/$username")
            }
            else {
                loginError = true
                loginErrorMessage = result.entries.iterator().next().key

            }
        }){
            Text(text = "login")
        }

        if(loginError)
            Text(
                text = loginErrorMessage,
                color = Color.Red
            )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SicveTheme {

    }
}

