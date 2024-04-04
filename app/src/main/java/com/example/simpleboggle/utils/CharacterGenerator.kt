package com.example.simpleboggle.utils

class CharacterGenerator {
    fun generateRandomChar(): Char {
        return ('A'..'Z').random()
    }
}