package com.example.data

import com.example.model.Meeting
import com.example.model.MeetingStatus
import com.example.model.TranscriptSegment
import kotlinx.coroutines.flow.Flow

class MeetingRepository(
    private val meetingDao: MeetingDao,
    private val transcriptDao: TranscriptDao,
    private val analysisDao: AnalysisDao
) {
    fun getAllMeetings(): Flow<List<Meeting>> = meetingDao.getAllMeetings()

    suspend fun getMeetingById(id: String): Meeting? = meetingDao.getMeetingByIdDirect(id)

    suspend fun insertMeeting(meeting: Meeting) = meetingDao.insertMeeting(meeting)

    suspend fun updateMeetingStatus(id: String, status: MeetingStatus, durationMs: Long, endedAt: Long?) =
        meetingDao.updateMeetingStatus(id, status, durationMs, endedAt)

    suspend fun deleteMeeting(id: String) = meetingDao.deleteMeetingById(id)

    fun getSegmentsForMeeting(meetingId: String): Flow<List<TranscriptSegment>> =
        transcriptDao.getSegmentsForMeeting(meetingId)

    suspend fun insertSegment(segment: TranscriptSegment) = transcriptDao.insertSegment(segment)

    suspend fun searchMeetings(query: String): List<Meeting> = meetingDao.searchMeetings(query)
}
