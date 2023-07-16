package com.rago.league.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rago.league.data.model.Team
import com.rago.league.data.repositories.TeamRepository
import com.rago.league.presentation.uistate.HomeUIState
import com.rago.league.presentation.uistate.State
import com.rago.league.presentation.uistate.TeamSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : ViewModel() {
    private val _homeUIState: MutableStateFlow<HomeUIState> = MutableStateFlow(
        HomeUIState(
            onChangeSearch = ::onChangeSearch,
            onChangeTeamSearch = ::onChangeTeamSearch,
            onShowDialogSearch = ::onShowDialogSearch,
            onHideDialogSearch = ::onHideDialogSearch,
            onSearch = ::onSearch,
            setOnNavDetails = ::setOnNavDetails,
            getTeams = ::getTeams,
            clearFilter = ::clearFilter
        )
    )
    val homeUIState: StateFlow<HomeUIState> = _homeUIState.asStateFlow()

    init {
        getTeams()
    }

    private fun getTeams() {
        viewModelScope.launch {
            _homeUIState.update { uiState ->
                uiState.copy(state = State.LOADING, error = "")
            }
            teamRepository.getTeams().collect { genericResponse ->
                genericResponse.error?.let {
                    _homeUIState.update { uiState ->
                        uiState.copy(state = State.ERROR, error = it)
                    }
                }

                genericResponse.data?.let { teams ->
                    _homeUIState.update { uiState ->
                        uiState.copy(
                            state = State.SUCCESS,
                            teams = teams.teams,
                            originalTeams = teams.teams
                        )
                    }
                }
            }
        }
    }

    private fun onChangeSearch(value: String) {
        viewModelScope.launch {
            _homeUIState.update {
                it.copy(search = value)
            }
        }
    }

    private fun onChangeTeamSearch(value: TeamSearch) {
        viewModelScope.launch {
            _homeUIState.update {
                it.copy(teamSearch = value)
            }
        }
    }

    private fun onShowDialogSearch() {
        viewModelScope.launch {
            _homeUIState.update {
                it.copy(showDialogSearch = true)
            }
        }
    }

    private fun onHideDialogSearch() {
        viewModelScope.launch {
            _homeUIState.update {
                it.copy(showDialogSearch = false)
            }
        }
    }

    private fun onSearch() {
        viewModelScope.launch {
            _homeUIState.update {
                it.copy(teams = _homeUIState.value.originalTeams)
            }
            val list = when (_homeUIState.value.teamSearch) {
                TeamSearch.NAME -> _homeUIState.value.teams.filter {
                    it.strTeam.lowercase().contains(
                        _homeUIState.value.search
                    )
                }

                TeamSearch.YEAR -> _homeUIState.value.teams.filter {
                    it.intFormedYear.contains(
                        _homeUIState.value.search
                    )
                }
            }
            _homeUIState.update {
                it.copy(teams = list, showDialogSearch = false)
            }
        }
    }

    private fun setOnNavDetails(onNavDetails: (Team) -> Unit) {
        viewModelScope.launch {
            _homeUIState.update {
                it.copy(onNavDetails = onNavDetails)
            }
        }
    }

    private fun clearFilter() {
        viewModelScope.launch {
            _homeUIState.update {
                it.copy(teams = _homeUIState.value.originalTeams, search = "")
            }
        }
    }
}