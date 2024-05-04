package com.lizowzskiy.accents.os_specific

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.UnauthorizedAccessException
import com.lizowzskiy.accents.UnsupportedOutputException
import com.lizowzskiy.accents.util.getFullOutput
import com.lizowzskiy.accents.util.use

internal fun getMacOsAccentColor(): Color {
    val osVersion = getMacOsMajorVersion()
    return when {
        osVersion >= 11 -> getMacOsAccentColorFromCli()

        else -> throw UnsupportedOperationException("macOS version $osVersion is not supported")
    }
}

internal fun getMacOsAccentColorFromCli(): Color {
    val output = try {
        ProcessBuilder("defaults", "read", "-g", "AppleAccentColor")
            .getFullOutput()
    } catch (e: SecurityException) {
        throw UnauthorizedAccessException(cause = e)
    }

    val result = try {
        val num = output.split("\n")[0].split(" ")[0]
        Accent.fromAppleAccentColorNum(num.toInt()).value
    } catch (e: Exception) {
        throw UnsupportedOutputException(output, cause = e)
    }

    return result
}

internal fun getMacOsMajorVersion() = ProcessBuilder("sw_vers").apply {
    redirectErrorStream(true)
}.use {
    inputReader().use { reader ->
        reader.lines()
            .filter { it.startsWith("ProductVersion:") }
            .findFirst()
            .map { it.substringAfter("ProductVersion:").trim() }
            .map { parseMacOSVersion(it) }
            .orElseThrow { IllegalArgumentException("ProductVersion was not found in sw_vers or has invalid format") }
    }
}

private fun parseMacOSVersion(version: String): Int {
    val versionParts = version.split(".")
    if (versionParts.isEmpty() || versionParts[0].isEmpty()) {
        throw IllegalArgumentException("ProductVersion has invalid format: $version")
    }

    return versionParts[0].toIntOrNull()
        ?: throw IllegalArgumentException("ProductVersion has invalid major version: $version")
}

private enum class Accent(val value: Color) {
    GRAPHITE(Color(140, 140, 140)), // #8C8C8C
    RED(Color(255, 82, 87)), // #FF5257
    ORANGE(Color(247, 130, 27)), // #F7821B
    YELLOW(Color(244, 198, 2)), // #FFC602
    GREEN(Color(98, 186, 70)), // #62BA46
    BLUE(Color(2, 119, 255)), // #0277FF
    PURPLE(Color(165, 80, 167)), // #A550A7
    PINK(Color(257, 79, 158)); // #F74F9E

    companion object {
        fun fromAppleAccentColorNum(input: Int): Accent {
            require(input in -1..6) { "invalid input number: $input" }

            return when (input) {
                -1 -> GRAPHITE
                0 -> RED
                1 -> ORANGE
                2 -> YELLOW
                3 -> GREEN
                4 -> BLUE
                5 -> PURPLE
                6 -> PINK

                else -> throw NoWhenBranchMatchedException("unreachable")
            }
        }
    }
}