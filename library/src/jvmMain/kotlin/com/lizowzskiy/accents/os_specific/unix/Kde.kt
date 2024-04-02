package com.lizowzskiy.accents.os_specific.unix

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.util.buildBashCommand
import com.lizowzskiy.accents.util.getOutput
import com.lizowzskiy.accents.util.use

/**
 * Get KDE accent color from config files
 *
 * Requirements: KDE
 * **Linux-only**
 */
fun getKdeAccentColor(
    plasmaConfigPath: String = "${getCurrentUserConfigDir()}/plasma-org.kde.plasma.desktop-appletsrc"
): Color {
    /*
    Example output:

    > grep -oP '(?<=AccentColor=)[^,]+' ~/.config/plasma-org.kde.plasma.desktop-appletsrc | sed 's/^"//;s/"$//'
    #C43928
    */
    val output = buildBashCommand("""grep -oP '(?<=AccentColor=)[^,]+' $plasmaConfigPath | sed 's/^"//;s/"${'$'}//'""")
        .use(Process::getOutput)

    return Color.fromHexRgb(output)
}

internal val isKdeAvailable: Boolean by lazy(LazyThreadSafetyMode.NONE) {
    val procb = ProcessBuilder("plasmashell", "--version")
        .apply { redirectErrorStream(true) }

    try {
        val proc = procb.start()
        proc.waitFor()

        proc.exitValue() == 0
    } catch(e: Exception) {
        false
    }
}