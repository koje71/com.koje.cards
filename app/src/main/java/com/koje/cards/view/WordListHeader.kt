package com.koje.cards.view

import com.koje.cards.R
import com.koje.framework.view.FrameLayoutBuilder


class WordListHeader(val title: String) : FrameLayoutBuilder.Editor {


    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()
                addTextView {
                    setTextSizeSP(30)
                    setTextColorID(R.color.white)
                    setFontId(R.font.nunito_bold)
                    setText(title)
                }

                addFiller()
            }
        }
    }

}

