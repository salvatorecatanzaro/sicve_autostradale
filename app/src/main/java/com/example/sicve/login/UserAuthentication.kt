package com.example.sicve.login

import com.example.sicve.entities.User

abstract class UserAuthentication (username: String, password: String){
    abstract fun login() : Map<String, User>
}