package com.rago.league.ui.utils

sealed class AppScreensImpl : AppScreens {

    object SplashScreen : AppScreensImpl() {
        override val route: String
            get() = "splash_screen"
    }

    object HomeScreen : AppScreensImpl() {
        override val route: String
            get() = "home_screen"
    }
}

interface AppScreens {
    val route: String
}