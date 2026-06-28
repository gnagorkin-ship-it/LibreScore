package org.librescore.musicxml

import org.librescore.common.*
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/** Writes standards-compatible MusicXML 4.0 partwise documents and .mxl containers. */
class MusicXmlWriter {
    fun write(score: RecognizedScore): String = buildString {
        appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
        appendLine("<score-partwise version=\"4.0\"><work><work-title>${score.title.xml()}</work-title></work><part-list><score-part id=\"P1\"><part-name>Piano</part-name></score-part></part-list><part id=\"P1\">")
        score.measures.forEachIndexed { m, measure -> appendLine("<measure number=\"${m+1}\"><attributes><divisions>${score.divisions}</divisions><key><fifths>0</fifths></key><time><beats>4</beats><beat-type>4</beat-type></time><clef><sign>G</sign><line>2</line></clef></attributes><sound tempo=\"${score.tempoBpm}\"/>"); measure.notes.forEach { n -> appendLine("<note><pitch><step>${n.pitch}</step><octave>${n.octave}</octave></pitch><duration>${n.durationDivisions}</duration><type>quarter</type></note>") }; appendLine("</measure>") }
        appendLine("</part></score-partwise>")
    }
    fun writeCompressed(score: RecognizedScore): ByteArray { val xml = write(score).toByteArray(); val out=ByteArrayOutputStream(); ZipOutputStream(out).use { zip -> zip.putNextEntry(ZipEntry("META-INF/container.xml")); zip.write(CONTAINER); zip.closeEntry(); zip.putNextEntry(ZipEntry("score.musicxml")); zip.write(xml); zip.closeEntry() }; return out.toByteArray() }
    private fun String.xml() = replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;")
    companion object { private val CONTAINER = """<?xml version="1.0" encoding="UTF-8"?><container version="1.0" xmlns="urn:oasis:names:tc:opendocument:xmlns:container"><rootfiles><rootfile full-path="score.musicxml" media-type="application/vnd.recordare.musicxml+xml"/></rootfiles></container>""".toByteArray() }
}
