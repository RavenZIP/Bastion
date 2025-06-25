package io.ravenzip.bastion.verdict

import io.ravenzip.bastion.result.Result

fun <T, W> Verdict.Success<T>.toWarned(warning: W): Result.Warned<T, W> =
    Result.Warned(data, warning)

fun <T, R> Verdict.Success<T>.map(transform: (T) -> R): Verdict.Success<R> =
    Verdict.Success(transform(data))
