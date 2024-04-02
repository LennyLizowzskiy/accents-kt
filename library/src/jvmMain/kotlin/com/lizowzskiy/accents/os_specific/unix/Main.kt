package com.lizowzskiy.accents.os_specific.unix

import com.lizowzskiy.accents.Color
import com.lizowzskiy.accents.os_specific.unix.UnixEnvironment.FreeDesktop
import com.lizowzskiy.accents.os_specific.unix.UnixEnvironment.Gtk
import com.lizowzskiy.accents.os_specific.unix.UnixEnvironment.Kde


internal fun getUnixAccentColor(): Color =
    when (UnixEnvironment.current) {
        Gtk -> getGtk3AccentColor()
        Kde -> getKdeAccentColor()
        FreeDesktop -> getFreedesktopAccentColor()
    }

internal fun getCurrentUserHomePath(): String =
    System.getenv("HOME")

internal fun getCurrentUserConfigDir(
    fallback: String = "${getCurrentUserHomePath()}/.config"
): String =
    System.getenv("XDG_CONFIG_HOME") ?: fallback

internal enum class UnixEnvironment {
    Gtk,
    Kde,

    /**
     * XDG, dbus & systemd
     */
    FreeDesktop; // Wayland

    companion object {
        @get:Throws(IllegalStateException::class)
        val current: UnixEnvironment by lazy {
            when {
                isFreeDesktopAvailable -> FreeDesktop
                isKdeAvailable -> Kde
                isGtk3Available -> Gtk
                // gtk has the lowest tier because it's installed on basically every desktop Linux system

                else -> throw IllegalStateException("host unix environment is not supported")
            }
        }
    }
}

