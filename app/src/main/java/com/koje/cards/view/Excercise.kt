package com.koje.cards.view

import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.StackEntry
import com.koje.framework.events.Notifier
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.TextViewBuilder


class Excercise : FrameLayoutBuilder.Editor {

    val solution = Notifier("")

    override fun edit(target: FrameLayoutBuilder) {
        val contentCandidates = mutableListOf<StackEntry>()
        Repository.content.forEach {
            if (it.checked.get()) {
                it.content.forEach {
                    if(it.name!=last) {
                        contentCandidates.add(it)
                    }
                }
            }
        }

        contentCandidates.shuffle()
        for (i in 0..2) {
            if (contentCandidates[0].score > contentCandidates[0].stack.getScore()) {
                contentCandidates.shuffle()
            }
        }

        val content = contentCandidates[0]
        last = content.name

        val answers = mutableSetOf<String>()
        Repository.content.forEach {
            if (it.checked.get()) {
                it.content.forEach {
                    if (it.solution != content.solution && answers.size < 3) {
                        answers.add(it.solution)
                    }
                }
            }
        }
        answers.add(content.solution)

        MainActivityHeader.content.set(ExcerciseHeader(content))
        with(target) {
            addLinearLayout {
                setOrientationVertical()
                setPaddingsDP(0, 0, 0, 0)

                addTextView {
                    setText(content.name)
                    setPaddingsDP(10, 10)
                    setGravityCenter()
                    setLayoutWeight(1f)
                    setTextSizeSP(40)
                }

                answers.shuffled().forEach{ answer ->
                    addTextView {
                        add(RoundCornerButtonFormat())
                        setBackgroundGradient {
                            setColorId(R.color.white)
                            setCornerRadius(10)
                            setStroke(3, R.color.black)
                        }
                        setText(answer)
                        setGravityCenter()
                        setOnClickListener {
                            solution.set(content.solution)
                            val solved = answer == content.solution
                            if(solved){
                                Repository.score++
                                content.score++
                                content.stack.save()
                            }else{
                                Repository.score--
                            }
                            Thread{
                                Thread.sleep(1000)
                                MainActivity.content.set(Excercise())
                            }.start()
                        }

                        addReceiver(solution){
                            if(it == answer){
                                setBackgroundGradient {
                                    setColorId(R.color.Solved)
                                    setCornerRadius(10)
                                    setStroke(3, R.color.black)
                                }
                            }
                        }

                    }
                }

                addLinearLayout {
                    setOrientationHorizontal()
                    setMarginsDP(0,10,0,0)
                    setBackgroundColorId(R.color.TitleBackground)
                    addFiller()
                    addTextView {
                        add(RoundCornerButtonFormat())
                        setText("weiter")
                    }
                }
            }
        }
    }


    companion object{
        var last = ""
    }
}

