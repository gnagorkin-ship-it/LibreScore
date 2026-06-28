package org.librescore.midi

import org.librescore.common.*
import java.io.ByteArrayOutputStream

/** Minimal Standard MIDI File type-0 writer for recognized monophonic scores. */
class MidiWriter {
    fun write(score: RecognizedScore): ByteArray { val track=ByteArrayOutputStream(); metaTempo(track, score.tempoBpm); score.measures.flatMap{it.notes}.forEach { note(track, midiNumber(it), ticks(it.durationDivisions, score.divisions), it.velocity) }; track.write(byteArrayOf(0,0xFF.toByte(),0x2F,0)); val data=track.toByteArray(); return ByteArrayOutputStream().apply { write("MThd".toByteArray()); i32(6); i16(0); i16(1); i16(480); write("MTrk".toByteArray()); i32(data.size); write(data) }.toByteArray() }
    private fun metaTempo(o: ByteArrayOutputStream, bpm:Int){ o.write(0); o.write(byteArrayOf(0xFF.toByte(),0x51,3)); val mpq=60_000_000/bpm; o.write(byteArrayOf((mpq shr 16).toByte(),(mpq shr 8).toByte(),mpq.toByte())) }
    private fun note(o:ByteArrayOutputStream,n:Int,t:Int,v:Int){ o.write(0); o.write(byteArrayOf(0x90.toByte(),n.toByte(),v.toByte())); var delta=t; val bytes=ArrayDeque<Int>(); bytes.addFirst(delta and 0x7F); delta = delta shr 7; while(delta>0){ bytes.addFirst((delta and 0x7F) or 0x80); delta=delta shr 7 }; bytes.forEach{o.write(it)}; o.write(byteArrayOf(0x80.toByte(),n.toByte(),0)) }
    private fun midiNumber(n:RecognizedNote)= (n.octave+1)*12 + mapOf("C" to 0,"D" to 2,"E" to 4,"F" to 5,"G" to 7,"A" to 9,"B" to 11).getValue(n.pitch)
    private fun ticks(d:Int, div:Int)= d * 480 / div
    private fun ByteArrayOutputStream.i16(v:Int){ write(byteArrayOf((v shr 8).toByte(), v.toByte())) }; private fun ByteArrayOutputStream.i32(v:Int){ write(byteArrayOf((v shr 24).toByte(),(v shr 16).toByte(),(v shr 8).toByte(),v.toByte())) }
}
