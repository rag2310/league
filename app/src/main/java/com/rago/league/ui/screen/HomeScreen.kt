@file:OptIn(ExperimentalMaterial3Api::class)

package com.rago.league.ui.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPasteOff
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rago.league.R
import com.rago.league.data.model.Team
import com.rago.league.presentation.uistate.HomeUIState
import com.rago.league.presentation.uistate.State
import com.rago.league.presentation.uistate.TeamSearch

@Composable
fun HomeScreen(homeUIState: HomeUIState) {
    if (homeUIState.showDialogSearch) {
        Dialog(onDismissRequest = homeUIState.onHideDialogSearch) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp), horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Busqueda por", style = MaterialTheme.typography.titleMedium)
                    }
                    Row(
                        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Nombre")
                            Checkbox(
                                checked = homeUIState.teamSearch == TeamSearch.NAME,
                                onCheckedChange = {
                                    homeUIState.onChangeTeamSearch(TeamSearch.NAME)
                                }
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Año")
                            Checkbox(
                                checked = homeUIState.teamSearch == TeamSearch.YEAR,
                                onCheckedChange = {
                                    homeUIState.onChangeTeamSearch(TeamSearch.YEAR)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        OutlinedTextField(
                            value = homeUIState.search,
                            onValueChange = homeUIState.onChangeSearch,
                            label = {
                                Text(text = "Busqueda")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Button(
                            onClick = homeUIState.onSearch,
                            enabled = homeUIState.search.isNotEmpty() && homeUIState.search.isNotBlank()
                        ) {
                            Text(text = "Buscar")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = homeUIState.onHideDialogSearch) {
                            Text(text = "Cancelar")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
    HomeScreenContent(homeUIState)
}

@Composable
private fun HomeScreenContent(homeUIState: HomeUIState) {
    Scaffold(Modifier.fillMaxSize(),
        topBar = {
            Box(
                Modifier
                    .height(72.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topEnd = 0.dp,
                            topStart = 0.dp,
                            bottomEnd = 15.dp,
                            bottomStart = 15.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .statusBarsPadding(),
            ) {
                Text(
                    text = "Copa del Rey",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge
                )
                Crossfade(modifier = Modifier.align(Alignment.CenterEnd),targetState = homeUIState.search.isEmpty()) {
                    if (it) {
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = homeUIState.onShowDialogSearch,
                            enabled = homeUIState.state == State.SUCCESS
                        ) {
                            Icon(Icons.Filled.FilterAlt, contentDescription = "Buscar")
                        }
                    }

                    if (!it) {
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = homeUIState.clearFilter,
                            enabled = homeUIState.state == State.SUCCESS
                        ) {
                            Icon(Icons.Filled.FilterAltOff, contentDescription = "Limpiar Filtro")
                        }
                    }
                }

            }
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {
            Crossfade(targetState = homeUIState.state) { state ->
                when (state) {
                    State.LOADING -> {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(text = "Obteniendo Equipos")
                        }
                    }

                    State.SUCCESS -> {
                        Crossfade(targetState = homeUIState.teams.size) { size ->
                            if (size == 0) {
                                Column(
                                    Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Filled.ContentPasteOff,
                                        contentDescription = "Lista Vacía"
                                    )
                                    Spacer(modifier = Modifier.height(30.dp))
                                    Text(text = "Sin resultados")
                                }
                            }

                            if (size > 0) {
                                LazyColumn(content = {
                                    items(homeUIState.teams) { team ->
                                        CardTeam(team = team, homeUIState.onNavDetails)
                                    }
                                })
                            }
                        }
                    }

                    State.ERROR -> {
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = homeUIState.getTeams) {
                                Text(text = "Reintentar")
                            }
                            Text(text = homeUIState.error)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardTeam(team: Team, onClick: (Team) -> Unit) {
    Card(
        modifier = Modifier.padding(10.dp),
        onClick = {
            onClick(team)
        }, colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(64.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(team.strTeamBadge)
                    .diskCacheKey(team.idTeam)
                    .crossfade(true)
                    .build(),
                contentDescription = "Team Badge",
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.logo)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Column {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo),
                        modifier = Modifier.size(32.dp),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = team.strTeam, style = MaterialTheme.typography.titleMedium)
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.stadium),
                        modifier = Modifier.size(32.dp),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = team.strStadium, style = MaterialTheme.typography.titleSmall)
                }
            }
        }
    }
}