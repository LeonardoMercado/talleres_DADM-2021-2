package com.rey.timefighter.reto0.tictactoe.leonardo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class BoardView(context: Context,
                attrs: AttributeSet? = null,
                defStyleAttr: Int=0) : View(context,attrs,defStyleAttr) {
    // Values:
    public val GRID_WIDTH: Float = 6.0f

    // Variables:
    private lateinit var mHumanBitmap: Bitmap
    private lateinit var mComputerBitmap: Bitmap
    private lateinit var mPaint: Paint

    init {
        initialize()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    public fun initialize(){
        mHumanBitmap = BitmapFactory.decodeResource(resources,R.drawable.x_img)
        mComputerBitmap = BitmapFactory.decodeResource(resources,R.drawable.o_img)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Determine the width and height of the View
        var boardWidth: Float = width.toFloat()
        var boardHeight: Float = height.toFloat()

        // Make thick, ligh gray lines
        mPaint.color = Color.LTGRAY
        mPaint.strokeWidth = GRID_WIDTH

        // Draw the two vertical board lines
        var cellWidth: Float = boardHeight / 3;
        canvas?.drawLine(cellWidth, 0F, cellWidth, boardHeight,mPaint)
        canvas?.drawLine(cellWidth*2,0F,cellWidth*2,boardHeight,mPaint)

        // Draw the two horizontal lines
        var cellHeight: Float = boardWidth / 3
        canvas?.drawLine(cellHeight,0F, cellHeight,boardWidth,mPaint)
        canvas?.drawLine(cellHeight*2,0F,cellHeight*2,boardWidth,mPaint)

        



    }

    


}