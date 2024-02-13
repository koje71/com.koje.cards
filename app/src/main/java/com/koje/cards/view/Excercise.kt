package com.koje.cards.view

import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.StackEntry
import com.koje.framework.events.Notifier
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class Excercise : FrameLayoutBuilder.Editor {

    val solution = Notifier("")

    override fun edit(target: FrameLayoutBuilder) {
        val contentCandidates = mutableListOf<StackEntry>()
        Repository.content.forEach {
            if (it.checked.get()) {
                it.content.forEach {
                    if (it.name != last) {
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

        MainActivityFooter.content.set(ExcerciseFooter(content))
        with(target) {
            addLinearLayout {
                setOrientationVertical()
                setPaddingsDP(10, 10, 10, 10)

                addTextView {
                    setText(content.name)
                    setPaddingsDP(10, 10)
                    setGravityCenter()
                    setLayoutWeight(1f)
                    setTextSizeSP(60)
                }

                answers.shuffled().forEach {
                    addGameButton(this, content, it)
                }
            }
        }
    }

    private fun addGameButton(target: LinearLayoutBuilder, content: StackEntry, answer: String) {
        with(target) {
            addTextView {
                var clicked = false
                add(RoundCornerButtonFormat())
                setBackgroundGradient {
                    setColorId(R.color.white)
                    setCornerRadius(10)
                    setStroke(3, R.color.black)
                }
                setText(answer)
                setGravityCenter()
                setOnClickListener {
                    clicked = true
                    solution.set(content.solution)
                    val solved = answer == content.solution
                    if (solved) {
                        Repository.score.increase()
                        content.score++
                        content.stack.save()
                    } else {
                        Repository.score.decrease()
                    }
                }

                addReceiver(solution) {
                    if (it == answer) {
                        setBackgroundGradient {
                            setColorId(R.color.Solved)
                            setCornerRadius(10)
                            setStroke(3, R.color.black)
                        }
                    } else if (clicked) {
                        setBackgroundGradient {
                            setColorId(R.color.Failed)
                            setCornerRadius(10)
                            setStroke(3, R.color.black)
                        }
                    }
                }
            }
        }
    }

    companion object {
        var last = ""
    }
}

