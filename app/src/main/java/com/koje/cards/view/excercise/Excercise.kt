package com.koje.cards.view.excercise

import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.StackEntry
import com.koje.cards.view.Activity
import com.koje.cards.view.general.RoundCornerButtonFormat
import com.koje.framework.events.Notifier
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class Excercise : FrameLayoutBuilder.Editor {

    val solution = Notifier("")

    override fun edit(target: FrameLayoutBuilder) {
        val content = selectContent()
        val answers = selectAnswers(content)
        Activity.footer.set(ExcerciseFooter(content))
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
                    setColorId(R.color.WhiteTransparent50)
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
                        content.stack.saveToJson()
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

    private fun selectContent(): StackEntry {
        val candidates = mutableListOf<StackEntry>()
        Repository.content.forEach {
            if (it.checked.get()) {
                it.content.forEach {
                    if (it.name != last) {
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
        last = result.name
        return result
    }

    private fun selectAnswers(content: StackEntry): Set<String> {
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
        var last = ""
    }
}

