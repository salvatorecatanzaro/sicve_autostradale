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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.sicve.ui.theme.SicveTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginComponent(name: String, modifier: Modifier = Modifier, navigationController: NavController) {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var incorrectPassword by remember {
        mutableStateOf(false)
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
            if(true)
                incorrectPassword = true
            else
               navigationController.navigate(route=ApplicationView.AdminView.route + "/$username")
        }){
            Text(text = "login")
        }

        if(incorrectPassword)
            Text(
                text = "The provided password is not correct",
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

