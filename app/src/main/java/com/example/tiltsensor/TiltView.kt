package com.example.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {
    private val greenPaint : Paint = Paint()
    private val blackPaint : Paint = Paint()

    private var cX : Float = 0f
    private var cY : Float = 0f

    private var xCoord : Float = 0f
    private var yCoord : Float = 0f


    init{
        //green paint
        greenPaint.color = Color.GREEN
        //black guide line paint
        blackPaint.style = Paint.Style.STROKE
    }
    override fun onSizeChanged(w: Int, h: Int,oldw: Int,oldh: Int){
        cX = w/ 2f
        cY = h /2f
    }
    override fun onDraw(canvas : Canvas?){
        //draw outside circle
        canvas?.drawCircle(cX,cY,100f,blackPaint)
        //draw green circle
        canvas?.drawCircle(cX + xCoord,cY+ yCoord,100f,greenPaint)
        //center crossLine
        canvas?.drawLine(cX-20,cY,cX+20,cY,blackPaint)
        canvas?.drawLine(cX,cY-20,cX,cY+20,blackPaint)
    }

    fun onSensorEvent(event : SensorEvent) {
        //화면을 가로로 돌렸으므로 x축과 y축을 서로 바꿈
        yCoord = event.values[0]*20
        xCoord = event.values[1]*20
        //20을 곱하는 이유는 센서값의 범위를 그대로 사용하면 너무 범위가 적어서
        // 녹색원의 움직임을 알아보기 어렵기 때문

        //onDraw()메소드 다시 호출
        invalidate()
    }
}