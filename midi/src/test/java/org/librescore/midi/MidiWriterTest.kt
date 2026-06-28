package org.librescore.midi
import org.junit.jupiter.api.Assertions.*; import org.junit.jupiter.api.Test; import org.librescore.common.*
class MidiWriterTest { @Test fun writesHeader(){ val s=RecognizedScore("T", listOf(RecognizedPage(0, listOf(RecognizedMeasure(listOf(RecognizedNote("C",4,4)))), listOf()))); assertEquals("MThd", String(MidiWriter().write(s).take(4).toByteArray())) } }
