package com.example.sicve


import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminView(name: String?) {
    Header(name)
    Body()
    NavBar(name)
}


@Composable
fun Header(name : String?) {
    Row (
        Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top
    )
    {
        Text(
            text = "Username: $name",
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Body() {
    Column (
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(name : String?) {
    val navigationController = rememberNavController()

    Scaffold(
        bottomBar = {
            val screens = listOf(
                AdminNavigationScreens.InsertScreen,
                AdminNavigationScreens.ModifyScreen,
                AdminNavigationScreens.InfoScreen
            )
            val navBackStackEntry by navigationController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBar{
                screens.forEach {screen->
                    AddItem(
                        screen = screen,
                        navDestination = currentDestination ,
                        navController = navigationController,
                        name = name)
                }
            }
        }
    )
    { it
        NavHost(
            navController = navigationController,
            startDestination = AdminNavigationScreens.InsertScreen.route + "/{$name}"
        )
        {
            composable(route = AdminNavigationScreens.InsertScreen.route + "/{$name}") {
                AdminInsertScreen(name)
            }
            composable(route = AdminNavigationScreens.ModifyScreen.route + "/{$name}") {
                AdminModifyScreen(name)
            }
            composable(route = AdminNavigationScreens.InfoScreen.route + "/{$name}") {
                AdminInfoScreen(name)
            }

        }
    }
}

// Extension of rowscope
@Composable
fun RowScope.AddItem(
    screen: AdminNavigationScreens,
    navDestination: NavDestination?,
    navController: NavHostController,
    name: String?
){
    NavigationBarItem(
        icon = {
            Icon(imageVector = screen.icon, contentDescription = " NavBar Icon")
        },
        label = {
            Text(text = screen.title)
        },
        selected = navDestination?.hierarchy?.any { it.route == screen.route } == true,
        onClick = {
            navController.navigate(screen.route + "/{$name}"){
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun AdminInsertScreen(name : String?) {
    Row() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(name)

        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Insertscreen",
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun AdminModifyScreen(name : String?) {
    Row() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(name)

        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Modify",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AdminInfoScreen(name : String?) {
    Row() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Header(name)

        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Row() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Info screen",
                fontWeight = FontWeight.Bold
            )
        }
    }

}