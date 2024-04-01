package com.lizowzskiy.accents.os_specific.unix

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.util.getFirstOutputLine
import com.lizowzskiy.accents.util.runBashCommand
import com.lizowzskiy.accents.util.use

/**
 * Get KDE accent color from config files
 */
internal fun getKdeAccentColor(): Color {
    /*
    Example output:

    > grep -oP '(?<=AccentColor=)[^,]+' ~/.config/plasma-org.kde.plasma.desktop-appletsrc | sed 's/^"//;s/"$//'
    #C43928
    */
    val color = runBashCommand("""grep -oP '(?<=AccentColor=)[^,]+' ${getCurrentUserConfigDir()}/plasma-org.kde.plasma.desktop-appletsrc | sed 's/^"//;s/"${'$'}//'""")
        .use {
            Color.fromHexRgb(this.getFirstOutputLine())
        }

    return color
}

internal val isKdeAvailable: Boolean by lazy {
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