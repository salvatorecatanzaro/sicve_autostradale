package com.example.sicve

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavigateApp() {
    val navigationController = rememberNavController()
    NavHost(startDestination=ApplicationView.LoginPage.route, navController=navigationController){
        composable(
            route=ApplicationView.LoginPage.route
        ){
            LoginComponent(navigationController = navigationController)
        }

        composable(
            route=ApplicationView.AdminView.route + "/{name}",
            arguments=listOf(
                navArgument("name"){
                    type = NavType.StringType
                    nullable = true
                }
            )
        ){
            name->
            AdminView(name = name.arguments?.getString("name"))

        }

    }
}