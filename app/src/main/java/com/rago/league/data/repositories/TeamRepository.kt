package com.rago.league.data.repositories

import com.rago.league.data.model.Teams
import com.rago.league.utils.GenericResponse
import kotlinx.coroutines.flow.Flow

interface TeamRepository {
    fun getTeams(): Flow<GenericResponse<Teams>>
}