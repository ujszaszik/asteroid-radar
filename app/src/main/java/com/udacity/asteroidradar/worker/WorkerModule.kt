package com.udacity.asteroidradar.worker

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class WorkerModule {

    @Provides
    @Singleton
    fun provideUpdateScheduler(@ApplicationContext context: Context) = UpdateScheduler(context)
}