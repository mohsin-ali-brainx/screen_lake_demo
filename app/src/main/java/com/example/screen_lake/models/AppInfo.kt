package com.example.screen_lake.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("app_info")
data class AppInfo(
    @PrimaryKey(autoGenerate = false)
    var apk:String,
    var realAppName:String?=null,
    var distractionLevel:String?=null,
    var appPrimaryUser:String?=null,
    var bitmapResource:String?=null,
)
