package org.librescore.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.librescore.common.ScoreBitmap

/** Streams Android PdfRenderer pages into bounded ARGB bitmaps suitable for OMR. */
class PdfPageRenderer(private val context: Context) {
    suspend fun render(uri: Uri, maxWidth: Int = 1800): List<ScoreBitmap> = withContext(Dispatchers.IO) {
        context.contentResolver.openFileDescriptor(uri, "r")!!.use { fd ->
            PdfRenderer(ParcelFileDescriptor.dup(fd.fileDescriptor)).use { renderer ->
                (0 until renderer.pageCount).map { index -> renderer.openPage(index).use { page ->
                    val scale = maxWidth.toFloat() / page.width.toFloat(); val w=maxWidth; val h=(page.height*scale).toInt().coerceAtLeast(1)
                    val bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888); bitmap.eraseColor(Color.WHITE); page.render(bitmap,null,null,PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    val pixels=IntArray(w*h); bitmap.getPixels(pixels,0,w,0,0,w,h); bitmap.recycle(); ScoreBitmap(w,h,pixels)
                } }
            }
        }
    }
}
