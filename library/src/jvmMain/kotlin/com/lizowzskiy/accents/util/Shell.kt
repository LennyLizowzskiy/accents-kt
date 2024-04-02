package com.lizowzskiy.accents.util

internal fun buildBashCommand(cmd: String, redirectErrorStream: Boolean = true): ProcessBuilder {
    return ProcessBuilder(
        "bash", "-c", cmd
    ).apply {
        if (redirectErrorStream) this.redirectErrorStream(true)
    }
}