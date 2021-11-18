package com.rey.timefighter.reto0.tictactoe.leonardo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.view.View


class BoardView(context: Context,
                attrs: AttributeSet? = null,
                defStyleAttr: Int=0) : View(context,attrs,defStyleAttr) {
    // Values:
    public val GRID_WIDTH = 6

    // Variables:
    private lateinit var mHumanBitmap: Bitmap
    private lateinit var mComputerBitmap: Bitmap

    init {
        initialize()
    }

    public fun initialize(){
        mHumanBitmap = BitmapFactory.decodeResource(resources,R.drawable.x_img)
        mComputerBitmap = BitmapFactory.decodeResource(resources,R.drawable.o_img)
    }

    


}