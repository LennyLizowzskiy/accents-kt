package com.lizowzskiy.accents.os_specific.unix

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.UnauthorizedAccessException
import com.lizowzskiy.accents.UnsupportedOutputException
import com.lizowzskiy.accents.util.buildBashCommand
import com.lizowzskiy.accents.util.getFullOutput
import kotlin.math.roundToInt

/**
 * Get accent color from a XDG portal
 *
 * Requirements: `bash`, `systemd`, `dbus`
 * **Linux-only**
 */
internal fun getFreedesktopAccentColor(): Color {
    /*
    Example output:

    > busctl --user call org.freedesktop.portal.Desktop /org/freedesktop/portal/desktop org.freedesktop.portal.Settings ReadAll as 1 "" | grep -oP 'accent-color"\s*\([a-z]+\)\s*\K\d+\.\d+\s+\d+\.\d+\s+\d+\.\d+'
    0.133333 0.196078 0.286275
     */
    val output = try {
        buildBashCommand("""busctl --user call org.freedesktop.portal.Desktop /org/freedesktop/portal/desktop org.freedesktop.portal.Settings ReadAll as 1 "" | grep -oP 'accent-color"\s*\([a-z]+\)\s*\K\d+\.\d+\s+\d+\.\d+\s+\d+\.\d+'""")
            .getFullOutput()
    } catch (e: SecurityException) {
        throw UnauthorizedAccessException(cause = e)
    }

    val result = try {
        val (r, g, b) = output
            .trim()
            .split(" ")
            .map {
                (it.toFloat() * 255)
                    .roundToInt()
                    .toShort()
            }

        Color(r, g, b, alpha = 255)
    } catch (e: IllegalArgumentException) {
        throw UnsupportedOutputException(output, cause = e)
    }

    return result
}

internal val isFreeDesktopAvailable: Boolean by lazy(LazyThreadSafetyMode.NONE) {
    System.getenv("XDG_RUNTIME_DIR") != null
}