package io.ravenzip.bastion.result

fun <W, E : Throwable> Result.Error<E>.toWarned(
    transform: (error: E) -> W
): Result.Warned<Nothing?, W> = Result.Warned(null, transform(error))

fun <EIn : Throwable, EOut : Throwable> Result.Error<EIn>.map(
    transform: (EIn) -> EOut
): Result.Error<EOut> = Result.Error(transform(error))
