package com.koje.cards

import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.koje.framework.App
import com.koje.framework.utils.Logger
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder
import java.io.File


class StackListHeader : FrameLayoutBuilder.Editor {


    override fun edit(target: FrameLayoutBuilder) {
        with(target){
            addLinearLayout {
                setOrientationHorizontal()
                addTextView {
                    setTextSizeSP(30)
                    setTextColorID(R.color.white)
                    setFontId(R.font.nunito_bold)
                    setText("Themen")
                }

                addFiller()

                addImageView {
                    setSizeDP(50)
                    setDrawableId(R.drawable.addicon)
                }
            }
        }
    }

}

