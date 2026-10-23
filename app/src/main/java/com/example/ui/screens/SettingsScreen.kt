package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MeetingViewModel

@Composable
fun SettingsScreen(viewModel: MeetingViewModel) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("Réglages", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Configure ta clé Gemini dans le fichier .env", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("GEMINI_API_KEY=ta_clé_ici", style = MaterialTheme.typography.bodySmall)
    }
}
