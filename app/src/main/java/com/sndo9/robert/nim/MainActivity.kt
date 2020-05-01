package com.sndo9.robert.nim

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.app.Dialog
import android.app.AlertDialog
import android.app.AlertDialog.Builder
//import android.support.v7.app.AppCompatActivity

/**
 * This is the main screen for the app. It allows the user to choose whether to face another player or an AI
 */
class MainActivity : AppCompatActivity() {


    /**
     * Button allowing user to pick AI
     */
    private var single: Button? = null
    /**
     * Button allowing user to pick play with a friend
     */
    private var multi: Button? = null
    /**
     * Intent for the single player
     */
    private var singlePlayer: Intent? = null

    /**
     * Intent for multiplayer
     */
    private var multiplayer: Intent? = null
    internal lateinit var levelDialog: Dialog
    /**
     * Creates the screen and interface for the main screen
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        levelDialog = Dialog(this)
        //Set intents and bundle boolean to specify opponent
        singlePlayer = Intent(this, SinglePlayer::class.java)
        singlePlayer!!.putExtra(SinglePlayer.WITH_AI, true)
        multiplayer = Intent(this, SinglePlayer::class.java)
        multiplayer!!.putExtra(SinglePlayer.WITH_AI, false)

        //Attach UI to Button variables
        single = findViewById(R.id.singleplayer) as Button
        multi = findViewById(R.id.multiplayer) as Button

        f = supportFragmentManager
        //Launch game with AI playing the user
        single!!.setOnClickListener {
            //                startActivity(singlePlayer);
            ShowLevelPopup()
        }
        //Launch game with two players playing
        multi!!.setOnClickListener { startActivity(multiplayer) }
    }

    fun ShowLevelPopup() {
        levelDialog.setContentView(R.layout.level_popup)
        levelDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        levelDialog.show()
    }

    companion object {
        protected lateinit var f: FragmentManager
        protected var iPage = Instruction_Page()
    }

}
