package com.lizowzskiy.accents.util

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

fun Process.getOutput(): String =
    inputReader().use { it.readText() }