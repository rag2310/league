package com.rago.league.data.api


import com.rago.league.data.model.Teams
import retrofit2.Call
import retrofit2.http.GET

interface TeamApiService {
    @GET("api/v1/json/3/search_all_teams.php?l=Copa%20del%20Rey")
    fun getTeams(): Call<Teams>
}