package com.strider.taskmanager.di

import android.app.Application
import androidx.room.Room
import com.strider.taskmanager.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import splitties.init.appCtx
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase() =
        Room.databaseBuilder(appCtx, AppDatabase::class.java, "Task.db")
            .fallbackToDestructiveMigration()
            .build()
}