package com.example.sicve

sealed class ApplicationView (val route : String) {
    object LoginPage : ApplicationView(route="login")
    object AdminView : ApplicationView(route="admin_view")
    object UserView : ApplicationView(route="user_veiw")
}