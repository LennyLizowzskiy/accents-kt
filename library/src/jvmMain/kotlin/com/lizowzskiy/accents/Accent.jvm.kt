@file:JvmName("AccentUtil")

package com.lizowzskiy.accents

import com.lizowzskiy.accents.os_specific.getMacOsAccentColor
import com.lizowzskiy.accents.os_specific.getWindowsAccentColor
import com.lizowzskiy.accents.os_specific.unix.getUnixAccentColor
import org.apache.commons.lang3.SystemUtils

actual fun getAccentColor(): Color =
    when {
        SystemUtils.IS_OS_WINDOWS -> getWindowsAccentColor()
        SystemUtils.IS_OS_MAC -> getMacOsAccentColor()
        SystemUtils.IS_OS_UNIX -> getUnixAccentColor()

        else -> throwUnsupportedOsException()
    }