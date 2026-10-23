package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.model.TranscriptSegment
import kotlinx.coroutines.flow.Flow

@Dao
interface TranscriptDao {
    @Query("SELECT * FROM transcript_segments WHERE meetingId = :meetingId ORDER BY sequence ASC, startOffsetMs ASC")
    fun getSegmentsForMeeting(meetingId: String): Flow<List<TranscriptSegment>>

    @Query("SELECT * FROM transcript_segments WHERE meetingId = :meetingId ORDER BY sequence ASC, startOffsetMs ASC")
    suspend fun getSegmentsForMeetingDirect(meetingId: String): List<TranscriptSegment>

    @Query("SELECT MAX(sequence) FROM transcript_segments WHERE meetingId = :meetingId")
    suspend fun getMaxSequence(meetingId: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSegment(segment: TranscriptSegment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSegments(segments: List<TranscriptSegment>)

    @Update
    suspend fun updateSegment(segment: TranscriptSegment)

    @Query("UPDATE transcript_segments SET speakerLabel = :newLabel WHERE meetingId = :meetingId AND (speakerLabel = :oldLabel OR (:oldLabel IS NULL AND speakerLabel IS NULL))")
    suspend fun updateSpeakerLabel(meetingId: String, oldLabel: String?, newLabel: String)

    @Query("DELETE FROM transcript_segments WHERE meetingId = :meetingId")
    suspend fun deleteSegmentsForMeeting(meetingId: String)

    // FTS full-text search join
    @Query("""
        SELECT ts.* FROM transcript_segments ts
        JOIN transcript_fts fts ON fts.rowid = ts.rowid
        WHERE transcript_fts MATCH :query
        ORDER BY ts.meetingId, ts.sequence ASC
    """)
    suspend fun searchSegmentsFts(query: String): List<TranscriptSegment>

    // Standard fallback LIKE search if FTS query syntax is raw
    @Query("SELECT * FROM transcript_segments WHERE text LIKE '%' || :query || '%' ORDER BY meetingId, sequence ASC")
    suspend fun searchSegmentsLike(query: String): List<TranscriptSegment>
}
