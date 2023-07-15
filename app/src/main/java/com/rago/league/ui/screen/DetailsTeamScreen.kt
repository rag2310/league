package com.rago.league.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DetailsTeamScreen(strTeam: String) {
    DetailsTeamScreenContent(strTeam)
}

@Composable
fun DetailsTeamScreenContent(strTeam: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "DetailsTeamScreen $strTeam")
    }
}
