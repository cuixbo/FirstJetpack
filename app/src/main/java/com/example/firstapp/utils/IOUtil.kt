package com.example.firstapp.utils

import java.io.InputStream
import java.io.OutputStream


public inline fun InputStream.copyTo(
    out: OutputStream,
    bufferSize: Int = DEFAULT_BUFFER_SIZE,
    progress: (bytes: Long) -> Unit,
): Long {
    var bytesCopied: Long = 0
    val buffer = ByteArray(bufferSize)
    var bytes = read(buffer)
    while (bytes >= 0) {
        out.write(buffer, 0, bytes)
        out.flush()
        bytesCopied += bytes
        bytes = read(buffer)
        progress(bytesCopied)
    }
    return bytesCopied
}

