package com.example.sicve.utils

class Validation {
    fun validFields(mutableListOf: MutableList<String>): Boolean {
        for(s in mutableListOf)
        {
            if(s == "")
                return false
        }
        return true
    }
}