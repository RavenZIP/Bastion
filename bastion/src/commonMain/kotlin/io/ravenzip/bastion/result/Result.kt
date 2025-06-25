package io.ravenzip.bastion.result

import io.ravenzip.bastion.verdict.Verdict

typealias Success<T> = Result<T, Nothing, Nothing>

typealias Warned<T, W> = Result<T, W, Nothing>

typealias Failure<E> = Result<Nothing, Nothing, E>

sealed interface Result<out T, out W, out E : Throwable> {
    data class Success<out T>(val data: T) : Result<T, Nothing, Nothing>

    data class Warned<out T, out W>(val data: T, val warning: W) : Result<T, W, Nothing>

    class Error<out E : Throwable>(val error: E) : Result<Nothing, Nothing, E>
}

fun <T, W, E : Throwable> Result<T, W, E>.toVerdict(): Verdict<T, E> =
    when (this) {
        is Result.Success -> Verdict.Success(data)
        is Result.Warned -> Verdict.Success(data)
        is Result.Error -> Verdict.Error(error)
    }

val <T, W, E : Throwable> Result<T, W, E>.isSuccess: Boolean
    get() = this is Result.Success

val <T, W, E : Throwable> Result<T, W, E>.isWarned: Boolean
    get() = this is Result.Warned

val <T, W, E : Throwable> Result<T, W, E>.notIsError: Boolean
    get() = this !is Result.Error

val <T, W, E : Throwable> Result<T, W, E>.isError: Boolean
    get() = this is Result.Error

fun <T, W, E : Throwable> Result<T, W, E>.dataOrThrow(): T =
    when (this) {
        is Result.Success -> data
        is Result.Warned -> data
        is Result.Error -> throw error
    }

fun <T, W, E : Throwable> Result<T, W, E>.dataOrNull(): T? =
    when (this) {
        is Result.Success -> data
        is Result.Warned -> data
        is Result.Error -> null
    }

fun <T, W, E : Throwable> Result<T, W, E>.dataOrDefault(default: () -> T): T =
    when (this) {
        is Result.Success -> data
        is Result.Warned -> data
        is Result.Error -> default()
    }

fun <T, W, E : Throwable> Result<T, W, E>.warningOrNull(): W? =
    if (this is Result.Warned) warning else null

fun <T, W, E : Throwable> Result<T, W, E>.errorOrNull(): E? =
    if (this is Result.Error) error else null

fun <T, W, E : Throwable> Result<T, W, E>.errorOrDefault(defaultError: () -> E): E =
    if (this is Result.Error) error else defaultError()

fun <T, W, E : Throwable> Result<T, W, E>.throwIfError(): Result<T, W, E> =
    if (this is Result.Error) throw error else this
