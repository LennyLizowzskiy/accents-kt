package com.lizowzskiy.accents.os_specific.unix

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.UnauthorizedAccessException
import com.lizowzskiy.accents.UnsupportedOutputException
import com.lizowzskiy.accents.util.buildBashCommand
import com.lizowzskiy.accents.util.getFullOutput
import java.io.File

/**
 * Get GTK3 accent color from the currently active theme
 */
internal fun getGtk3AccentColor(
    gtk3ConfigPath: String = "${getCurrentUserConfigDir()}/gtk-3.0/gtk.css"
): Color {
    /*
    Example output:

    > grep -oP '(?<=@define-color accent_color \#)[a-fA-F0-9]{6}' ~/.config/gtk-3.0/gtk.css
    c4b28a
    */
    val output = try {
        buildBashCommand("""grep -oP '(?<=@define-color accent_color \#)[a-fA-F0-9]{6}' $gtk3ConfigPath""")
            .getFullOutput()
    } catch (e: SecurityException) {
        throw UnauthorizedAccessException(cause = e)
    }

    val result = try {
        Color.fromHexRgb(output)
    } catch (e: IllegalArgumentException) {
        throw UnsupportedOutputException(output, cause = e)
    }

    return result
}

internal val isGtk3Available: Boolean by lazy(LazyThreadSafetyMode.NONE) {
    val gtk3Dir = File("${getCurrentUserConfigDir()}/gtk-3.0")
    gtk3Dir.exists() && gtk3Dir.isDirectory
}