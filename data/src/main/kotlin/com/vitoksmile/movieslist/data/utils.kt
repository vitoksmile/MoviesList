package com.vitoksmile.movieslist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal fun <T> Flow<T>.mapToResult(): Flow<Result<T>> {
    return map { Result.success(it) }
        .catch { emit(Result.failure(it)) }
}
