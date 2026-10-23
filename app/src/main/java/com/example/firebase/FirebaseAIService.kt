package com.example.firebase

enum class FirebaseStatus {
    READY          // Toujours prêt (mode simulation)
}

/**
 * Mode simulation pure — aucune clé API nécessaire.
 */
class FirebaseAIService {

    fun getStatus(): FirebaseStatus = FirebaseStatus.READY

    fun initializeWithCredentials(apiKey: String = "", appId: String = "", projectId: String = ""): Result<Unit> {
        return Result.success(Unit)
    }

    suspend fun generateSummary(transcript: String): String {
        return buildString {
            appendLine("📋 Compte-rendu de réunion (simulation)")
            appendLine()
            appendLine("• Points principaux discutés")
            appendLine("• Décisions prises")
            appendLine("• Actions à suivre")
            appendLine()
            appendLine("---")
            appendLine("Extrait de la transcription :")
            appendLine(transcript.take(300).ifBlank { "Aucun contenu disponible." })
        }
    }
}
