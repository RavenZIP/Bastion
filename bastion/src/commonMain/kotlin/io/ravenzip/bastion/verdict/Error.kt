package io.ravenzip.bastion.verdict

import io.ravenzip.bastion.result.Result
import io.ravenzip.bastion.result.Result.Warned

fun <W, E> Verdict.Error<E>.toWarned(transform: (error: E) -> W): Result<Nothing?, W, Nothing> =
    Warned(null, transform(error))

fun <T, R> Verdict.Error<T>.map(transform: (T) -> R): Verdict.Error<R> =
    Verdict.Error(transform(error))
