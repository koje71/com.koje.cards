package com.koje.cards.view

import com.koje.cards.R
import com.koje.cards.view.stacklist.StackList
import com.koje.framework.view.LinearLayoutBuilder

class MainActivityHeader : LinearLayoutBuilder.Editor {
    override fun edit(target: LinearLayoutBuilder) {
        with(target) {

            addLinearLayout {
                setOrientationVertical()
                setBackgroundColorId(R.color.TitleBackground)
                addTextView {
                    setGradientBackgroundVertical(
                        R.color.black,
                        R.color.TitleBackground
                    )
                    setPaddingsDP(10, 0)
                    setTextSizeSP(40)
                    setTextColorID(R.color.white)
                    setFontId(R.font.nunito_bold)
                    setText("Lernkarten Spiel")

                    setOnClickListener {
                        MainActivity.content.set(StackList())
                    }
                }
                addView {
                    setHeightDP(3)
                    setWidthMatchParent()
                    setBackgroundColorId(R.color.WhiteTransparent)
                }
            }
        }
    }

}