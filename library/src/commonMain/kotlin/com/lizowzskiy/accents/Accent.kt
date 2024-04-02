@file:JvmName("AccentUtil")
@file:Suppress("unused")

package com.lizowzskiy.accents


/**
 * Get system-wide accent color of the current target
 *
 * @throws
 */
expect fun getAccentColor(): Color

data class Color internal constructor(
    val r: Short = 255,
    val g: Short = 255,
    val b: Short = 255,
    val alpha: Short = 255
) {
    internal companion object {
        /**
         * Convert hex string into [Color]
         *
         * @param input [String] in a format of `#RRGGBBAA`, where `#` and `AA` are optional
         *
         * @throws IllegalArgumentException if [input] is considered invalid
         */
        fun fromHexRgb(input: String): Color {
            val chunked = input
                .replaceFirst("#", "")
                .chunked(2)

            return when (input.length - 1) {
                6, 8 -> {
                    val (r, g, b) =
                        try {
                            chunked.map { it.toShort(16) }
                        } catch (e: Exception) {
                            throwInvalidHexCodeException(input, cause = e)
                        }

                    Color(
                        r, g, b,
                        alpha = chunked[3].toShortOrNull(16) ?: 255
                    )
                }

                else -> throwInvalidHexCodeException(input)
            }
        }

        private fun throwInvalidHexCodeException(input: Any, cause: Throwable? = null): Nothing {
            throw IllegalArgumentException("invalid hex code: $input", cause)
        }
    }
}