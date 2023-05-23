package com.andrei_singeleytsev.sportquiz.di

import android.app.Application
import androidx.room.Room
import com.andrei_singeleytsev.sportquiz.data.room.MainDataBase
import com.andrei_singeleytsev.sportquiz.data.room.repository.PictureModelRepository
import com.andrei_singeleytsev.sportquiz.data.room.repository.UserScoreRepository
import com.andrei_singeleytsev.sportquiz.data.room.repository.implementation.PictureModelRepositoryImpl
import com.andrei_singeleytsev.sportquiz.data.room.repository.implementation.UserScoreRepositoryImpl
import com.andrei_singeleytsev.sportquiz.domain.implementations.GameHelper
import com.andrei_singeleytsev.sportquiz.domain.repository.GameHelperProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    @Singleton
    fun provideMainDatabase(app: Application): MainDataBase {
        return Room.databaseBuilder(
            app,
            MainDataBase::class.java,
            "sport_quiz_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserScore(db: MainDataBase): UserScoreRepository {
        return UserScoreRepositoryImpl(db.userScoreDao)
    }

    @Provides
    @Singleton
    fun providesPictureModel(db: MainDataBase): PictureModelRepository {
        return PictureModelRepositoryImpl(db.pictureModelDao)
    }

    @Provides
    @Singleton
    fun providesGameHelper(): GameHelperProvider {
        return GameHelper()
    }
}