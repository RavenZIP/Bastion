package io.ravenzip.bastion.verdict

import io.ravenzip.bastion.result.Result

fun <W, E : Throwable> Verdict.Error<E>.toWarned(
    transform: (error: E) -> W
): Result.Warned<Nothing?, W> = Result.Warned(null, transform(error))

fun <EIn : Throwable, EOut : Throwable> Verdict.Error<EIn>.map(
    transform: (EIn) -> EOut
): Verdict.Error<EOut> = Verdict.Error(transform(error))
