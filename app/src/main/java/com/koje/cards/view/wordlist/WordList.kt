package com.koje.cards.view.wordlist

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.R
import com.koje.cards.data.Stack
import com.koje.cards.data.StackEntry
import com.koje.cards.view.Activity
import com.koje.cards.view.general.InputFieldFormat
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class WordList(val stack: Stack) : FrameLayoutBuilder.Editor {

    private lateinit var list: RecyclerView

    override fun edit(target: FrameLayoutBuilder) {
        Activity.footer.set(WordListFooter(stack))
        with(target) {
            addLinearLayout {
                setOrientationVertical()
                setWidthMatchParent()

                addCreateEntry(this)
                addScrollView {
                    setWidthMatchParent()
                    addRecyclerView {
                        setWidthMatchParent()
                        setLinearLayoutManager()
                        setAdapter(WordListAdapter(stack))
                        list = this.view
                    }
                }
                addFiller()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCreateEntry(target: LinearLayoutBuilder) {
        with(target) {
            addLinearLayout {
                setOrientationHorizontal()

                var edit1: EditText? = null
                var edit2: EditText? = null

                addEditText {
                    setPaddingsDP(10, 5)
                    setTextSizeSP(18)
                    setMarginsDP(5, 5, 5, 0)
                    setText("Frage")
                    setBackgroundNull()
                    setWidthDP(100)
                    setGravityCenter()

                    add(InputFieldFormat())
                    setLayoutWeight(1f)
                    edit1 = this.view
                }

                addEditText {
                    setPaddingsDP(10, 5)
                    setTextSizeSP(18)
                    setMarginsDP(0, 5, 0, 0)
                    setText("Antwort")
                    setBackgroundNull()
                    setGravityCenter()
                    setWidthDP(100)
                    add(InputFieldFormat())
                    setLayoutWeight(1f)
                    edit2 = this.view
                }

                addImageView {
                    setDrawableId(R.drawable.addicon)
                    setSizeDP(50)
                    setPaddingsDP(10, 10)

                    setOnClickListener {
                        addNewStackEntry(edit1?.text.toString(), edit2?.text.toString())
                    }
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewStackEntry(word1: String, word2: String) {
        with(stack) {
            content.forEach {

                // keine Wortwiederholungen
                if (it.name == word1) {
                    return
                }
            }

            content.add(0, StackEntry(this, word1, word2, 0))
            save()
            list.adapter?.notifyDataSetChanged()
        }
    }
}

