package com.sndo9.robert.nim

import android.util.Log

import java.util.ArrayList


class AI(
        /**
         * Determines if this AI is player 1. true means it is, and false means it isn't
         */
        var isPlayerOne: Boolean) {

    /**
     * This method will select all the sticks that this AI wants to select
     * @param rows a list of all the rows the AI can choose from
     * @return the row that was chosen and had sticks selected from
     */
    fun doTurn(vararg rows: ArrayList<stick>): Int {
        val row = getRand(rows.size - 1)
        Log.w("AI", "I'm taking my turn")

        //Count how many sticks are left in this row
        val totalSticks = 1 + row * 2
        var remainingSticks = totalSticks
        for (s in rows[row]) {
            if (s.isRemoved)
                remainingSticks--
        }

        if (remainingSticks == 0) {
            //Check to see if all sticks are removed
            if (GameLogic.checkAllSticksRemoved(*rows)) {
                Log.w("AI", "No more moves")
                return 0
            }
            Log.w("AI", "restarting doTurn")

            //Try randomly selecting another row that hopefully has sticks in it. One ahs to exist
            return doTurn(*rows)
        }

        Log.w("AI", "Remaining Sticks $remainingSticks")
        selectSticksToRemove(rows[row], remainingSticks, totalSticks)
        Log.w("AI", "Done Selecting sticks")

        return row
    }

    /**
     * This method will use the stick's select method to randomly select a stick from a given row
     * @param arrayOne the row that a stick will be selected from
     * @param sticksToRemove how many sticks there are possible to remove
     * @param totalNumSticks the maximum amount of sticks in the row
     */
    private fun selectSticksToRemove(arrayOne: ArrayList<stick>, sticksToRemove: Int, totalNumSticks: Int) {
        //Get how many sticks the AI wants to remove
        val numSticks = getRand(sticksToRemove - 1) + 1

        Log.w("AI", "Removing $numSticks/$sticksToRemove/$totalNumSticks sticks\n-----------------------")
        for (x in 0 until numSticks) {
            var stick: Int
            do {
                stick = getRand(totalNumSticks - 1)
                Log.w("AI", "Trying to remove $stick")
                //Loop back is choice is invalid
            } while (arrayOne[stick].isRemoved || arrayOne[stick].isSelected)

            Log.w("AI", "Removing stick $stick")

            arrayOne[stick].select()
        }


    }

    /**
     * Helper method to deal with getting a random number.
     * @param seed the seed to multiply the random result by
     * @return a random value between 0-seed
     */
    private fun getRand(seed: Int): Int {
        return Math.round(Math.random() * seed).toInt()
    }


}

