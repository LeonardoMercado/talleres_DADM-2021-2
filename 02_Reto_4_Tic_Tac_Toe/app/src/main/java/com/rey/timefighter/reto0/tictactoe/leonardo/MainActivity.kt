package com.rey.timefighter.reto0.tictactoe.leonardo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // Declaring Buttons
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btnReset: ImageButton
    private lateinit var mBoardButtons: List<Button>

    // Declaring Labels
    private lateinit var lblInfoGame: TextView

    // Declaring other variables
    private var win = 0


    // Declaring other constans
    private val HUMAN_PLAYER = "X"
    private val COMPUTER_PLAYER = "O"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Doing some settings
        setup()
    }
    private fun setup(){
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
        btnReset.setOnClickListener {clearBoard()}
    }
    private fun clearBoard(){
        // clearing the buttons
        for(i in 0..mBoardButtons.lastIndex){
            mBoardButtons[i].text = ""
            mBoardButtons[i].isEnabled = true
            mBoardButtons[i].isClickable = true
            onClick(mBoardButtons[i],i)
        }
        // Clearing the label info
        lblInfoGame.text = getString(R.string.msgYouTurn)
    }
    private fun onClick(btn:Button, location:Int){
        btn.setOnClickListener {
            if(btn.isEnabled){
                setMove(HUMAN_PLAYER,location)
                var winner = checkForWinner()
                if(winner == 0){
                    lblInfoGame.text = getString(R.string.msgComputerTurn)
                    val moveComputer = getComputerMove()
                    setMove(COMPUTER_PLAYER,moveComputer)
                    winner = checkForWinner()
                }
                if(winner == 0){
                    lblInfoGame.text = getString(R.string.msgYouTurn)
                }else if(winner == 1){
                    lblInfoGame.text = getString(R.string.msgTie)
                }else if(winner == 2){
                    lblInfoGame.text = getText(R.string.msgWinUser)
                }else{
                    lblInfoGame.text = getText(R.string.msgWinComputer)
                    for(btn in mBoardButtons){
                        btn.isEnabled = false
                        btn.isClickable = false
                    }
                }
            }
        }
    }
    private fun setMove(player:String, location:Int){
        mBoardButtons[location].isEnabled = false
        mBoardButtons[location].isClickable = false
        mBoardButtons[location].text = player

        if(player == HUMAN_PLAYER){
            mBoardButtons[location].setTextColor(Color.parseColor("#00C800"))
        }else{
            mBoardButtons[location].setTextColor(Color.parseColor("#C80000"))
        }

    }
    private fun getComputerMove():Int{
        var move:Int
        // First see if there's a move O can make to win
        for(i in 0..8){
            if(mBoardButtons[i].text != HUMAN_PLAYER && mBoardButtons[i].text != COMPUTER_PLAYER){
                val curr = mBoardButtons[i].text
                mBoardButtons[i].text = COMPUTER_PLAYER
                if(checkForWinner() == 3){
                    //Toast.makeText(this,"moving to: ${i+1}",Toast.LENGTH_SHORT).show()
                    return i
                }else{
                    mBoardButtons[i].text = curr
                }
            }
        }
        // See if there's a move O can make to block X from winning
        for(i in 0..8){
            if(mBoardButtons[i].text != HUMAN_PLAYER && mBoardButtons[i].text != COMPUTER_PLAYER){
                val curr = mBoardButtons[i].text
                mBoardButtons[i].text = HUMAN_PLAYER
                if(checkForWinner() == 2){
                    //Toast.makeText(this,"moving to: ${i+1}",Toast.LENGTH_SHORT).show()
                    return i
                }else{
                    mBoardButtons[i].text = curr
                }
            }
        }
        // Generate random move
        do {
            move = (0..8).random()
        }while (mBoardButtons[move].text == HUMAN_PLAYER || mBoardButtons[move].text == COMPUTER_PLAYER)

        return move
    }
    private fun checkForWinner():Int{
        // Check for a winner.  Return
        //  0 if no winner or tie yet
        //  1 if it's a tie
        //  2 if X won
        //  3 if O won

        // Check horizontal wins----------------------------------------
        for(i in 0..6 step 3){
            if(mBoardButtons[i].text == HUMAN_PLAYER &&
               mBoardButtons[i+1].text == HUMAN_PLAYER &&
               mBoardButtons[i+2].text == HUMAN_PLAYER){
                return 2
            }
            if(mBoardButtons[i].text == COMPUTER_PLAYER &&
               mBoardButtons[i+1].text == COMPUTER_PLAYER &&
               mBoardButtons[i+2].text == COMPUTER_PLAYER){
                return 3
            }
        }
        //--------------------------------------------------------------


        // Check vertical wins-----------------------------------------
        for(i in 0..2){
            if(mBoardButtons[i].text == HUMAN_PLAYER &&
                mBoardButtons[i+3].text == HUMAN_PLAYER &&
                mBoardButtons[i+6].text == HUMAN_PLAYER){
                return 2
            }
            if(mBoardButtons[i].text == COMPUTER_PLAYER &&
                mBoardButtons[i+3].text == COMPUTER_PLAYER &&
                mBoardButtons[i+6].text == COMPUTER_PLAYER){
                return 3
            }
        }
        //--------------------------------------------------------------


        // Check for diagonal wins------------------------------------------
        if((mBoardButtons[0].text == HUMAN_PLAYER &&
            mBoardButtons[4].text == HUMAN_PLAYER &&
            mBoardButtons[8].text == HUMAN_PLAYER) ||
           (mBoardButtons[2].text == HUMAN_PLAYER &&
            mBoardButtons[4].text == HUMAN_PLAYER &&
            mBoardButtons[6].text == HUMAN_PLAYER)){
            return 2
        }
        if((mBoardButtons[0].text == COMPUTER_PLAYER &&
            mBoardButtons[4].text == COMPUTER_PLAYER &&
            mBoardButtons[8].text == COMPUTER_PLAYER) ||
           (mBoardButtons[2].text == COMPUTER_PLAYER &&
            mBoardButtons[4].text == COMPUTER_PLAYER &&
            mBoardButtons[6].text == COMPUTER_PLAYER)){
            return 3
        }
        //--------------------------------------------------------------

        // Check for tie
        for(i in 0..8){
            // If we find a spot, then no one has won yet
            if(mBoardButtons[i].text != HUMAN_PLAYER && mBoardButtons[i].text != COMPUTER_PLAYER){
                return 0
            }
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1
    }
}