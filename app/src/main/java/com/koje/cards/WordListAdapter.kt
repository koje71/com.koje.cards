package com.koje.cards

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.koje.framework.view.LinearLayoutBuilder


class WordListAdapter(val content: MutableList<WordPair>) :
    RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

        companion object{
            val col1Id = View.generateViewId()
            val col2Id = View.generateViewId()
        }

    init {
        for (i in 0..100000) {
            content.add(WordPair("key $i", "value $i"))
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var col1: EditText = view.findViewById(col1Id) as EditText
        var col2: EditText = view.findViewById(col2Id) as EditText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        with(LinearLayoutBuilder(LinearLayout(parent.context))) {
            setOrientationHorizontal()
            addEditText {
                setViewId(col1Id)
            }
            addView {
                setHeightDP(10)
                setWidthMatchParent()

                setBackgroundColorId(R.color.black)
                setLayoutWeight(1f)

            }
            addEditText {
                setViewId(col2Id)
            }

            return ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.col1.setText(content[position].value)
        holder.col2.setText(content[position].solution)
    }
}