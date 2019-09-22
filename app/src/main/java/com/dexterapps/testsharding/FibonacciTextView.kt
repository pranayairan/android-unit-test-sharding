package com.dexterapps.testsharding

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class FibonacciTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var numbers = arrayOf(0, 1)

    init {
        updateView()

        setOnClickListener {
            next()
        }
    }

    private fun updateView() {
        text = "$currentValue"
    }

    private fun next() {
        val value = currentValue
        numbers[0] = numbers[1]
        numbers[1] = value
        updateView()
    }

    private val currentValue: Int
        get() = numbers[0] + numbers[1]
}