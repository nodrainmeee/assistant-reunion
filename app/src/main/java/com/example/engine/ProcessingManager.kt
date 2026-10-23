package com.example.engine

enum class ProcessingStep {
    TRANSCRIBING,
    DIARIZING,
    ANALYZING,
    DONE,
    FAILED
}

class ProcessingManager(
    private val transcriptionEngine: TranscriptionEngine = TranscriptionEngine(),
    private val meetingAnalyzer: MeetingAnalyzer = MeetingAnalyzer()
) {
    suspend fun processMeeting(meetingId: String, fullTranscript: String): ProcessingStep {
        // Simple sequential pipeline
        transcriptionEngine.finalizeTranscription(meetingId)
        meetingAnalyzer.analyzeMeeting(meetingId, fullTranscript)
        return ProcessingStep.DONE
    }
}
