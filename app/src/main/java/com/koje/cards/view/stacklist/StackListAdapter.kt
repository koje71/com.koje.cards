package com.koje.cards.view.stacklist

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.CompoundButtonCompat
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.view.Activity
import com.koje.cards.view.wordlist.WordList
import com.koje.framework.view.LinearLayoutBuilder


class StackListAdapter() :
    RecyclerView.Adapter<StackListAdapter.ViewHolder>() {

    val content = Repository.content

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(nameId)
        var wordCount = view.findViewById<TextView>(wordCountId)
        var score = view.findViewById<TextView>(scoreId)
        var checked = view.findViewById<CheckBox>(checkedId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        with(LinearLayoutBuilder(LinearLayout(parent.context))) {
            setOrientationHorizontal()
            val line = this.view
            addImageView {
                setMarginsDP(10, 8, 5, 0)
                setDrawableId(R.drawable.editicon)
                setSizeDP(40)
                setOnClickListener {
                    loadStackByName(line.findViewById<TextView>(nameId).text.toString())
                }
            }

            addTextView {
                setViewId(nameId)
                setPaddingsDP(10, 5)
                setTextSizeSP(30)
                setLayoutWeight(1f)
                setMarginsDP(0, 5, 0, 0)
            }


            addLinearLayout {
                setOrientationVertical()
                setGravityRight()
                setPaddingsDP(0, 10, 10, 0)
                addTextView {
                    setGravityRight()
                    setViewId(wordCountId)
                }
                addTextView {
                    setGravityRight()
                    setViewId(scoreId)
                }
            }

            addCheckbox {
                setViewId(checkedId)
                setMarginsDP(5, 0, 10, 15)
                view.scaleX = 1.5f
                view.scaleY = 1.5f

                val states = arrayOf(
                    intArrayOf(android.R.attr.state_checked),
                    intArrayOf()
                )
                val colors = listOf(
                    getColorFromID(R.color.TitleBackground),
                    getColorFromID(R.color.TitleBackground)
                )
                CompoundButtonCompat.setButtonTintList(
                    view,
                    ColorStateList(states, colors.toIntArray())
                )

                setOnCheckedChangeListener {
                    selectStackByName(line.findViewById<TextView>(nameId).text.toString(), it)
                }
            }

            return ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return content.size
    }

    private fun loadStackByName(name: String) {
        Repository.content.forEach {
            if (it.name == name) {
                Activity.content.set(WordList(it))
                return
            }
        }
    }

    private fun selectStackByName(name: String, checked: Boolean) {
        Repository.content.forEach {
            if (it.name == name) {
                it.checked.set(checked)
                return
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stack = content[position]
        with(holder) {
            name.setText(stack.name)
            wordCount.setText("${stack.content.size}")
            score.setText("%.2f".format(stack.getScore()))
            checked.isChecked = stack.checked.get()
        }
    }


    companion object {
        val nameId = View.generateViewId()
        val wordCountId = View.generateViewId()
        val scoreId = View.generateViewId()
        val checkedId = View.generateViewId()
    }

}