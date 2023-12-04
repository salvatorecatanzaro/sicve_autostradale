package com.example.sicve.login

import com.example.sicve.entities.User

abstract class UserAuthentication{
    abstract fun login() : Map<String, User>
}