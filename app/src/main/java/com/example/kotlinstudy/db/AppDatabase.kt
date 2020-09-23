package com.example.kotlinstudy.db

import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.common.utils.GlobalConfig
import com.example.kotlinstudy.db.bean.TableQuestion
import com.example.kotlinstudy.db.converter.ListTypeConverter
import com.example.kotlinstudy.db.dao.QuestionDao

@Database(entities = [TableQuestion::class], version = 1)
@TypeConverters(ListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao

    companion object {
        private const val TAG = "AppDatabase"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val tempInstance2 = INSTANCE
                if (tempInstance2 != null) {
                    return tempInstance2
                }
                val databaseName = "kotlin_study"
                val instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "$databaseName.db")
                    .fallbackToDestructiveMigration()
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .setJournalMode(JournalMode.AUTOMATIC)
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyDatabase() {
            INSTANCE = null
        }

        private val migration1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                try {
                    database.execSQL("ALTER TABLE member ADD COLUMN high_risk_tips TEXT DEFAULT NULL")
                    database.execSQL("ALTER TABLE conversation ADD COLUMN create_timestamp TEXT DEFAULT NULL")
                    database.execSQL("ALTER TABLE conversation ADD COLUMN validRounds INTEGER DEFAULT 0")
                    database.execSQL("ALTER TABLE conversation ADD COLUMN max INTEGER DEFAULT 10")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                }
            }

        }

        private val migration2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {

                try {
                    database.execSQL("ALTER TABLE msg ADD COLUMN valid_rounds INTEGER NOT NULL DEFAULT 0")
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                }
            }
        }

        @JvmStatic
        fun inDb(init: (db: AppDatabase) -> Any) {
            val database = getDatabase(GlobalConfig.getContext())
            database.transactionExecutor.execute {
                init(database)
            }
        }

    }
}