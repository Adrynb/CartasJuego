package com.example.juegoemparejar

class Carta(val id: Int, val imageId: Int) {
    var volteada: Boolean = false
    var matchOn: Boolean = false

    fun flip(){
        volteada = !volteada
    }

}