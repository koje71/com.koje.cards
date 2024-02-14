package com.koje.cards.view.general

import com.koje.cards.R
import com.koje.framework.view.EditTextBuilder
import com.koje.framework.view.GradientDrawableBuilder
import com.koje.framework.view.TextViewBuilder

class InputFieldFormat : EditTextBuilder.Editor {
    override fun edit(target: EditTextBuilder) {
        with(target){
            setTextSizeSP(25)
            setBackgroundGradient {
                setColorId(R.color.white)
                setCornerRadius(10)
                setStroke(3, R.color.black)
            }
        }

    }

}