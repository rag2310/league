package com.rago.league.data.repositories

import com.rago.league.data.api.TeamApiService
import com.rago.league.data.model.Teams
import com.rago.league.utils.GenericResponse
import com.rago.league.utils.RetrofitUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamApiService: TeamApiService,
    private val retrofitUtils: RetrofitUtils
) : TeamRepository {
    override fun getTeams(): Flow<GenericResponse<Teams>> {
        return retrofitUtils.retrofitCallbackFlow(teamApiService.getTeams())
    }
}