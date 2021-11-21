package com.cerverae18.superlistfinal

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class SquareButton: LinearLayout {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    // This is used to make square buttons.
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}