package com.sndo9.robert.nim

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

import android.R.attr.visibility


class stick
/**
 * Instantiates a new Stick.
 *
 * @param stickPic      the stick picture
 * @param givenRow      the given row of the stick
 * @param givenPosition the given position of the stick
 * @param c             the context of the caller
 */
(
        /**
         * The Image of the stick
         */
        protected var image: ImageView, givenRow: Int, givenPosition: Int, c: Context) {
    //    /**
    //     * The Visibility.
    //    */
    //    protected boolean visibility;//Remove
    /**
     * Is the stick selected.
     */
    /**
     * Returns isSelected
     *
     * @return isSelected
     */
    var isSelected: Boolean = false
    /**
     * The row the stick is in
     */
    /**
     * Gets row of the stick.
     *
     * @return the row
     */
    var row: Int = 0
        protected set
    /**
     * The number of the stick in the row
     */
    /**
     * Gets position of the stick.
     *
     * @return the position
     */
    var position: Int = 0
        protected set

    /**
     * The view
     */
    protected var thisV: View

    /**
     * The animation to rotate the image 45 degrees
     */
    protected lateinit var rotate: Animation
    /**
     * The animation to rotate the image back 45 degrees
     */
    protected lateinit var rotateBack: Animation
    /**
     * The animation to remove the image
     */
    protected lateinit var remove: Animation
    /**
     * has the stick been removed
     */
    var isRemoved = false

    init {
        setRotations(c)
        image.visibility = View.VISIBLE
        row = givenRow
        position = givenPosition
        thisV = image
    }//visibility = true;

    //    /**
    //     * Make visible boolean.
    //     *
    //     * @return the boolean
    //     */
    ////Undo selection
    //    public boolean makeVisible() {
    //        if (visibility == false) {
    //            visibility = true;
    //            image.setVisibility(View.VISIBLE);
    //            return true;
    //        } else
    //            return false;
    //    }
    //
    ////    /**
    ////     * Make invisible boolean.
    ////     *
    ////     * @return the boolean
    ////     */
    //////Stick is selected
    ////    public boolean makeInvisible() {
    ////        if (visibility == true) {
    ////            visibility = false;
    ////            image.setVisibility(View.INVISIBLE);
    ////            return true;
    ////        } else
    ////            return false;
    ////    }

    //    /**
    //     * Is visible boolean.
    //     *
    //     * @return the boolean
    //     */
    //    public boolean isVisible() {
    //        return visibility;
    //    }

    /**
     * Unselects the stick
     *
     * @return true if the stick was unselected, false otherwise
     */
    fun select(): Boolean {
        if (!isSelected) {
            isSelected = true
            thisV.startAnimation(rotate)
            return true
        }
        return false
    }

    /**
     * Unselects the stick
     *
     * @return true if the stick was unselected, false otherwise
     */
    fun unSelect(): Boolean {
        if (isSelected) {
            isSelected = false
            thisV.startAnimation(rotateBack)
            return true
        }
        return false
    }

    /**
     * Removes the stick
     */
    fun remove() {
        image.setOnClickListener(null)
        thisV.startAnimation(remove)
        image.visibility = View.GONE
        isRemoved = true
        isSelected = false
    }

    /**
     * Disables the stick
     */
    fun disable() {
        image.isEnabled = false
    }

    /**
     * Enables the stick
     */
    fun enable() {
        image.isEnabled = true
    }

    /**
     * Sets the animation variables
     * @param context of the caller
     */
    private fun setRotations(c: Context) {
        rotate = AnimationUtils.loadAnimation(c, R.anim.stick_selection)
        rotateBack = AnimationUtils.loadAnimation(c, R.anim.stick_unselect)
        remove = AnimationUtils.loadAnimation(c, R.anim.stick_removal)
    }

    /**
     * Converts string settings to a string
     * @return
     */
    override fun toString(): String {
        var output = ""
        if (isRemoved)
            output = output + 1
        else
            output = output + 0

        return output
    }

    /**
     * Takes string ad sets string settings
     *
     * @param input the input
     */
    fun fromString(input: Int) {
        if (input == 1) remove()
    }

    /**
     * Change image of the stick.
     *
     * @param pic the pic
     */
    fun changeImage(pic: Drawable) {
        image.setImageDrawable(pic)
    }

}
