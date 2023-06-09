package com.chayun.naeiyagiapp.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import com.chayun.naeiyagiapp.R

class EditText : AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        addTextChangedListener(onTextChanged = { text, _, _, _ ->
            error = if ((text?.toString()?.length ?: 0) <= 7) resources.getString(R.string.password_min_char) else null
        })
    }
}