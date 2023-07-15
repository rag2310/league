package com.rago.league.utils

import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RetrofitUtils @Inject constructor(
    private val networkUtils: NetworkUtils
) {

    fun <T> retrofitCallbackFlow(api: Call<T>): Flow<GenericResponse<T>> = callbackFlow {

        if (networkUtils.isInternetAvailable()) {
            val callback: Callback<T> = object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {

                    if (response.errorBody() != null) {
                        val json = response.errorBody()?.string()
                        trySend(GenericResponse(error = json))
                    }

                    if (response.body() != null) {
                        trySend(GenericResponse(data = response.body()))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    trySend(GenericResponse(error = t.message!!))
                }
            }

            api.enqueue(callback)
        }

        if (!networkUtils.isInternetAvailable()) {
            trySend(GenericResponse(error = "No tienes conexión a internet"))
        }

        awaitClose {
            if (api.isCanceled) {
                api.cancel()
            }
        }
    }
}