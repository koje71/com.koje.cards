package com.koje.framework.view

import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.WordListAdapter

class RecyclerViewBuilder(override val view: RecyclerView) :
    ViewBuilder(view) {

    interface Editor : ViewEditor<RecyclerViewBuilder>

    fun setAdapter(adapter: WordListAdapter){
        view.adapter = adapter
    }


}