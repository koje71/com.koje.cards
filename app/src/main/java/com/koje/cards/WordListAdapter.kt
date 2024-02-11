package com.koje.cards

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.data.Stack
import com.koje.framework.view.LinearLayoutBuilder


class WordListAdapter(val stack: Stack) :
    RecyclerView.Adapter<WordListAdapter.ViewHolder>() {

    val content = stack.content


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var col1: TextView = view.findViewById(col1Id) as TextView
        var col2: TextView = view.findViewById(col2Id) as TextView
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        with(LinearLayoutBuilder(LinearLayout(parent.context))) {
            setOrientationHorizontal()

            addTextView {
                setViewId(col1Id)
                setWidthDP(100)
                setLayoutWeight(1f)
                setPaddingsDP(10,5)
                setTextSizeSP(24)
            }
            addTextView {
                setViewId(col2Id)
                setWidthDP(100)
                setLayoutWeight(1f)
                setPaddingsDP(10,5)
                setTextSizeSP(24)
            }


            addImageView {
                setDrawableId(R.drawable.removeicon)
                setSizeDP(50)
                setPaddingsDP(5,5)

                setOnClickListener {
                    val name = parent.findViewById<TextView>(col1Id).text
                    val iterator = stack.content.iterator()
                    while (iterator.hasNext()) {
                        val current = iterator.next()
                        if(current.name == name){
                            iterator.remove()
                        }
                        stack.save()
                    }
                    notifyDataSetChanged()
                }
            }

            return ViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return content.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.col1.setText(content[position].name)
        holder.col2.setText(content[position].solution)
    }

    companion object{
        val col1Id = View.generateViewId()
        val col2Id = View.generateViewId()
    }

}