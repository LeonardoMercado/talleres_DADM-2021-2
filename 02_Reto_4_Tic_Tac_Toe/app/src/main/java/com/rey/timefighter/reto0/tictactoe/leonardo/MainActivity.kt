package com.rey.timefighter.reto0.tictactoe.leonardo

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.rey.timefighter.reto0.tictactoe.leonardo.databinding.ActivityMainBinding
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    // Declaring List of Buttons
    private lateinit var mBoardButtons: List<Button>

    // Declaring other variables
    private var humanScore = 0
    private var tieScore = 0
    private var androidScore = 0
    private var flagdifficulty:Difficulty = Difficulty.EASY
    // Declaring other constans
    private val HUMAN_PLAYER = "X"
    private val COMPUTER_PLAYER = "O"
    // For binding
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Doing some settings-+
        setup()

        // Menu actions
        binding.bottomNavigationView.setOnItemSelectedListener { it ->
            when(it.itemId){
                R.id.reset -> {
                    clearBoard()
                    resetScore()
                    true
                }
                R.id.newGame -> {
                    clearBoard() // clear the board
                    true
                }
                R.id.difficulty -> {
                    cambiarDificultad()
                    true
                }
                R.id.exit -> {
                    cerrarApp()
                    true
                }
                else -> false
            }
        }
    }
    private fun setup(){
        // Filling the list of buttons
        mBoardButtons = listOf(binding.btn1,
                               binding.btn2,
                               binding.btn3,
                               binding.btn4,
                               binding.btn5,
                               binding.btn6,
                               binding.btn7,
                               binding.btn8,
                               binding.btn9)
        // Starting a new Game
        clearBoard()
        resetScore()
        //btnReset.setOnClickListener {clearBoard()}
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
        binding.lblInfo.text = getString(R.string.msgYouTurn)
    }
    private fun onClick(btn:Button, location:Int){
        btn.setOnClickListener {
            if(btn.isEnabled){
                setMove(HUMAN_PLAYER,location)
                var winner = checkForWinner()
                if(winner == 0){
                    binding.lblInfo.text = getString(R.string.msgComputerTurn)
                    val moveComputer = getComputerMove()
                    setMove(COMPUTER_PLAYER,moveComputer)
                    winner = checkForWinner()
                }
                if(winner == 0){
                    binding.lblInfo.text = getString(R.string.msgYouTurn)
                }else if(winner == 1){
                    binding.lblInfo.text = getString(R.string.msgTie)
                    tieScore++
                    binding.lblScoreTies.text = "Ties:$tieScore"
                    blockAllSpace()
                }else if(winner == 2){
                    binding.lblInfo.text = getText(R.string.msgWinUser)
                    humanScore++
                    blockAllSpace()
                    binding.lblScoreHuman.text = "Human:$humanScore"
                    blockAllSpace()
                }else{
                    binding.lblInfo.text = getText(R.string.msgWinComputer)
                    androidScore++
                    binding.lblScoreAndroid.text = "Android:$androidScore"
                    blockAllSpace()
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
        if(flagdifficulty == Difficulty.EASY){
            // Generate random move
            do {
                move = (0..8).random()
            }while (mBoardButtons[move].text == HUMAN_PLAYER || mBoardButtons[move].text == COMPUTER_PLAYER)
            return move
        }else if(flagdifficulty == Difficulty.HARDER){
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
            // Generate random move
            do {
                move = (0..8).random()
            }while (mBoardButtons[move].text == HUMAN_PLAYER || mBoardButtons[move].text == COMPUTER_PLAYER)
            return move
        }else if(flagdifficulty == Difficulty.EXPERT){
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
        return -1
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
    private fun resetScore(){
        binding.lblScoreHuman.text = getString(R.string.lblHumanScore)
        binding.lblScoreTies.text = getString(R.string.lblTiesScore)
        binding.lblScoreAndroid.text = getString(R.string.lblAndroidScore)
    }
    private fun mensajes(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
    enum class Difficulty {
        EASY, HARDER, EXPERT
    }
    private fun cambiarDificultad(){
        val options = arrayOf(getString(R.string.lblEasy),
                              getString(R.string.lblHarder),
                              getString(R.string.lblExpert))
        val optionDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.titleDifficulty))
            .setSingleChoiceItems(options,flagdifficulty.ordinal){dialogInterface, i ->
                when(i){
                    0 -> flagdifficulty = Difficulty.EASY
                    1 -> flagdifficulty = Difficulty.HARDER
                    2 -> flagdifficulty = Difficulty.EXPERT
                }
                clearBoard()
                dialogInterface.dismiss()
            }.create()
        optionDialog.show()
    }
    private fun cerrarApp(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.lblTitleCloseApp))
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int -> finish() }
        builder.setNegativeButton("No") { _: DialogInterface, _: Int -> }
        builder.show()
    }
    override fun onBackPressed() {
        cerrarApp()
    }
    private fun blockAllSpace(){
        for(btn in mBoardButtons){
            btn.isEnabled = false
            btn.isClickable = false
        }
    }
}