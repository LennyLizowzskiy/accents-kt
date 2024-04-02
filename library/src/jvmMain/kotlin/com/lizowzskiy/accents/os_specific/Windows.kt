package com.lizowzskiy.accents.os_specific

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.util.getOutput
import com.lizowzskiy.accents.util.use

fun getWindowsAccentColor(): Color {
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
fun getWindowsAccentColorFromRegistry(
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
    val output = ProcessBuilder(
        "reg", "query",
        path, "/v", value
    )
        .redirectErrorStream(true)
        .use(Process::getOutput)

    // output format: #aarrggbb
    val hexColor = hexColorRegex.find(output)?.value
        ?: throw IllegalArgumentException("no value $value was found at $path")

    return Color.fromHexRgb(
        input = run {
            val alpha = hexColor.substring(0, 2)
            val rgb = hexColor.substring(2)

            rgb + alpha
        }
    )
}

private val hexColorRegex = Regex("(?<=0x)[0-9a-fA-F]{6,8}")