package io.ravenzip.bastion.result

import io.ravenzip.bastion.verdict.Verdict

sealed interface Result<out T, out W, out E> {
    data class Success<out T>(val data: T) : Result<T, Nothing, Nothing>

    data class Warned<out T, out W>(val data: T, val warning: W) : Result<T, W, Nothing>

    class Error<out E>(val error: E) : Result<Nothing, Nothing, E>
}

fun <T, W, E> Result<T, W, E>.toVerdict(): Verdict<T, E> =
    when (this) {
        is Result.Success -> Verdict.Success(data)
        is Result.Warned -> Verdict.Success(data)
        is Result.Error -> Verdict.Error(error)
    }

val <T, W, E> Result<T, W, E>.isSuccess: Boolean
    get() = this is Result.Success

val <T, W, E> Result<T, W, E>.isWarned: Boolean
    get() = this is Result.Warned

val <T, W, E> Result<T, W, E>.isError: Boolean
    get() = this is Result.Error

fun <T, W, E> Result<T, W, E>.dataOrNull(): T? =
    when (this) {
        is Result.Success -> data
        is Result.Warned -> data
        is Result.Error -> null
    }

fun <T, W, E> Result<T, W, E>.dataOrElse(defaultValue: () -> T): T =
    when (this) {
        is Result.Success -> data
        is Result.Warned -> data
        is Result.Error -> defaultValue()
    }

fun <T, W, E> Result<T, W, E>.warningOrNull(): W? = if (this is Result.Warned) warning else null

fun <T, W, E> Result<T, W, E>.errorOrNull(): E? = if (this is Result.Error) error else null

fun <T, W, E> Result<T, W, E>.errorOrElse(defaultError: () -> E): E =
    if (this is Result.Error) error else defaultError()
