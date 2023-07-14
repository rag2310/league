package com.rago.league.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rago.league.presentation.uistate.SplashUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val _splashUIState: MutableStateFlow<SplashUIState> = MutableStateFlow(
        SplashUIState(
            setOnNavHome = ::setOnNavHome
        )
    )
    val splashUIState: StateFlow<SplashUIState> = _splashUIState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _splashUIState.update {
                it.copy(success = true)
            }
        }
    }

    private fun setOnNavHome(onNavHome: () -> Unit) {
        viewModelScope.launch {
            _splashUIState.update {
                it.copy(onNavHome = onNavHome)
            }
        }
    }
}