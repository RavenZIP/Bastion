package io.ravenzip.bastion.result

fun <W, E> Result.Error<E>.toWarned(transform: (error: E) -> W): Result.Warned<Nothing?, W> =
    Result.Warned(null, transform(error))

fun <T, R> Result.Error<T>.map(transform: (T) -> R): Result.Error<R> =
    Result.Error(transform(error))
