package com.lizowzskiy.accents.util

internal fun runBashCommand(cmd: String): ProcessBuilder {
    return ProcessBuilder(
        "bash", "-c", cmd
    ).apply {
        redirectErrorStream(true)
    }
}