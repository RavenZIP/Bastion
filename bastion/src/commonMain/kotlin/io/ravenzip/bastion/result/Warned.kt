package io.ravenzip.bastion.result

fun <T, W> Result.Warned<T, W>.toSuccess(): Result<T, W, Nothing> = Result.Success(this.data)

fun <T, W, R> Result.Warned<T, W>.mapData(transform: (T) -> R): Result.Warned<R, W> =
    Result.Warned(transform(this.data), this.warning)

fun <T, W, R> Result.Warned<T, W>.mapWarning(transform: (W) -> R): Result.Warned<T, R> =
    Result.Warned(this.data, transform(this.warning))

fun <TIn, WIn, TOut, WOut> Result.Warned<TIn, WIn>.map(
    transformData: (TIn) -> TOut,
    transformWarning: (WIn) -> WOut,
): Result.Warned<TOut, WOut> =
    Result.Warned(transformData(this.data), transformWarning(this.warning))
