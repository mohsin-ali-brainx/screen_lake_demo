package com.example.screen_lake.enums

import androidx.compose.ui.graphics.Color

enum class AppBehaviors (val importance:Int, val behaviorName:String, val color: Color, val background: Color) {
    NOT_DEFINED(0,"Not Defined", Color(0xFF9BA1A6), Color.Transparent),
    STOP_THIS(1,"I want to stop this", Color(0xFFFF1D2D), Color(0x33FF1D2D)),
    WANT_LESS(2,"I want to do this less", Color(0xFFF76808), Color(0x33F76808)),
    NOT_A_PROBLEM(3,"This isn't a problem for me", Color(0xFF25D0AB), Color(0x3325D0AB)),
}

fun getAppBehaviorList():List<AppBehaviors>{
    return ArrayList<AppBehaviors>().apply {
        add(AppBehaviors.STOP_THIS)
        add(AppBehaviors.WANT_LESS)
        add(AppBehaviors.NOT_A_PROBLEM)
    }
}

fun getAppBehaviorFromImportance(key:Int):AppBehaviors{
    return when(key){
        AppBehaviors.STOP_THIS.importance->AppBehaviors.STOP_THIS
        AppBehaviors.WANT_LESS.importance->AppBehaviors.WANT_LESS
        AppBehaviors.NOT_A_PROBLEM.importance->AppBehaviors.NOT_A_PROBLEM
        else->{
            AppBehaviors.NOT_DEFINED
        }
    }
}