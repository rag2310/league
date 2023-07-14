package com.rago.league.presentation.uistate

data class SplashUIState(
    val success: Boolean = false,
    val onNavHome: () -> Unit = {},
    val setOnNavHome: (() -> Unit) -> Unit = {}
)