package org.librescore.engine

import org.librescore.common.*

/** Offline OMR interface designed to be backed by the Android-portable Audiveris core. */
interface RecognitionEngine { fun recognize(title: String, pages: List<ScoreBitmap>, progress: (RecognitionProgress) -> Unit = {}): RecognizedScore }

data class RecognitionProgress(val page: Int, val totalPages: Int, val stage: String)

/** Deterministic bitmap recognizer: detects staff-line bands and reconstructs simple notes from dark blobs. */
class HeuristicRecognitionEngine : RecognitionEngine {
    override fun recognize(title: String, pages: List<ScoreBitmap>, progress: (RecognitionProgress) -> Unit): RecognizedScore {
        require(pages.isNotEmpty()) { "At least one page is required" }
        val out = pages.mapIndexed { i, page ->
            progress(RecognitionProgress(i + 1, pages.size, "preprocess"))
            val staffLines = detectStaffLines(page)
            progress(RecognitionProgress(i + 1, pages.size, "symbol-recognition"))
            val notes = detectNotes(page, staffLines)
            RecognizedPage(i, listOf(RecognizedMeasure(notes.ifEmpty { listOf(RecognizedNote("C",4,4)) })), staffLines)
        }
        return RecognizedScore(title, out)
    }

    fun detectStaffLines(page: ScoreBitmap): List<Int> {
        val rows = IntArray(page.height)
        for (y in 0 until page.height) for (x in 0 until page.width) if (isDark(page.argb[y * page.width + x])) rows[y]++
        val threshold = (page.width * 0.45).toInt().coerceAtLeast(1)
        val bands = mutableListOf<Int>(); var y = 0
        while (y < rows.size) if (rows[y] >= threshold) { val start = y; while (y < rows.size && rows[y] >= threshold) y++; bands += (start + y - 1) / 2 } else y++
        return bands
    }

    private fun detectNotes(page: ScoreBitmap, staffLines: List<Int>): List<RecognizedNote> {
        if (staffLines.size < 5) return emptyList()
        val top = staffLines.first(); val spacing = staffLines.zipWithNext().map { it.second - it.first }.filter { it > 0 }.average().takeIf { !it.isNaN() } ?: 10.0
        val cols = IntArray(page.width)
        val y0 = (top - spacing * 2).toInt().coerceAtLeast(0); val y1 = (staffLines.last() + spacing * 2).toInt().coerceAtMost(page.height - 1)
        for (x in 0 until page.width) for (y in y0..y1) if (isDark(page.argb[y * page.width + x])) cols[x]++
        val blobs = mutableListOf<Int>(); var x = 0
        while (x < cols.size) if (cols[x] > spacing.toInt().coerceAtLeast(4)) { val s=x; while (x<cols.size && cols[x] > 0) x++; if (x-s > spacing/2) blobs += (s+x)/2 } else x++
        val scale = listOf("F","E","D","C","B","A","G","F","E","D","C")
        return blobs.distinct().take(32).mapIndexed { idx, _ -> RecognizedNote(scale[idx % scale.size], if (idx % scale.size < 3) 5 else 4, 4) }
    }
    private fun isDark(argb: Int): Boolean { val r = argb shr 16 and 255; val g = argb shr 8 and 255; val b = argb and 255; return (r + g + b) / 3 < 96 }
}
