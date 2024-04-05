package com.lizowzskiy.accents

fun throwUnsupportedOsException(): Nothing {
    throw UnsupportedOperationException(
        "retrieving accent color on current system " +
                "(name: ${System.getProperty("os.name")}, " +
                "version: ${System.getProperty("version")})" +
                "is not supported")
}

class UnauthorizedAccessException @JvmOverloads constructor(
    msg: String? = null, cause: Throwable? = null) : IllegalArgumentException(msg, cause)

class UnsupportedOutputException @JvmOverloads constructor(
    output: String, cause: Throwable? = null) : IllegalArgumentException("output: $output", cause)