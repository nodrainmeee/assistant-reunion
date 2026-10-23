package com.example.engine

import com.example.model.MeetingSummary

/**
 * Placeholder meeting analyzer.
 * Will be connected to Gemini / Firebase AI later.
 */
class MeetingAnalyzer {

    suspend fun analyzeMeeting(
        meetingId: String,
        transcriptText: String
    ): MeetingSummary {
        // Simulated analysis
        return MeetingSummary(
            meetingId = meetingId,
            summaryText = "Compte-rendu généré automatiquement (version de démonstration).\n\nLe contenu complet de la transcription sera analysé par Gemini une fois la clé API configurée.",
            keyPointsJson = "[\"Point clé 1\", \"Point clé 2\"]",
            importantDetailsJson = "[]"
        )
    }
}
