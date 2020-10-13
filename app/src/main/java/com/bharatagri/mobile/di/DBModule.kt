package com.bharatagri.mobile.di

import android.content.Context
import com.bharatagri.mobile.service.database.AppDatabase
import com.bharatagri.mobile.service.repository.LocalRepository
import com.bharatagri.mobile.service.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
class DBModule {

    @Provides
    fun provideMovieDao(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context).movieDao()

    @Provides
    fun provideMovieRepository(movieDao: MovieDao) = LocalRepository(movieDao)
}