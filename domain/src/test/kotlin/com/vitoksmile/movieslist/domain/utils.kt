package com.vitoksmile.movieslist.domain

import org.junit.Assert.assertEquals

fun <T> Result<T>.assertIsSuccess(value: T): Result<T> = apply {
    if (!isSuccess) throw AssertionError("Result is failure, expected success")

    assertEquals(value, getOrNull())
}

fun <T> Result<T>.assertIsFailure(error: Throwable): Result<T> = apply {
    if (!isFailure) throw AssertionError("Result is success, expected failure")

    assertEquals(error, exceptionOrNull())
}
