package com.lizowzskiy.accents.util

import java.io.InputStreamReader

fun <T : Any> ProcessBuilder.use(
    block: Process.() -> T
): T {
    val result: T
    start().apply {
        result = block()
        destroy()
    }
    return result
}

internal fun Process.getFirstOutputLine(): String {
    val line: String
    InputStreamReader(inputStream).use { reader ->
        line = reader.readLines()[0]
    }
    return line
}