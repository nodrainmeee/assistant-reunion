package com.example.firebase

enum class FirebaseStatus {
    NOT_CONFIGURED,
    READY,
    ERROR
}

/**
 * Placeholder for Gemini / Firebase AI integration.
 * Configure with your GEMINI_API_KEY in .env
 */
class FirebaseAIService {

    private var status: FirebaseStatus = FirebaseStatus.NOT_CONFIGURED

    fun getStatus(): FirebaseStatus = status

    fun initializeWithCredentials(apiKey: String, appId: String = "", projectId: String = ""): Result<Unit> {
        return if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            status = FirebaseStatus.READY
            Result.success(Unit)
        } else {
            status = FirebaseStatus.NOT_CONFIGURED
            Result.failure(IllegalArgumentException("Clé Gemini manquante ou invalide"))
        }
    }

    suspend fun generateSummary(transcript: String): String {
        if (status != FirebaseStatus.READY) {
            return "Configure ta clé Gemini pour activer l'analyse IA."
        }
        // Placeholder - real call to Gemini would go here
        return "Résumé généré par IA (simulation).\n\n$transcript.take(200)..."
    }
}
