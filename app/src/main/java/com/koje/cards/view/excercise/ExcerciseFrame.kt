package com.koje.cards.view.excercise

import com.koje.cards.view.Activity
import com.koje.framework.events.IntNotifier
import com.koje.framework.events.Notifier
import com.koje.framework.view.FrameLayoutBuilder


class ExcerciseFrame : FrameLayoutBuilder.Editor {

    val content = Notifier(Excercise(this))
    val score = IntNotifier(0)
    val history = mutableListOf<Boolean>()

    override fun edit(target: FrameLayoutBuilder) {
        Activity.footer.set(ExcerciseFooter(this))
        with(target){
            addFrameLayout {
                setWidthMatchParent()
                addReceiver(content) {
                    replaceWithFade(it)
                }
            }
            add(ScoreArea(this@ExcerciseFrame))
        }
    }


}

