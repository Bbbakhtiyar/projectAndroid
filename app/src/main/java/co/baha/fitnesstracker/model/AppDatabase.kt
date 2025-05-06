package co.baha.fitnesstracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Calc::class, WaterTracker::class], version = 2)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun calcDao(): CalcDao
    abstract fun waterTrackerDao(): WaterTrackerDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return if (INSTANCE == null) {
                synchronized(this) {
                    val migration_1_2 = object : Migration(1, 2) {
                        override fun migrate(database: SupportSQLiteDatabase) {
                            // Миграция: создание таблицы WaterTracker
                            database.execSQL("""
                                CREATE TABLE IF NOT EXISTS `WaterTracker` (
                                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                                    `amount` REAL NOT NULL, 
                                    `date` INTEGER NOT NULL
                                )
                            """)
                        }
                    }

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "fitness_tracker"
                    )
                        .addMigrations(migration_1_2)
                        .build()
                }
                INSTANCE as AppDatabase
            } else {
                INSTANCE as AppDatabase
            }
        }
    }
}