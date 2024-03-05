package com.koje.cards.view.overview

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.koje.cards.R
import com.koje.cards.data.Repository
import com.koje.cards.data.Stack
import com.koje.cards.view.Activity
import com.koje.cards.view.general.InputFieldFormat
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder


class Overview : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        Activity.footer.set(OverviewFooter())
    }


}

