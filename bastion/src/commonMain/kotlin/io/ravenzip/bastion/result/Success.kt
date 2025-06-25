package io.ravenzip.bastion.result

fun <T, W> Result.Success<T>.toWarned(warning: W): Result.Warned<T, W> =
    Result.Warned(data, warning)

fun <T, R> Result.Success<T>.map(transform: (T) -> R): Result.Success<R> =
    Result.Success(transform(data))
