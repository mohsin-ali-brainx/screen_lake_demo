package com.example.screen_lake.db.repository

import com.example.screen_lake.db.dao.SocialMediaUserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialMediaUserRepository @Inject constructor(private val socialMediaUserDao: SocialMediaUserDao) {
}