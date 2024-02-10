package com.koje.cards

import com.koje.framework.events.Notifier
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder

class MainActivityHeader:LinearLayoutBuilder.Editor {
    override fun edit(target: LinearLayoutBuilder) {
        with(target){

            addLinearLayout {
                setOrientationVertical()
                setBackgroundColorId(R.color.TitleBackground)
                addTextView {
                    setPaddingsDP(10,0)
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
                addFrameLayout {
                    setHeightDP(60)
                    setPaddingsDP(10,5,5,5)
                    setWidthMatchParent()
                    addReceiver(content){
                        replace(it)
                    }
                }
            }
        }
    }

    companion object{
        val content = Notifier<FrameLayoutBuilder.Editor>(StackListHeader())
    }
}