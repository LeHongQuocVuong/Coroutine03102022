package com.example.coroutine03102022

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var jobRed:Job? = null
    var jobGreen:Job? = null
    var jobBlue:Job? = null
    var isFinished:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button:Button = findViewById(R.id.btn_Start)
        button.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startRunning()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startRunning(){
        resetRunning()
        jobRed = MainScope().launch {
            val progressRed:ProgressBar = findViewById(R.id.progress_Red)
            running(progressRed)
        }
        jobGreen = MainScope().launch {
            val progressGreen:ProgressBar = findViewById(R.id.progress_Green)
            running(progressGreen)
        }
        jobBlue = MainScope().launch {
            val progressBlue:ProgressBar = findViewById(R.id.progress_Blue)
            running(progressBlue)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun running(progress: ProgressBar){
        progress.progress=0
        progress.max=1000
        while(progress.progress<progress.max && !isFinished){
            progress.progress+=(1..50).random()
            delay(15)
        }
        if(!isFinished){
            isFinished = true
            Toast.makeText(this,"${progress.tooltipText} chien thang!",Toast.LENGTH_LONG).show()
        }
    }

    fun resetRunning(){
        isFinished = false
        jobRed?.cancel()
        jobGreen?.cancel()
        jobBlue?.cancel()
    }

    override fun onDestroy() {
        resetRunning()
        super.onDestroy()
    }
}