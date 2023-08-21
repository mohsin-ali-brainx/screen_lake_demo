package com.example.screen_lake.di

import android.content.Context
import androidx.room.Room
import com.example.screen_lake.dataSource.db.ScreenLakeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {

    @Singleton
    @Provides
    @Named("test_database")
    fun provideDatabase(@ApplicationContext app: Context): ScreenLakeDatabase {
        return Room.inMemoryDatabaseBuilder(app, ScreenLakeDatabase::class.java).allowMainThreadQueries().build()
    }
}