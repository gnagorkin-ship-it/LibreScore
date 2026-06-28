package org.librescore.common

/** A rendered page image in portable grayscale pixels. */
data class ScoreBitmap(val width: Int, val height: Int, val argb: IntArray) { init { require(argb.size == width * height) } }

data class RecognizedNote(val pitch: String, val octave: Int, val durationDivisions: Int, val velocity: Int = 80)
data class RecognizedMeasure(val notes: List<RecognizedNote>)
data class RecognizedPage(val pageIndex: Int, val measures: List<RecognizedMeasure>, val staffLines: List<Int>)
data class RecognizedScore(val title: String, val pages: List<RecognizedPage>, val divisions: Int = 4, val tempoBpm: Int = 96) {
    val measures: List<RecognizedMeasure> get() = pages.flatMap { it.measures }
}
sealed interface LibreScoreResult<out T> { data class Success<T>(val value: T): LibreScoreResult<T>; data class Failure(val message: String, val cause: Throwable? = null): LibreScoreResult<Nothing> }
