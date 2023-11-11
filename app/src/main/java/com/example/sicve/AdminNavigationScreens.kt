package com.example.sicve

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AdminNavigationScreens (
    var route : String,
    var icon: ImageVector,
    var title: String
)
{
    object InsertScreen : AdminNavigationScreens(route = "insert", icon = Icons.Default.AddCircle, title = "home")
    object ModifyScreen : AdminNavigationScreens(route = "modify", icon = Icons.Default.Edit, title = "modify")
    object InfoScreen : AdminNavigationScreens(route = "info", icon = Icons.Default.Info, title = "info")
}