package no.shortcut.demo

object Utils {
    private const val HEX_CHARS = "0123456789ABCDEF"

    fun ByteArray.bytesToHex(): String {

        val hex = CharArray(2 * this.size)
        this.forEachIndexed { i, byte ->
            val unsigned = 0xff and byte.toInt()
            hex[2 * i] = HEX_CHARS[unsigned / 16]
            hex[2 * i + 1] = HEX_CHARS[unsigned % 16]
        }
        return hex.joinToString("")
    }
}