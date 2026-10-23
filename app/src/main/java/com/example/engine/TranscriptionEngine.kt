package com.example.engine

import com.example.model.TranscriptSegment
import kotlinx.coroutines.delay

/**
 * Placeholder transcription engine.
 * Replace with real speech-to-text (Whisper, Google Speech, etc.) later.
 */
class TranscriptionEngine {

    suspend fun transcribeAudioChunk(audioData: ByteArray, meetingId: String, sequence: Int): TranscriptSegment {
        // Simulate processing delay
        delay(300)
        return TranscriptSegment(
            meetingId = meetingId,
            sequence = sequence,
            text = "[Transcription simulée - segment $sequence]",
            startOffsetMs = sequence * 3000L,
            endOffsetMs = (sequence + 1) * 3000L
        )
    }

    suspend fun finalizeTranscription(meetingId: String): List<TranscriptSegment> {
        delay(500)
        return emptyList()
    }
}
