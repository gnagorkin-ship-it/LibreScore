package org.librescore.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.librescore.engine.HeuristicRecognitionEngine
import org.librescore.common.ScoreBitmap
import org.librescore.musicxml.MusicXmlWriter
import org.librescore.midi.MidiWriter

class MainActivity : ComponentActivity() { override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { LibreScoreApp() } } }

@Composable fun LibreScoreApp() { MaterialTheme { Surface(Modifier.fillMaxSize()) { AdaptiveHome() } } }

@Composable private fun AdaptiveHome() {
    var status by remember { mutableStateOf("Ready for offline PDF recognition") }
    val engine = remember { HeuristicRecognitionEngine() }
    val xml = remember { MusicXmlWriter() }
    val midi = remember { MidiWriter() }
    Row(Modifier.fillMaxSize().padding(24.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("LibreScore", style = MaterialTheme.typography.displaySmall)
            Text("Offline optical music recognition for Android. Import PDFs, recognize notation, export MusicXML, MXL and MIDI.")
            Button(onClick = { val score = engine.recognize("Sample", listOf(sampleStaff())); status = "Recognized ${score.measures.size} measure(s), MusicXML ${xml.write(score).length} chars, MIDI ${midi.write(score).size} bytes" }) { Text("Run built-in recognition sample") }
        }
        Card(Modifier.weight(1f).fillMaxHeight()) { Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { Text("Workflow", style = MaterialTheme.typography.headlineSmall); listOf("PDF Import", "PDF Preview", "Recognition", "Playback", "Export", "Settings", "About").forEach { Text("• $it") }; Text(status) } }
    }
}

private fun sampleStaff(): ScoreBitmap { val w=400; val h=160; val p=IntArray(w*h){0xFFFFFFFF.toInt()}; listOf(50,60,70,80,90).forEach { y -> for (x in 20 until 380) p[y*w+x]=0xFF000000.toInt() }; for (y in 58..76) for (x in 110..128) p[y*w+x]=0xFF000000.toInt(); return ScoreBitmap(w,h,p) }
