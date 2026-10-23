package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MeetingViewModel

@Composable
fun ProcessingScreen(
    viewModel: MeetingViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    val progress by viewModel.processingProgress.collectAsState()
    val currentMeeting by viewModel.currentMeeting.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startProcessing()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Analyse en cours…", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        LinearProgressIndicator(progress = { progress }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        if (progress >= 1f) {
            Button(onClick = {
                currentMeeting?.id?.let { onNavigateToDetail(it) }
            }) {
                Text("Voir le compte-rendu")
            }
        }
    }
}
