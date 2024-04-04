package com.example.simpleboggle.ui

interface ActivityCallback {
    fun transmitScore(score:Int)
    fun resetGame()
    fun checkWord(word:String)
}