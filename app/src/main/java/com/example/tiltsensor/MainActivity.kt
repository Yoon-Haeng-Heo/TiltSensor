package com.example.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener{

    private val sensorManager by lazy{
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    override fun onResume(){
        super.onResume()
        sensorManager.registerListener(this,
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private lateinit var tiltView : TiltView

    override fun onCreate(savedInstanceState: Bundle?) {
        //화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //화면이 가로 모드로 고정되게 하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiltView = TiltView(this)
        setContentView(tiltView)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {  //센서 정밀도가 변경되면 호출

    }

    override fun onSensorChanged(p0: SensorEvent?) {    //센서값이 변경되면 호출
        //values[0] : x축 값, 위로 기울이면 -10 ~ 0, 아래로 기울이면 0 ~ 10
        //values[1] : y축 값, 왼쪽으로 기울이면 -10 ~ 0 , 오른쪽으로 기울이면 0 ~ 10
        //values[2] : z축 값, 미사용
        p0?.let{
            //use log function for debug
            Log.d("MainActivity","onSensorChanged: x :" + "${p0.values[0]}, y: ${p0.values[1]}, z : ${p0.values[2]}")

            tiltView.onSensorEvent(p0)
        }

    }
}