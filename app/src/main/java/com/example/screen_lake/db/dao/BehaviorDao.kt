package com.example.screen_lake.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.screen_lake.models.Behavior

@Dao
interface BehaviorDao {
    @Upsert
    suspend fun insertBehaviorInfo(behavior: Behavior)
    @Query("SELECT * FROM behavior")
    suspend fun getBehaviorList():List<Behavior>
}