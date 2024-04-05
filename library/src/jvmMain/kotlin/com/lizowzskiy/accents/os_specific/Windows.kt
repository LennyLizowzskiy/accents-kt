package com.lizowzskiy.accents.os_specific

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.UnauthorizedAccessException
import com.lizowzskiy.accents.UnsupportedOutputException
import com.lizowzskiy.accents.util.getFullOutput

internal fun getWindowsAccentColor(): Color {
    val osVersion = System.getProperty("os.version")

    // Windows 10: 10.*
    // Windows 11: 10.0.22000
    return when {
        osVersion.startsWith("10.") -> getWindowsAccentColorFromRegistry()

        else -> throw IllegalArgumentException("Windows version $osVersion is not supported")
    }
}

/**
 * Get Windows accent color from registry
 *
 * Requirements: Windows 10+, access to reading registry (`reg`)
 */
internal fun getWindowsAccentColorFromRegistry(
    path: String = """HKEY_CURRENT_USER\Software\Microsoft\Windows\DWM""",
    value: String = "AccentColor"
): Color {
    /* // \ = new line
    > reg query HKEY_CURRENT_USER\Software\Microsoft\Windows\DWM /v AccentColor
    \
    HKEY_CURRENT_USER\Software\Microsoft\Windows\DWM
        AccentColor    REG_DWORD    0xffd77800
    \
    \
     */
    val output = try {
        ProcessBuilder(
            "reg", "query",
            path, "/v", value
        ).getFullOutput()
    } catch (e: SecurityException) {
        throw UnauthorizedAccessException(cause = e)
    }

    val result = try {
        // output format: #aarrggbb
        val hexColor = hexColorRegex.find(output)?.value
            ?: throw IllegalArgumentException("no value $value was found at $path")

        Color.fromHexRgb(
            input = run {
                val alpha = hexColor.substring(0, 2)
                val rgb = hexColor.substring(2)

                rgb + alpha
            }
        )
    } catch(e: IllegalArgumentException) {
        throw UnsupportedOutputException(output, cause = e)
    }

    return result
}

private val hexColorRegex = Regex("(?<=0x)[0-9a-fA-F]{6,8}")