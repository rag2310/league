package com.rago.league.presentation.uistate

import com.rago.league.data.model.Team

data class HomeUIState(
    val teams: List<Team> = listOf(),
    val originalTeams: List<Team> = listOf(),
    val error: String = "",
    val state: State = State.LOADING,
    val teamSearch: TeamSearch = TeamSearch.NAME,
    val onChangeTeamSearch: (TeamSearch) -> Unit = {},
    val search: String = "",
    val onChangeSearch: (String) -> Unit = {},
    val showDialogSearch: Boolean = false,
    val onShowDialogSearch: () -> Unit = {},
    val onHideDialogSearch: () -> Unit = {},
    val onSearch: () -> Unit = {},
    val setOnNavDetails: ((Team) -> Unit) -> Unit = {},
    val onNavDetails: (Team) -> Unit = {},
    val getTeams: () -> Unit = {},
    val clearFilter:()->Unit = {}
)

enum class TeamSearch {
    NAME,
    YEAR
}

enum class State {
    LOADING,
    SUCCESS,
    ERROR
}