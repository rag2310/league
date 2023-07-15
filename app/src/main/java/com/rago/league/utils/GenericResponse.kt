package com.rago.league.utils

data class GenericResponse<T>(
    val error: String? = null,
    val data: T? = null
)