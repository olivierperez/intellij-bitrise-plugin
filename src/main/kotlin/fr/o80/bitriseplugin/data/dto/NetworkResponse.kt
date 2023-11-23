package fr.o80.bitriseplugin.data.dto

sealed interface NetworkResponse<T> {
    class Success<T>(val content: T): NetworkResponse<T>
    class Error<T>(val error: ErrorDto): NetworkResponse<T>
}
