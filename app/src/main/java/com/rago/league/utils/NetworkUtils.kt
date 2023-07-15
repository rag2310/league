package com.rago.league.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import javax.inject.Inject


class NetworkUtils @Inject constructor(private val context: Context) {
    @SuppressLint("MissingPermission")
    fun isInternetAvailable(): Boolean {
        val connectionManager =
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            )
                    as ConnectivityManager
        val networkCapabilities = connectionManager.activeNetwork ?: return false
        val actNw =
            connectionManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    companion object {
        fun openUrl(activityContext: Context, appURL: String) {
            val playIntent: Intent = Intent().apply {
                action = Intent.ACTION_VIEW

                data = Uri.parse(appURL)

            }
            try {
                activityContext.startActivity(playIntent)
            } catch (e: Exception) {
                // handle the exception
                Log.i("NetworkUtils", "openUrl: ${e.message}")
            }
        }
    }
}