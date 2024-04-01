package com.lizowzskiy.accents.os_specific.unix

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.util.getFirstOutputLine
import com.lizowzskiy.accents.util.runBashCommand
import com.lizowzskiy.accents.util.use
import java.io.File

/**
 * Get GTK3 accent color from the currently active theme
 */
internal fun getGtk3AccentColor(): Color {
    /*
    Example output:

    > grep -oP '(?<=@define-color accent_color \#)[a-fA-F0-9]{6}' ~/.config/gtk-3.0/gtk.css
    c4b28a
    */
    val color = runBashCommand("""grep -oP '(?<=@define-color accent_color \#)[a-fA-F0-9]{6}' ${getCurrentUserConfigDir()}/gtk-3.0/gtk.css""")
        .use {
            Color.fromHexRgb(this.getFirstOutputLine())
        }

    return color
}

internal val isGtk3Available: Boolean by lazy {
    val gtk3Dir = File("${getCurrentUserConfigDir()}/gtk-3.0")
    gtk3Dir.exists() && gtk3Dir.isDirectory
}