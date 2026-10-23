package com.example.export

import android.content.Context
import com.example.model.Meeting
import com.example.model.TranscriptSegment
import java.io.File

/**
 * Simple exporter for meeting transcripts and summaries.
 */
class MeetingExporter(private val context: Context) {

    fun exportAsText(
        meeting: Meeting,
        segments: List<TranscriptSegment>,
        summary: String = ""
    ): File {
        val fileName = "reunion_${meeting.id.take(8)}.txt"
        val file = File(context.getExternalFilesDir(null), fileName)

        val content = buildString {
            appendLine("=== ${meeting.title} ===")
            appendLine("Projet : ${meeting.project.ifBlank { "-" }}")
            appendLine("Statut : ${meeting.status}")
            appendLine()
            if (summary.isNotBlank()) {
                appendLine("--- Compte-rendu ---")
                appendLine(summary)
                appendLine()
            }
            appendLine("--- Transcription ---")
            segments.forEach { seg ->
                val speaker = seg.speakerLabel ?: "Locuteur"
                appendLine("[$speaker] ${seg.text}")
            }
        }

        file.writeText(content)
        return file
    }
}
