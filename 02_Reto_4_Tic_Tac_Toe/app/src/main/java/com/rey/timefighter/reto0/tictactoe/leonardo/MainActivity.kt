package com.rey.timefighter.reto0.tictactoe.leonardo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // Declaring Buttons
    internal lateinit var btn1: Button
    internal lateinit var btn2: Button
    internal lateinit var btn3: Button
    internal lateinit var btn4: Button
    internal lateinit var btn5: Button
    internal lateinit var btn6: Button
    internal lateinit var btn7: Button
    internal lateinit var btn8: Button
    internal lateinit var btn9: Button
    internal lateinit var btnReset: ImageButton
    internal lateinit var mBoardButtons: List<Button>

    // Declaring Labels
    internal lateinit var lblInfoGame: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Doing some settings
        setup()
    }
    fun setup(){
        // Linking a ids
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        mBoardButtons = listOf(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9)
        lblInfoGame = findViewById(R.id.lblInfo)
        btnReset = findViewById(R.id.btnReset)

        // Starting a new Game
        clearBoard()
    }
    fun clearBoard(){
        // clearing the buttons
        for(btn in mBoardButtons){
            btn.text = ""
            btn.isEnabled = true
            btn.isClickable = true
        }

        // Clearing the label info
        lblInfoGame.text = getString(R.string.msgYouTurn)
    }
    fun setMove(player:Char, location:Int){}
    fun getComputerMove():Int{return 0}
    fun checkForWinner():Int{return 0}
}