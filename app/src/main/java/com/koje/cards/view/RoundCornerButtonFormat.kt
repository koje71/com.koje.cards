package com.koje.cards.view

import com.koje.cards.R
import com.koje.framework.view.GradientDrawableBuilder
import com.koje.framework.view.TextViewBuilder

class RoundCornerButtonFormat : TextViewBuilder.Editor {

    override fun edit(target: TextViewBuilder) {
        with(target) {
            setPaddingsDP(20, 5, 20, 10)
            setTextSizeSP(20)
            setFontId(R.font.nunito_bold)
            setMarginsDP(5, 5,5,5)

            setBackgroundStateList {
                addStatePressedGradient {
                    setColorId(R.color.ButtonClick)
                    setCornerAndStroke(this)
                }
                addStateWildcardGradient {
                    setColorId(R.color.white)
                    setCornerAndStroke(this)
                }
            }
        }
    }

    private fun setCornerAndStroke(target: GradientDrawableBuilder) {
        with(target) {
            setCornerRadius(10)
            setStroke(3, R.color.black)
        }
    }
}