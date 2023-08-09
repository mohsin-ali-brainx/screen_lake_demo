package com.example.screen_lake.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.screen_lake.models.Behavior

@Dao
interface BehaviorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBehaviorInfo(behavior: Behavior)
    @Query("SELECT * FROM behavior")
    suspend fun getBehaviorList():List<Behavior>

    @Query("SELECT * FROM behavior WHERE name=:name")
    suspend fun getBehaviorByName(name:String): Behavior?
}