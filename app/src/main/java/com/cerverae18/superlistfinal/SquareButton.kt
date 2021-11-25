package com.cerverae18.superlistfinal

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout


/**
 * A Custom LinearLayout Class to create a Square Button
 * This is a class that extends from the LinearLayout to create a Perfectly Square Button
*  @constructor receives the context and passes it to LinearLayout
 *  @constructor receives the context  and attributes and passes it to LinearLayout
 */

class SquareButton : LinearLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    // This is used to make square buttons.
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}