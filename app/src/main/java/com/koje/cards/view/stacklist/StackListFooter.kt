package com.koje.cards.view.stacklist

import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.view.Activity
import com.koje.cards.view.excercise.Excercise
import com.koje.framework.events.StringNotifier
import com.koje.framework.view.FrameLayoutBuilder


class StackListFooter : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()
                setPaddingsDP(5, 5)

                addTextView {
                    setTextColorID(R.color.white)
                    addReceiver(message){
                        setText(it)
                    }
                }
                addFiller()

                addImageView {
                    setDrawableId(R.drawable.forwardicon)
                    setSizeDP(50)
                    setPaddingsDP(8, 8)

                    setOnClickListener {
                        onForwardClick()
                    }
                }

            }
        }
    }

    private fun onForwardClick(){
        var count = 0
        Repository.content.forEach {
            if(it.checked.get()){
                count+=it.content.size
            }
        }

        if(count<5){
            message.set("nicht genug Inhalte ausgewählt")
            return
        }

        Repository.score.set(0)
        Activity.content.set(Excercise())
    }

    companion object{
        val message = StringNotifier("")
    }
}

