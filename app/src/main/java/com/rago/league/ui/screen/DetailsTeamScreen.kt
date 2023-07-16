package com.rago.league.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rago.league.R
import com.rago.league.data.model.DescriptionRegion
import com.rago.league.data.model.Team
import com.rago.league.utils.NetworkUtils

@Composable
fun DetailsTeamScreen(team: Team, onNavBack: () -> Unit) {
    DetailsTeamScreenContent(team, onNavBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsTeamScreenContent(team: Team, onNavBack: () -> Unit) {
    val tabs = getTabs(team)
    val scrollState = rememberScrollState()
    var state by remember { mutableStateOf(0) }

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
                IconButton(onClick = onNavBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                }
                Text(
                    text = "Detalles del equipo",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 10.dp)
                .verticalScroll(scrollState),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(150.dp)
                        .weight(1f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(team.strTeamBadge)
                        .diskCacheKey(team.idTeam)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Team Badge",
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(id = R.drawable.logo)
                )
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .weight(1f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(team.strTeamJersey ?: R.drawable.jersey)
                        .diskCacheKey(team.strTeamJersey)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Team Jersey",
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(id = R.drawable.logo)
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = team.strTeam, style = MaterialTheme.typography.titleLarge)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = team.intFormedYear, style = MaterialTheme.typography.labelMedium)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconUrl(url = team.strWebsite, icon = R.drawable.website)
                IconUrl(url = team.strRSS, icon = R.drawable.rss)
                IconUrl(url = team.strYoutube, icon = R.drawable.youtube)
                IconUrl(url = team.strInstagram, icon = R.drawable.instagram)
                IconUrl(url = team.strFacebook, icon = R.drawable.facebook)
                IconUrl(url = team.strTwitter, icon = R.drawable.twitter)
            }
            TabRow(selectedTabIndex = state) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = {
                            Text(
                                text = title.code,
                                maxLines = 2,
                            )
                        }
                    )
                }
            }
            Crossfade(modifier = Modifier.padding(bottom = 10.dp), targetState = state) { index ->
                val descriptionRegion = tabs[index]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp,
                                topStart = 0.dp,
                                topEnd = 0.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Text(
                        text = descriptionRegion.description,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun IconUrl(url: String, @DrawableRes icon: Int) {
    val context = LocalContext.current
    AnimatedVisibility(visible = url.isNotEmpty() && url.isNotBlank()) {
        IconButton(onClick = {
            val urlReplace = url.replace("\"", "")
            NetworkUtils.openUrl(context, "https://$urlReplace")
        }) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Youtube",
                tint = Color.Unspecified
            )
        }
    }
}


private fun getTabs(team: Team): List<DescriptionRegion> {
    val list = mutableListOf<DescriptionRegion>()
    team.strDescriptionDE?.let {
        list.add(
            DescriptionRegion(
                "DE",
                it
            )
        )
    }
    team.strDescriptionEN?.let {
        list.add(
            DescriptionRegion(
                "EN",
                it
            )
        )
    }
    team.strDescriptionES?.let {
        list.add(
            DescriptionRegion(
                "ES",
                it
            )
        )
    }

    return list
}
