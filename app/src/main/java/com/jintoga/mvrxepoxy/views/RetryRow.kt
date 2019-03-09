package com.jintoga.mvrxepoxy.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.jintoga.mvrxepoxy.R
import kotlinx.android.synthetic.main.retry_row.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class RetryRow @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.retry_row, this)
    }

    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        retryView.setOnClickListener(clickListener)
    }
}