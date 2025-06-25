package io.ravenzip.bastion.verdict

import io.ravenzip.bastion.result.Result

sealed interface Verdict<out T, out E> {
    data class Success<out T>(val data: T) : Verdict<T, Nothing>

    data class Error<out E>(val error: E) : Verdict<Nothing, E>
}

val <T, E> Verdict<T, E>.isSuccess: Boolean
    get() = this is Verdict.Success

val <T, E> Verdict<T, E>.isError: Boolean
    get() = this is Verdict.Error

fun <T, E> Verdict<T, E>.dataOrNull(): T? =
    when (this) {
        is Verdict.Success -> data
        is Verdict.Error -> null
    }

fun <T, E> Verdict<T, E>.dataOrElse(defaultValue: () -> T): T =
    when (this) {
        is Verdict.Success -> data
        is Verdict.Error -> defaultValue()
    }

fun <T, E> Verdict<T, E>.toResult() =
    when (this) {
        is Verdict.Success -> Result.Success(data)
        is Verdict.Error -> Result.Error(error)
    }

fun <T, E> Verdict<T, E>.errorOrElse(defaultError: () -> E): E =
    if (this is Verdict.Error) error else defaultError()
