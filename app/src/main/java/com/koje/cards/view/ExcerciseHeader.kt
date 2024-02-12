package com.koje.cards.view

import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.StackEntry
import com.koje.framework.view.FrameLayoutBuilder


class ExcerciseHeader(val content: StackEntry) : FrameLayoutBuilder.Editor {


    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()
                addTextView {
                    setTextSizeSP(30)
                    setTextColorID(R.color.white)
                    setFontId(R.font.nunito_bold)
                    setText(content.stack.name)
                }

                addFiller()

                addTextView {
                    setTextSizeSP(30)
                    setTextColorID(R.color.white)
                    setFontId(R.font.nunito_bold)
                    setText(Repository.score.toString())
                }

            }
        }
    }

}

