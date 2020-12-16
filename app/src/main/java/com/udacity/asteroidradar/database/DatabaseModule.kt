package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    companion object {
        private const val DATABASE_NAME = "AsteroidDatabase"
    }

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): AsteroidDatabase {
        return Room.databaseBuilder(context, AsteroidDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    }

}