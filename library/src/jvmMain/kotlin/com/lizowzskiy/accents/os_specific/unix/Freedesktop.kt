package com.lizowzskiy.accents.os_specific.unix

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.util.getFirstOutputLine
import com.lizowzskiy.accents.util.runBashCommand
import com.lizowzskiy.accents.util.use
import kotlin.math.roundToInt

/**
 * Get accent color from a XDG portal
 *
 * Requirements: `bash`, `systemd`, `dbus`
 */
internal fun getFreedesktopAccentColor(): Color {
    /*
    Example output:

    > busctl --user call org.freedesktop.portal.Desktop /org/freedesktop/portal/desktop org.freedesktop.portal.Settings ReadAll as 1 "" | grep -oP 'accent-color"\s*\([a-z]+\)\s*\K\d+\.\d+\s+\d+\.\d+\s+\d+\.\d+'
    0.133333 0.196078 0.286275
     */
    val color = runBashCommand("""busctl --user call org.freedesktop.portal.Desktop /org/freedesktop/portal/desktop org.freedesktop.portal.Settings ReadAll as 1 "" | grep -oP 'accent-color"\s*\([a-z]+\)\s*\K\d+\.\d+\s+\d+\.\d+\s+\d+\.\d+'""")
        .use {
            val (r, g, b) = getFirstOutputLine()
                .trim()
                .split(" ")
                .map {
                    (it.toFloat() * 255)
                        .roundToInt()
                        .toShort()
                }

            Color(r, g, b, alpha = 255)
        }

    return color
}

internal val isFreeDesktopAvailable: Boolean by lazy {
    System.getenv("XDG_RUNTIME_DIR") != null
}