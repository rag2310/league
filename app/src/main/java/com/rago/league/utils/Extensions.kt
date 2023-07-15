package com.rago.league.utils

import androidx.navigation.NavHostController
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun NavHostController.navigateWithPopUp(
    toRoute: String,
    fromRoute: String
) {
    this.navigate(toRoute) {
        popUpTo(fromRoute) {
            inclusive = true
        }
    }
}