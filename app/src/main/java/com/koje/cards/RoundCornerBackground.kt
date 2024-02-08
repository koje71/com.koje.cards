package com.koje.cards
import com.koje.framework.view.ViewBuilder

class RoundCornerBackground(val background: Int) : ViewBuilder.Editor {

    override fun edit(target: ViewBuilder) {
        with(target) {
            setBackgroundGradient {
                setCornerRadius(10)
                setStroke(2, R.color.TitleBackground)
                setColorId(background)
            }
        }
    }
}