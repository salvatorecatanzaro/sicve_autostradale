package com.example.sicve.entities

class Autovelox(
     var id: Int,
     var limiteVelocita: Int,
     var computer: Computer
) {
     var targaInfrazione: Map<String, String> = mutableMapOf()
}