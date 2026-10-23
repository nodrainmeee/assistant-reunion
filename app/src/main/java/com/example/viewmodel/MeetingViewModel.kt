package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.model.Meeting
import com.example.model.MeetingStatus
import com.example.model.TranscriptSegment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MeetingViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val meetingDao = database.meetingDao()
    private val transcriptDao = database.transcriptDao()

    private val _meetings = MutableStateFlow<List<Meeting>>(emptyList())
    val meetings: StateFlow<List<Meeting>> = _meetings.asStateFlow()

    private val _currentMeeting = MutableStateFlow<Meeting?>(null)
    val currentMeeting: StateFlow<Meeting?> = _currentMeeting.asStateFlow()

    private val _transcriptSegments = MutableStateFlow<List<TranscriptSegment>>(emptyList())
    val transcriptSegments: StateFlow<List<TranscriptSegment>> = _transcriptSegments.asStateFlow()

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _processingProgress = MutableStateFlow(0f)
    val processingProgress: StateFlow<Float> = _processingProgress.asStateFlow()

    init {
        viewModelScope.launch {
            meetingDao.getAllMeetings().collect { list ->
                _meetings.value = list
            }
        }
    }

    fun selectMeeting(meetingId: String) {
        viewModelScope.launch {
            _currentMeeting.value = meetingDao.getMeetingByIdDirect(meetingId)
            transcriptDao.getSegmentsForMeeting(meetingId).collect { segments ->
                _transcriptSegments.value = segments
            }
        }
    }

    fun createMeeting(title: String, project: String = "", description: String = "") {
        viewModelScope.launch {
            val meeting = Meeting(
                id = UUID.randomUUID().toString(),
                title = title,
                project = project,
                description = description,
                status = MeetingStatus.ACTIVE
            )
            meetingDao.insertMeeting(meeting)
            _currentMeeting.value = meeting
            _isRecording.value = true
        }
    }

    fun stopRecording() {
        _isRecording.value = false
        viewModelScope.launch {
            _currentMeeting.value?.let { meeting ->
                meetingDao.updateMeetingStatus(
                    meeting.id,
                    MeetingStatus.ENDED,
                    System.currentTimeMillis() - meeting.startedAt,
                    System.currentTimeMillis()
                )
            }
        }
    }

    fun startProcessing() {
        viewModelScope.launch {
            _processingProgress.value = 0.1f
            // Placeholder for real processing pipeline
            _processingProgress.value = 1.0f
            _currentMeeting.value?.let { meeting ->
                meetingDao.updateMeetingStatus(
                    meeting.id,
                    MeetingStatus.ANALYZED,
                    meeting.durationMs,
                    meeting.endedAt
                )
            }
        }
    }

    fun deleteMeeting(meetingId: String) {
        viewModelScope.launch {
            meetingDao.deleteMeetingById(meetingId)
        }
    }
}
