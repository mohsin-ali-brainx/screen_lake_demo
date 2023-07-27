package com.example.screenlake.db.repository

import com.example.screenlake.db.dao.SocialMediaUserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialMediaUserRepository @Inject constructor(private val socialMediaUserDao: SocialMediaUserDao) {
}