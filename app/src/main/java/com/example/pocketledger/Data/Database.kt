package com.example.pocketledger.Data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.pocketledger.model.Category
import com.example.pocketledger.model.Expense
import com.example.pocketledger.model.Group
import com.example.pocketledger.model.SavingsGoal
import java.util.Date

@Database(
    entities = [Expense::class, Category::class, Group::class, SavingsGoal::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PocketLedgerDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
    abstract fun groupDao(): GroupDao
    abstract fun savingsGoalDao(): SavingsGoalDao

    companion object {
        @Volatile
        private var INSTANCE: PocketLedgerDatabase? = null

        fun getDatabase(context: android.content.Context): PocketLedgerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PocketLedgerDatabase::class.java,
                    "pocket_ledger_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}