package com.example.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

enum class MeetingStatus {
    ACTIVE,
    PAUSED,
    ENDED,
    ANALYZING,
    ANALYZED,
    ANALYSIS_FAILED
}

enum class SegmentOrigin {
    LIVE,
    RECOVERED,
    DIARIZED
}

enum class ActionStatus {
    TODO,
    DONE
}

@Entity(
    tableName = "meetings",
    indices = [Index(value = ["project"]), Index(value = ["status"])]
)
data class Meeting(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val project: String = "",
    val language: String = "auto",
    val status: MeetingStatus = MeetingStatus.ACTIVE,
    val participants: String = "", // Comma-separated participant names
    val createdAt: Long = System.currentTimeMillis(),
    val startedAt: Long = System.currentTimeMillis(),
    val endedAt: Long? = null,
    val durationMs: Long = 0L
)

@Entity(
    tableName = "transcript_segments",
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["id"],
            childColumns = ["meetingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["meetingId"]),
        Index(value = ["meetingId", "sequence"]),
        Index(value = ["meetingId", "connectionIndex"])
    ]
)
data class TranscriptSegment(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val meetingId: String,
    val connectionIndex: Int = 0,
    val sequence: Int = 0,
    val startOffsetMs: Long = 0L,
    val endOffsetMs: Long = 0L,
    val speakerLabel: String? = null,
    val text: String,
    val origin: SegmentOrigin = SegmentOrigin.LIVE
)

@Fts4(contentEntity = TranscriptSegment::class)
@Entity(tableName = "transcript_fts")
data class TranscriptFts(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowid: Int,
    val text: String
)

@Entity(
    tableName = "speaker_mappings",
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["id"],
            childColumns = ["meetingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["meetingId"])]
)
data class SpeakerMapping(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val meetingId: String,
    val originalLabel: String,
    val displayName: String
)

@Entity(
    tableName = "manual_notes",
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["id"],
            childColumns = ["meetingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["meetingId"])]
)
data class ManualNote(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val meetingId: String,
    val timestampMs: Long = 0L,
    val text: String
)

@Entity(
    tableName = "action_items",
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["id"],
            childColumns = ["meetingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["meetingId"])]
)
data class ActionItem(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val meetingId: String,
    val task: String,
    val owner: String = "Non précisé",
    val deadline: String = "Non précisé",
    val status: ActionStatus = ActionStatus.TODO
)

@Entity(
    tableName = "decisions",
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["id"],
            childColumns = ["meetingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["meetingId"])]
)
data class Decision(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val meetingId: String,
    val text: String
)

@Entity(
    tableName = "open_questions",
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["id"],
            childColumns = ["meetingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["meetingId"])]
)
data class OpenQuestion(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val meetingId: String,
    val question: String,
    val context: String = ""
)

@Entity(
    tableName = "meeting_summaries",
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["id"],
            childColumns = ["meetingId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["meetingId"], unique = true)]
)
data class MeetingSummary(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val meetingId: String,
    val summaryText: String,
    val keyPointsJson: String = "[]",
    val importantDetailsJson: String = "[]"
)
