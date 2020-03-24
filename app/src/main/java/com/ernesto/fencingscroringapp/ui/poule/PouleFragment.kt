package com.ernesto.fencingscroringapp.ui.poule

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ernesto.fencingscroringapp.R
import kotlinx.android.synthetic.main.dialog_match_ended.view.*
import kotlinx.android.synthetic.main.fragment_poule.*

class PouleFragment : Fragment() {

    private lateinit var pouleViewModel: PouleViewModel

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poule, container, false)
    }

    override fun onStart() {
        super.onStart()
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        RedScoreTxtPoule.setOnClickListener {
            addPoint(RedScoreTxtPoule)
        }

        RedScoreTxtPoule.setOnLongClickListener {
            quitPoint(RedScoreTxtPoule)
        }

        GreenScoreTxtPoule.setOnClickListener {
            addPoint(GreenScoreTxtPoule)
        }

        GreenScoreTxtPoule.setOnLongClickListener {
            quitPoint(GreenScoreTxtPoule)
        }

        ChronoPoule.setOnClickListener {
            startStopChrono()
        }

        ChronoPoule.setOnChronometerTickListener {
            timeCount++
            if (timeCount == 182 && !inPriority) {
                startStopChrono()
                if (RedScoreTxtPoule.text.toString().toInt() == GreenScoreTxtPoule.text.toString().toInt()) {
                    drawPriority()
                } else {
                    showWinner()
                }
            } else if (timeCount == 62 && inPriority) {
                startStopChrono()
                showWinner()
            }
        }

        ResetAllBtnPoule.setOnClickListener {
            resetAll()
        }

        ResetTimeBtnPoule.setOnClickListener {
            resetTime()
        }

        DrawPriorityBtnPoule.setOnClickListener {
            drawPriority()
        }

        StartStopBtnPoule.setOnClickListener {
            startStopChrono()
        }

        LYellowCardBtnPoule.setOnClickListener {
            yellowCard('L')
        }

        LRedCardBtnPoule.setOnClickListener {
            redCard('L')
        }

        LBlackCardBtnPoule.setOnClickListener {
            blackCard('L')
        }

        RYellowCardBtnPoule.setOnClickListener {
            yellowCard('R')
        }

        RRedCardBtnPoule.setOnClickListener {
            redCard('R')
        }

        RBlackCardBtnPoule.setOnClickListener {
            blackCard('R')
        }

    }

    private fun drawPriority() {
        if (started) {
            startStopChrono()
        }
        this.prioritySide = priority.random()
        if (prioritySide == 'R') {
            RightPriorityTxtPoule.setTextColor(Color.parseColor("#FFEA00"))
        } else if (prioritySide == 'L') {
            LeftPriorityTxtPoule.setTextColor(Color.parseColor("#FFEA00"))
        }
        inPriority = true;
        resetTime()
    }

    private fun resetTime() {
        if (started) {
            startStopChrono()
        }
        ChronoPoule.base = SystemClock.elapsedRealtime()
        pauseOffSet = 0
        timeCount = 0
    }

    private fun resetAll() {
        if (started) {
            startStopChrono()
        }
        RedScoreTxtPoule.text = "00"
        GreenScoreTxtPoule.text = "00"
        ChronoPoule.base = SystemClock.elapsedRealtime()
        pauseOffSet = 0
        LeftPriorityTxtPoule.setTextColor(Color.parseColor("#72FFEA00"))
        RightPriorityTxtPoule.setTextColor(Color.parseColor("#72FFEA00"))
        rYellowCardCount = 0
        lYellowCardCount = 0
        started = false
        inPriority = false
        timeCount = 0
    }

    private fun blackCard(c: Char) {
        showBlackCardAcceptDialog()
        when (c) {
            'R' -> {
                showBlackCard()
            }
            'L' -> {
                showBlackCard()
            }
        }
    }

    private fun showBlackCardAcceptDialog() {
        var confirmDialog = AlertDialog.Builder(context!!)
            .setTitle("Black Card Dialog")
            .setMessage("You are going to show a Black Card. Are you sure?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                showBlackCard()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { _d, _ ->
                _d.cancel()
            })
            .create()
            .show()
    }

    private fun showBlackCard() {
        val blackCardDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_black_card, null)
        val dialogBuilder = AlertDialog.Builder(activity!!).setView(blackCardDialogView)
        val blackCardDialog = dialogBuilder.show()
        blackCardDialog.setCanceledOnTouchOutside(true)
        blackCardDialogView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                blackCardDialog.cancel()
            }
        })
    }

    private fun redCard(c: Char) {
        when (c) {
            'R' -> {
                addPoint(RedScoreTxtPoule)
                showRedCard()
            }
            'L' -> {
                addPoint(GreenScoreTxtPoule)
                showRedCard()
            }
        }
    }

    private fun showRedCard() {
        val redCardDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_red_card, null)
        val dialogBuilder = AlertDialog.Builder(activity!!).setView(redCardDialogView)
        val redCardDialog = dialogBuilder.show()
        redCardDialog.setCanceledOnTouchOutside(true)
        redCardDialogView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                redCardDialog.dismiss()
            }
        })
    }

    private fun yellowCard(c: Char) {
        when (c) {
            'R' -> {
                if (rYellowCardCount != 0) {
                    redCard(c)
                } else {
                    rYellowCardCount++
                    showYellowCard()
                }
            }
            'L' -> {
                if (lYellowCardCount != 0) {
                    redCard(c)
                } else {
                    showYellowCard()
                    lYellowCardCount++
                }
            }
        }
    }

    private fun showYellowCard() {
        val yellowCardDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_yellow_card, null)
        val dialogBuilder = AlertDialog.Builder(activity!!).setView(yellowCardDialogView)
        val yellowCardDialog = dialogBuilder.show()
        yellowCardDialog.setCanceledOnTouchOutside(true)
        yellowCardDialogView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                yellowCardDialog.dismiss()
            }
        })
    }

    private fun startStopChrono() {
        if (!started) {
            ChronoPoule.base = SystemClock.elapsedRealtime() - pauseOffSet
            ChronoPoule.start()
            started = true
        } else if (started) {
            ChronoPoule.stop()
            pauseOffSet = SystemClock.elapsedRealtime() - ChronoPoule.base!!
            started = false
        }

        StartStopBtnPoule.text = if (started) {
            "Stop"
        } else {
            "Start"
        }
    }

    private fun quitPoint(score: TextView): Boolean {
        if (!score.text.equals("00")) {
            if (started) {
                startStopChrono()
            }
            var actualScore: Int = score.text.toString().toInt()
            score.text = String.format("%1$02d", (actualScore - 1))
        }
        return true;
    }

    private fun addPoint(score: TextView) {
        if (started) {
            startStopChrono()
        }
        var actualScore: Int = score.text.toString().toInt()
        score.text = String.format("%1$02d", (actualScore + 1))
        if (isMatchEnded(actualScore + 1)) {
            showWinner()
        }
    }

    private fun isMatchEnded(score: Int): Boolean {
        return score == 5
    }

    private fun showWinner() {
        if (started) {
            startStopChrono()
        }

        //lockAll()


        val winnerDialogView =
            LayoutInflater.from(activity).inflate(R.layout.dialog_match_ended, null)
        val dialogBuilder = AlertDialog.Builder(activity!!).setView(winnerDialogView)
        val winnerDialog = dialogBuilder.show()
        winnerDialogView.RedScoreTxtMatch.text = RedScoreTxtPoule.text
        winnerDialogView.GreenScoreTxtMatch.text = GreenScoreTxtPoule.text
        winnerDialogView.AcceptBtnMatch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                winnerDialog.dismiss()
            }
        })
    }

}
