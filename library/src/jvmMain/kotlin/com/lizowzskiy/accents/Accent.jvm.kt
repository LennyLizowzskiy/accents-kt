package com.lizowzskiy.accents

import com.lizowzskiy.accents.os_specific.getMacOsAccentColor
import com.lizowzskiy.accents.os_specific.getWindowsAccentColor
import com.lizowzskiy.accents.os_specific.unix.getUnixAccentColor
import org.apache.commons.lang3.SystemUtils

/**
 * Get system-wide accent color of the current target
 */
actual fun getAccentColor(): Color =
    when {
        SystemUtils.IS_OS_WINDOWS -> getWindowsAccentColor()
        SystemUtils.IS_OS_MAC -> getMacOsAccentColor()
        SystemUtils.IS_OS_UNIX -> getUnixAccentColor()

        else -> throw IllegalArgumentException(
            "retrieving accent color on current system " +
                    "(name: ${System.getProperty("os.name")}, " +
                    "version: ${System.getProperty("version")})" +
                    "is not supported")
    }