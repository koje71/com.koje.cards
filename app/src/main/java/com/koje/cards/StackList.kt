package com.koje.cards

import com.koje.framework.App
import com.koje.framework.utils.Logger
import com.koje.framework.view.FrameLayoutBuilder
import com.koje.framework.view.LinearLayoutBuilder
import java.io.File


class StackList : FrameLayoutBuilder.Editor {

    override fun edit(target: FrameLayoutBuilder) {
        MainActivityHeader.content.set(StackListHeader())
        loadStackFiles()
        with(target){
            addLinearLayout {
                setOrientationVertical()

                addScrollView {
                    addLinearLayout {
                        setOrientationVertical()
                        addListEntry(this,"Latein 1")
                        addListEntry(this,"Latein 2")
                        addListEntry(this,"Latein 3")
                    }
                }
                addFiller()

                addRelativeLayout {
                    setGravityCenterRight()
                    setPaddingsDP(10,10)

                    addLinearLayout {
                        setOrientationHorizontal()
                        addTextView {
                            add(RoundCornerBackground(R.color.white))
                            setPaddingsDP(20,10,20,15)
                            setTextSizeSP(20)
                            setFontId(R.font.nunito_bold)
                            setMarginsDP(0,5,0,0)
                            setText("Start")
                        }
                    }
                }
            }
        }
    }

    fun addListEntry(target:LinearLayoutBuilder,name:String){
        with(target){
            addLinearLayout {
                setOrientationHorizontal()
                addImageView {
                    setMarginsDP(10,8,5,0)
                    setDrawableId(R.drawable.editicon)
                    setSizeDP(40)

                    setOnClickListener {
                        MainActivity.content.set(WordList(name))
                    }
                }
                addTextView {
                    setPaddingsDP(10,5)
                    setTextSizeSP(30)
                    setMarginsDP(0,5,0,0)
                    setText(name)
                }

                addFiller()

                addLinearLayout {
                    setOrientationVertical()
                    setPaddingsDP(0,10,10,0)
                    addTextView {
                        setText("1256")
                    }
                    addTextView {
                        setText("99,7%")
                    }
                }

                addCheckbox {
                    setMarginsDP(5,0,10,15)
                    view.scaleX = 1.5f
                    view.scaleY = 1.5f
                }

            }
            addView {
                setHeightDP(3)
                setBackgroundColorId(R.color.BlackTransparent)
            }
        }
    }

    fun addCreateEntry(target:LinearLayoutBuilder){
        with(target){
            addLinearLayout {
                setOrientationHorizontal()
                setPaddingsDP(5,20,0,0)
                addImageView {
                    setSizeDP(50)
                    setDrawableId(R.drawable.addicon)
                }
                addTextView {
                    setPaddingsDP(10,5)
                    setTextSizeSP(30)
                    setMarginsDP(0,5,0,0)
                    setText("Hinzufügen")
                }

                setOnClickListener {
                    loadStackFiles()
                }
            }
        }

    }

    fun loadStackFiles(){
        try {
            val dir = App.context.filesDir.absolutePath
            val cardsPath = "$dir/cards"
            val cardsFolder = File(cardsPath)
            if(!cardsFolder.isDirectory){
                cardsFolder.mkdir()
                Logger.info(this,"app-folder: created")
            }


            File("$cardsPath/latein.txt").bufferedWriter().use { out ->
                out.write("hallo1")
            }


        }catch (e:Exception){
            Logger.info(this,"error:" + e)
        }

    }
}

