@file:Suppress("unused")

package com.vitoksmile.movieslist.data

import com.vitoksmile.movieslist.domain.MoviesRepository
import com.vitoksmile.movieslist.domain.usecase.GetMovieDetailsUseCase
import com.vitoksmile.movieslist.domain.usecase.GetMoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    @Singleton
    fun bindsMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository
}

@Module
@InstallIn(ViewModelComponent::class)
internal class DomainModule {

    @Provides
    fun providesGetMoviesUseCase(
        repository: MoviesRepository,
    ): GetMoviesUseCase =
        GetMoviesUseCase(repository)

    @Provides
    fun providesGetMovieDetailsUseCase(
        repository: MoviesRepository,
    ): GetMovieDetailsUseCase =
        GetMovieDetailsUseCase(repository)
}
