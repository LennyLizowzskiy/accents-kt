package com.lizowzskiy.accents.os_specific.unix

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.util.buildBashCommand
import com.lizowzskiy.accents.util.getOutput
import com.lizowzskiy.accents.util.use
import java.io.File

/**
 * Get GTK3 accent color from the currently active theme
 */
fun getGtk3AccentColor(
    gtk3ConfigPath: String = "${getCurrentUserConfigDir()}/gtk-3.0/gtk.css"
): Color {
    /*
    Example output:

    > grep -oP '(?<=@define-color accent_color \#)[a-fA-F0-9]{6}' ~/.config/gtk-3.0/gtk.css
    c4b28a
    */
    val output = buildBashCommand("""grep -oP '(?<=@define-color accent_color \#)[a-fA-F0-9]{6}' $gtk3ConfigPath""")
        .use(Process::getOutput)

    return Color.fromHexRgb(output)
}

internal val isGtk3Available: Boolean by lazy(LazyThreadSafetyMode.NONE) {
    val gtk3Dir = File("${getCurrentUserConfigDir()}/gtk-3.0")
    gtk3Dir.exists() && gtk3Dir.isDirectory
}