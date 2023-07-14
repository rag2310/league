package com.rago.league

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rago.league.presentation.uistate.SplashUIState
import com.rago.league.presentation.viewmodel.SplashViewModel
import com.rago.league.ui.screen.HomeScreen
import com.rago.league.ui.screen.SplashScreen
import com.rago.league.ui.theme.LeagueTheme
import com.rago.league.ui.utils.AppScreensImpl.*
import com.rago.league.utils.navigateWithPopUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            LeagueTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LeagueScreen()
                }
            }
        }
    }
}

@Composable
private fun LeagueScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SplashScreen.route) {
        composable(SplashScreen.route) {
            val splashViewModel: SplashViewModel = hiltViewModel()
            val splashUIState: SplashUIState by splashViewModel.splashUIState.collectAsState()
            LaunchedEffect(key1 = Unit, block = {
                splashUIState.setOnNavHome {
                    navController.navigateWithPopUp(
                        HomeScreen.route,
                        SplashScreen.route
                    )
                }
            })
            SplashScreen(splashUIState = splashUIState)
        }

        composable(HomeScreen.route) {
            HomeScreen()
        }
    }
}