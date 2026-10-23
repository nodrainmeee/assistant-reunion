package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.example.model.MeetingAnalysis

@Dao
interface AnalysisDao {
    @Query("SELECT * FROM meeting_analysis WHERE meetingId = :meetingId")
    fun getAnalysisForMeeting(meetingId: Long): Flow<MeetingAnalysis?>

    @Query("SELECT * FROM meeting_analysis WHERE meetingId = :meetingId")
    suspend fun getAnalysisForMeetingOnce(meetingId: Long): MeetingAnalysis?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(analysis: MeetingAnalysis): Long

    @Query("DELETE FROM meeting_analysis WHERE meetingId = :meetingId")
    suspend fun deleteForMeeting(meetingId: Long)
}
