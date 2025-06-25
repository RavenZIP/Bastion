package io.ravenzip.bastion.verdict

import io.ravenzip.bastion.result.Result

typealias Just<T> = Verdict<T, Nothing>

typealias Problem<E> = Verdict<Nothing, E>

sealed interface Verdict<out T, out E : Throwable> {
    data class Success<out T>(val data: T) : Verdict<T, Nothing>

    data class Error<out E : Throwable>(val error: E) : Verdict<Nothing, E>
}

fun <T, E : Throwable> Verdict<T, E>.toResult() =
    when (this) {
        is Verdict.Success -> Result.Success(data)
        is Verdict.Error -> Result.Error(error)
    }

val <T, E : Throwable> Verdict<T, E>.isSuccess: Boolean
    get() = this is Verdict.Success

val <T, E : Throwable> Verdict<T, E>.isError: Boolean
    get() = this is Verdict.Error

fun <T, E : Throwable> Verdict<T, E>.dataOrThrow(): T? =
    when (this) {
        is Verdict.Success -> data
        is Verdict.Error -> throw error
    }

fun <T, E : Throwable> Verdict<T, E>.dataOrNull(): T? =
    when (this) {
        is Verdict.Success -> data
        is Verdict.Error -> null
    }

fun <T, E : Throwable> Verdict<T, E>.dataOrDefault(defaultValue: () -> T): T =
    when (this) {
        is Verdict.Success -> data
        is Verdict.Error -> defaultValue()
    }

fun <T, E : Throwable> Verdict<T, E>.errorOrNull(): E? = if (this is Verdict.Error) error else null

fun <T, E : Throwable> Verdict<T, E>.errorDefault(default: () -> E): E =
    if (this is Verdict.Error) error else default()

fun <T, E : Throwable> Verdict<T, E>.throwIfError(): Verdict<T, E> =
    if (this is Verdict.Error) throw error else this
