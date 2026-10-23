package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.MeetingViewModel

@Composable
fun MeetingsListScreen(
    viewModel: MeetingViewModel,
    onNavigateToMeetingDetail: (String) -> Unit
) {
    val meetings by viewModel.meetings.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mes réunions", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        if (meetings.isEmpty()) {
            Text("Aucune réunion pour le moment.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn {
                items(meetings) { meeting ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onNavigateToMeetingDetail(meeting.id) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(meeting.title, style = MaterialTheme.typography.titleMedium)
                            Text(meeting.status.name, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
