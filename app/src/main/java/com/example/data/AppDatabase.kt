package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.model.Meeting
import com.example.model.MeetingAnalysis
import com.example.model.TranscriptSegment
import com.example.model.Converters

@Database(
    entities = [Meeting::class, TranscriptSegment::class, MeetingAnalysis::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun meetingDao(): MeetingDao
    abstract fun transcriptDao(): TranscriptDao
    abstract fun analysisDao(): AnalysisDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "assistant_reunion_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
