package org.librescore.player

import android.content.Context
import android.media.MediaPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class MidiScorePlayer(private val context: Context) {
    private var player: MediaPlayer? = null
    private val _state = MutableStateFlow(PlayerState())
    val state: StateFlow<PlayerState> = _state
    fun load(midi: ByteArray) { stop(); val file=File.createTempFile("librescore", ".mid", context.cacheDir); file.writeBytes(midi); player = MediaPlayer().apply { setDataSource(file.absolutePath); prepare(); setOnCompletionListener { _state.value = _state.value.copy(isPlaying=false, positionMs=duration) } }; _state.value=PlayerState(durationMs=player?.duration ?: 0) }
    fun play(){ player?.start(); _state.value=_state.value.copy(isPlaying=true) }
    fun pause(){ player?.pause(); _state.value=_state.value.copy(isPlaying=false, positionMs=player?.currentPosition ?: 0) }
    fun stop(){ player?.release(); player=null; _state.value=PlayerState() }
    fun seek(ms:Int){ player?.seekTo(ms); _state.value=_state.value.copy(positionMs=ms) }
}
data class PlayerState(val isPlaying:Boolean=false, val positionMs:Int=0, val durationMs:Int=0, val loop:Boolean=false, val tempo:Float=1f)
