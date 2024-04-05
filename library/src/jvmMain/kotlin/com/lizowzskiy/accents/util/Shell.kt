package com.lizowzskiy.accents.util

internal fun buildBashCommand(cmd: String): ProcessBuilder {
    return ProcessBuilder(
        "bash", "-c", cmd
    )
}