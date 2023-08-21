package com.example.screen_lake.domain.models

import android.content.Context
import com.example.screen_lake.R

data class GenericSelectionModel(
    val key:Any,
    val image:Int,
    val text:String,
    var isChecked:Boolean=false
)

fun getOccupationList(context: Context):ArrayList<GenericSelectionModel>{
    context.apply {
        return ArrayList<GenericSelectionModel>().apply {
            add(GenericSelectionModel(0,R.drawable.iv_mobile,getString(R.string.ui_ux_designer)))
            add(GenericSelectionModel(1,R.drawable.iv_pallete,getString(R.string.graphic_designer)))
            add(GenericSelectionModel(2,R.drawable.iv_laptop,getString(R.string.web_developer)))
            add(GenericSelectionModel(3,R.drawable.iv_controller,getString(R.string.gamer)))
            add(GenericSelectionModel(4,R.drawable.iv_camera,getString(R.string.photographer)))
        }
    }
}
