package com.example.screen_lake.domain.models

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.screen_lake.R
import com.example.screen_lake.appUtils.enums.AppBehaviors

@Entity("behavior")
data class Behavior(
    @PrimaryKey(autoGenerate = false)
    var name:String,
    var importance:Int= AppBehaviors.NOT_DEFINED.importance,
    var permissionGiven:Boolean=false,
)

fun getAppBehaviorList(context: Context):ArrayList<Behavior>{
    context.apply {
        return ArrayList<Behavior>().apply {
            add(Behavior(getString(R.string.doom_scrolling)))
            add(Behavior(getString(R.string.habitual_pick_ups)))
            add(Behavior(getString(R.string.gaming)))
            add(Behavior(getString(R.string.watching_videos)))
            add(Behavior(getString(R.string.viewing_adult_content)))
            add(Behavior(getString(R.string.texting_too_much)))
            add(Behavior(getString(R.string.calling_too_much)))
        }
    }
}

fun List<Behavior>.getUpdatedBehaviorList(savedList:List<Behavior>):ArrayList<Behavior>{
    return ArrayList<Behavior>().apply {
        this@getUpdatedBehaviorList.forEach { behavior->
            val savedBehavior = savedList.firstOrNull { it.name== behavior.name}
            add(savedBehavior?:behavior)
        }
    }
}
