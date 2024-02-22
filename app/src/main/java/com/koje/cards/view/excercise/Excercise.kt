package com.koje.cards.view.excercise

import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.StackEntry
import com.koje.cards.view.general.RoundCornerButtonFormat
import com.koje.framework.events.Notifier
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class Excercise(val frame: ExcerciseFrame) : FrameLayoutBuilder.Editor {

    val solution = Notifier("")
    val content = selectContent()
    val answers = selectAnswers()
    var finished = false

    override fun edit(target: FrameLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationVertical()
                setPaddingsDP(10, 10, 10, 10)

                addLinearLayout {
                    setOrientationVertical()
                    setGravityCenterHoritontal()
                    setLayoutWeight(1f)

                    addFiller()
                    addTextView {
                        setText(getContentName(content))
                        setGravityCenter()
                        setTextSizeSP(60)
                    }

                    val hint = getContentHint(content)
                    if (hint.isNotEmpty()) {
                        addTextView {
                            setText(hint)
                            setGravityCenter()
                            setTextSizeSP(24)
                        }
                    }
                    addFiller()
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
                    setColorId(R.color.WhiteTransparent50)
                    setCornerRadius(10)
                    setStroke(3, R.color.black)
                }
                setText(answer)
                setGravityCenter()
                setOnClickListener {
                    if (!finished) {
                        finished = true
                        clicked = true
                        solution.set(content.solution)
                        val solved = answer == content.solution
                        if (solved) {
                            frame.score.increase()
                            frame.history.add(true)
                            content.score++
                            content.stack.saveToJson()
                        } else {
                            frame.score.decrease()
                            frame.history.add(false)
                        }
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


    private fun selectContent(): StackEntry {
        val candidates = mutableListOf<StackEntry>()
        Repository.content.forEach {
            if (it.checked.get()) {
                it.content.forEach {
                    if (it.name != frame.last) {
                        candidates.add(it)
                    }
                }
            }
        }

        candidates.shuffle()

        for (i in 0..10) {
            if (candidates[0].score > candidates[0].stack.getScore()) {
                candidates.shuffle()
            }
        }

        val result = candidates[0]
        frame.last = result.name
        return result
    }

    private fun selectAnswers(): Set<String> {
        val answers = mutableSetOf<String>()
        val all = mutableListOf<String>()
        content.stack.content.forEach {
            if (it.solution != content.solution) {
                all.add(it.solution)
            }
        }
        all.shuffle()
        all.forEach {
            if (answers.size < 3) {
                answers.add(it)
            }
        }
        answers.add(content.solution)
        return answers
    }

    companion object {

        fun getContentName(content: StackEntry): String {
            val lfIndex = content.name.indexOf('\n')
            return (when (lfIndex > 0) {
                true -> content.name.substring(0, lfIndex)
                else -> content.name
            })
        }

        fun getContentHint(content: StackEntry): String {
            val lfIndex = content.name.indexOf('\n')
            return (when (lfIndex > 0) {
                true -> content.name.substring(lfIndex + 1)
                else -> ""
            })
        }

    }
}

