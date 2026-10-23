package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MeetingViewModel

@Composable
fun MeetingDetailScreen(
    meetingId: String,
    viewModel: MeetingViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(meetingId) {
        viewModel.selectMeeting(meetingId)
    }

    val meeting by viewModel.currentMeeting.collectAsState()
    val segments by viewModel.transcriptSegments.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("← Retour") }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = meeting?.title ?: "Détail de la réunion",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Statut : ${meeting?.status?.name ?: "-"}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Segments de transcription : ${segments.size}")
    }
}
