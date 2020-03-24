package com.ernesto.fencingscroringapp.ui.poule

import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.lifecycle.ViewModel

class PouleViewModel : ViewModel() {

    val priority: CharArray = charArrayOf('L', 'R')

    var redScore: Int = 0

    var greenScore: Int = 0

    var started: Boolean = false

    var timeCount: Int = 0

    var pauseOffSet: Long = 0

    var inPriority: Boolean = false

    var lYellowCardCount: Int = 0

    var rYellowCardCount: Int = 0

    var prioritySide: Char = 'N'

    fun addPoint(score: TextView){

    }

    fun quitPoint(score: TextView){

    }

    fun startStopChrono(chrono: Chronometer){
        if(started){
            chrono.stop()
            pauseOffSet = SystemClock.elapsedRealtime() - chrono.base
            started = false
        }
        else{
            chrono.base = SystemClock.elapsedRealtime() - pauseOffSet
            chrono.start()
            started = true
        }
    }

    fun resetAll(){

    }

    fun resetTime(){

    }

    fun drawPriority(){

    }

    fun yellowCard(yellowCardBtn: Button){

    }

    fun redCard(redCardBtn: Button){

    }

    fun blackCard(blackCardBtn: Button){

    }

    fun isMatchEnded(score: Int): Boolean{
        return true;
    }

    fun showWinner(){

    }
}