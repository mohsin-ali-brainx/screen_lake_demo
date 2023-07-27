package com.example.screenlake.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.screenlake.enums.BehaviorImportance

@Entity("behavior")
data class Behavior(
    @PrimaryKey(autoGenerate = false)
    var name:String,
    var importance:Int= BehaviorImportance.LOW.ordinal,
    var permissionGiven:Boolean=false,
)
