package com.sndo9.robert.nim

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

import java.util.ArrayList

import android.R.attr.fragment
//import android.support.v7.app.AppCompatActivity
import com.sndo9.robert.nim.R.id.instructionPage

/**
 * This activity creates the game and decides if the AI is to be used
 */
class SinglePlayer : AppCompatActivity(), Instruction_Page.OnFragmentInteractionListener {
    /**
     * The Running score, gets passed on to win screen to record score.
     */
    protected var runningScore: Int = 0
    /**
     * Boolean recording if the information page is open
     */
    protected var pageOpen = false
    /**
     * Instance of the game logic
     */
    protected lateinit var logic: GameLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_player)

        //Getting extras from intent call. If extra exists set playWithAI
        val call = intent
        val extras = call.extras
        var playWithAI = true // Default to true
        if (extras != null) {
            if (extras.containsKey(WITH_AI)) playWithAI = extras.getBoolean(WITH_AI)
        }
        //Get prefrences from save file and set running score
        val save = getSharedPreferences("save", 0)
        runningScore = Integer.parseInt(save.getString("points", "0")!!)
        //Create game logic instance for user to play
        logic = GameLogic(this, findViewById(R.id.activity_single_player))
        logic.turnAiOn(playWithAI)

        //Set fragment manager, button, and click listener to open and close the instruction page
        f = supportFragmentManager
        information = findViewById(R.id.buttonInformation) as Button
        information!!.setOnClickListener {
            if (!pageOpen) {
                pageOpen = true
                logic.pause()
                val fT = f.beginTransaction()
                fT.add(R.id.container, iPage, "hi")
                fT.commit()
            } else {
                pageOpen = false
                logic.unPause()
                val f = supportFragmentManager
                val fT = f.beginTransaction()
                fT.remove(iPage)
                fT.commit()
            }
        }

        logic.startGame()

    }

    /**
     * End game.
     *
     * @param playerOne Boolean telling endGame if it was player one's turn when called
     * @param c         Context of activity calling endgame
     * @param turns     The number of turns that it took someone to win
     * @param isAI      Boolean telling endGame if the AI was playing
     */
    fun endGame(playerOne: Boolean?, c: Context, turns: Int, isAI: Boolean?) {
        var turns = turns

        val passingTurns = turns++ + 1
        //Create intent for the win screen and bundle information needed for the score screen
        val goToWin = Intent(c, WinScreen::class.java)
        val extra = Bundle()
        extra.putInt("score", runningScore)
        extra.putBoolean("winner", playerOne!!)
        extra.putInt("numTurns", passingTurns)
        extra.putBoolean("AI", isAI!!)
        goToWin.putExtras(extra)
        c.startActivity(goToWin)
    }

    override fun onFragmentInteraction(uri: Uri) {
        //you can leave it empty
    }

    companion object {

        /**
         * The constant WITH_AI. This is stores the string tag for the extra specifying AI usage
         */
        val WITH_AI = "PLAY_WITH_THE_AI_ON"
        /**
         * Holds the information button
         */
        private var information: Button? = null

        /**
         * Holds the fragment manager used for the information page
         */
        lateinit var f: FragmentManager
        /**
         * Holds the fragment for the information page
         */
        protected var iPage = Instruction_Page()
    }
}
