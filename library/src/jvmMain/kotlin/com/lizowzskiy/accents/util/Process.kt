package com.lizowzskiy.accents.util

import java.io.Reader

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

fun ProcessBuilder.getFullOutput(): String {
    redirectErrorStream(true)

    val result: String
    start().apply {
        result = inputReader().use(Reader::readText)
        destroy()
    }
    return result
}