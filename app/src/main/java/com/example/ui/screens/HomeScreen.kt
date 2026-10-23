package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.viewmodel.MeetingViewModel

@Composable
fun HomeScreen(
    viewModel: MeetingViewModel,
    onNavigateToNewMeeting: () -> Unit,
    onNavigateToMeetingDetail: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToActiveMeeting: () -> Unit
) {
    val meetings by viewModel.meetings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.home_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = onNavigateToNewMeeting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.home_btn_new_meeting))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${meetings.size} réunion(s)",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
