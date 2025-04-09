package com.example.vinilos.data.models

sealed interface Response<T> {
    data class Success<T>(val data: T): Response<T>
    data class Error<T>(val msg: String): Response<T>
    class Loading<T>: Response<T>
}
