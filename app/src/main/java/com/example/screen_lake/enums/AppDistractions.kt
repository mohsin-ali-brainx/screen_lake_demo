package com.example.screen_lake.enums

import androidx.compose.ui.graphics.Color

enum class AppDistractions(val key:String, val distraction:String, val color:Color, val background:Color) {
    NOT_DEFINED("not_defined","Not Defined", Color(0xFF9BA1A6), Color.Transparent),
    VERY_DISTRACTING("very_distracting","Very Distracting", Color(0xFFFF1D2D),Color(0x33FF1D2D)),
    DISTRACTING("distracting","Distracting", Color(0xFFF76808),Color(0x33F76808)),
    NOT_A_PROBLEM("not_a_problem","Not a Problem for Me", Color(0xFF25D0AB),Color(0x3325D0AB)),
}

fun getAppDistractionList():List<AppDistractions>{
    return ArrayList<AppDistractions>().apply {
        add(AppDistractions.VERY_DISTRACTING)
        add(AppDistractions.DISTRACTING)
        add(AppDistractions.NOT_A_PROBLEM)
    }
}

fun getAppDistractionFromKey(key:String?):AppDistractions{
    return when(key){
        AppDistractions.VERY_DISTRACTING.key->AppDistractions.VERY_DISTRACTING
        AppDistractions.DISTRACTING.key->AppDistractions.DISTRACTING
        AppDistractions.NOT_A_PROBLEM.key->AppDistractions.NOT_A_PROBLEM
        else->{
            AppDistractions.NOT_DEFINED
        }
    }
}