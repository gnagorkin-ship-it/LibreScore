package org.librescore.musicxml
import org.junit.jupiter.api.Assertions.*; import org.junit.jupiter.api.Test; import org.librescore.common.*
class MusicXmlWriterTest { @Test fun writesXml(){ val s=RecognizedScore("T", listOf(RecognizedPage(0, listOf(RecognizedMeasure(listOf(RecognizedNote("C",4,4)))), listOf()))); assertTrue(MusicXmlWriter().write(s).contains("score-partwise")); assertTrue(MusicXmlWriter().writeCompressed(s).isNotEmpty()) } }
