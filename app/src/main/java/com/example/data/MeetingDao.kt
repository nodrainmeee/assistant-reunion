package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.Meeting
import com.example.model.MeetingStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface MeetingDao {
    @Query("SELECT * FROM meetings ORDER BY createdAt DESC")
    fun getAllMeetings(): Flow<List<Meeting>>

    @Query("SELECT * FROM meetings WHERE id = :id")
    fun getMeetingById(id: String): Flow<Meeting?>

    @Query("SELECT * FROM meetings WHERE id = :id")
    suspend fun getMeetingByIdDirect(id: String): Meeting?

    @Query("SELECT * FROM meetings WHERE status = 'ACTIVE' OR status = 'PAUSED' ORDER BY createdAt DESC LIMIT 1")
    suspend fun getInterruptedMeeting(): Meeting?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeeting(meeting: Meeting)

    @Update
    suspend fun updateMeeting(meeting: Meeting)

    @Query("UPDATE meetings SET status = :status, durationMs = :durationMs, endedAt = :endedAt WHERE id = :id")
    suspend fun updateMeetingStatus(id: String, status: MeetingStatus, durationMs: Long, endedAt: Long?)

    @Query("DELETE FROM meetings WHERE id = :id")
    suspend fun deleteMeetingById(id: String)

    @Query("SELECT DISTINCT project FROM meetings WHERE project != '' ORDER BY project ASC")
    fun getAllProjects(): Flow<List<String>>

    @Query("SELECT * FROM meetings WHERE project = :project AND id != :excludeMeetingId ORDER BY createdAt DESC")
    suspend fun getPreviousMeetingsInProject(project: String, excludeMeetingId: String = ""): List<Meeting>

    @Query("SELECT * FROM meetings WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR project LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    suspend fun searchMeetings(query: String): List<Meeting>
}
