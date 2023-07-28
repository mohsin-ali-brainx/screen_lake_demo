package com.example.screen_lake.enums

import androidx.compose.ui.graphics.Color

enum class AppDistractions(val id:Int,val distraction:String,val color:Color,val background:Color) {
    NOT_DEFINED(0,"Not Defined", Color(0xFF9BA1A6), Color.Transparent),
    VERY_DISTRACTING(1,"Very Distracting", Color(0xFFFF1D2D),Color(0x33FF1D2D)),
    DISTRACTING(2,"Distracting", Color(0xFFF76808),Color(0x33F76808)),
    NOT_A_PROBLEM(3,"Not a Problem for Me", Color(0xFF25D0AB),Color(0x3325D0AB)),
}

fun getAppDistractionList():List<AppDistractions>{
    return ArrayList<AppDistractions>().apply {
        add(AppDistractions.VERY_DISTRACTING)
        add(AppDistractions.DISTRACTING)
        add(AppDistractions.NOT_A_PROBLEM)
    }
}