package com.example.screen_lake.dataSource.db.repository

import com.example.screen_lake.dataSource.db.dao.SocialMediaUserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialMediaUserRepository @Inject constructor(private val socialMediaUserDao: SocialMediaUserDao) {
}