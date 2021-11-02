package com.rey.timefighter.reto0.tictactoe.leonardo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Doing some settings
        setup()
    }
    fun setup(){

        val game = TicTacToeGame()

    }
    fun clearBoard(){}
    fun setMove(player:Char, location:Int){}
    fun getComputerMove():Int{return 0}
    fun checkForWinner():Int{return 0}
}