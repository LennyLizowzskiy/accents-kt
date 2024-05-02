@file:Suppress("unused")

package com.lizowzskiy.accents


/**
 * Get system-wide accent color of the current target
 *
 * @throws UnauthorizedAccessException if we couldn't receive the output from a machine because the system rejected the call
 * @throws UnsupportedOutputException if we could receive the output a machine, but the implementation of the parser couldn't convert it
 * @throws UnsupportedOperationException if given environment is not supported
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
         * @param input [String] in the format of `#RRGGBBAA`, where `#` and `AA` are optional
         *
         * @throws IllegalArgumentException if [input] is considered invalid
         */
        fun fromHexRgb(input: String): Color {
            val chunked = input
                .replaceFirst("#", "")
                .chunked(2)

            return when (input.length) {
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