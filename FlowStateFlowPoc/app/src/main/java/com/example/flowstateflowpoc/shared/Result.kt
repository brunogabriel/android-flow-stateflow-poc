package com.example.flowstateflowpoc.shared

sealed class Result<out R> {
    object Empty : Result<Nothing>()
    object Loading : Result<Nothing>()
    object DismissLoading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
}