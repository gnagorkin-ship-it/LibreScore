package org.librescore.storage

import android.content.Context
import android.content.Intent
import android.net.Uri

class ScoreExporter(private val context: Context) {
    fun share(uri: Uri, mime: String): Intent = Intent(Intent.ACTION_SEND).setType(mime).putExtra(Intent.EXTRA_STREAM, uri).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    fun write(uri: Uri, bytes: ByteArray) { context.contentResolver.openOutputStream(uri)?.use { it.write(bytes) } ?: error("Unable to open export destination") }
}
