package com.koje.cards.view.general

import com.koje.cards.R
import com.koje.framework.view.EditTextBuilder

class InputFieldFormat : EditTextBuilder.Editor {
    override fun edit(target: EditTextBuilder) {
        with(target) {
            setTextSizeSP(25)
            setBackgroundGradient {
                setColorId(R.color.WhiteTransparent50)
                setCornerRadius(10)
                setStroke(3, R.color.black)
            }
        }

    }

}