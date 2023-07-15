package com.rago.league.data.di

import com.rago.league.data.api.TeamApiService
import com.rago.league.data.repositories.TeamRepository
import com.rago.league.data.repositories.TeamRepositoryImpl
import com.rago.league.utils.RetrofitUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoriesModule {

    @Provides
    @ViewModelScoped
    fun provideTeamRepository(
        teamApiService: TeamApiService,
        retrofitUtils: RetrofitUtils
    ): TeamRepository = TeamRepositoryImpl(
        teamApiService, retrofitUtils
    )
}