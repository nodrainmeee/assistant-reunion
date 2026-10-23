package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MeetingViewModel

@Composable
fun ActiveMeetingScreen(
    viewModel: MeetingViewModel,
    onNavigateToProcessing: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val currentMeeting by viewModel.currentMeeting.collectAsState()
    val isRecording by viewModel.isRecording.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = currentMeeting?.title ?: "Réunion en cours",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (isRecording) "Enregistrement en cours…" else "En pause",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.stopRecording()
                onNavigateToProcessing()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Terminer la réunion")
        }
    }
}
