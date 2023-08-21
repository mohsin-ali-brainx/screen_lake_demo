package com.example.screen_lake.domain.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("social_media_user")
data class SocialMediaUser(
    @PrimaryKey(autoGenerate = false)
    var email: String,
    var username: String?=null,
    var password: String?=null,
    var fullName: String?=null,
    var dateOfBirth: String?=null,
    var gender: String?=null,
    var phoneNumber: Int?=null,
    var occupation: String?=null,
    var salary: Double?=null,
    var country: String?=null,
    var city: String?=null
)
