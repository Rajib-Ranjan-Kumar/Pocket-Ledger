package com.example.pocketledger.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pocketledger.model.Category
import com.example.pocketledger.model.Expense
import com.example.pocketledger.model.Group
import com.example.pocketledger.model.SavingsGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE date >= :startDate AND date <= :endDate")
    fun getExpensesByDateRange(startDate: Long, endDate: Long): Flow<List<Expense>>

    @Insert
    suspend fun insertExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT category, SUM(amount) as total FROM expenses GROUP BY category")
    suspend fun getCategoryTotals(): List<CategoryTotal>
}

data class CategoryTotal(val category: String, val total: Double)

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Insert
    suspend fun insertCategory(category: Category)
}

@Dao
interface GroupDao {
    @Query("SELECT * FROM groups")
    fun getAllGroups(): Flow<List<Group>>

    @Insert
    suspend fun insertGroup(group: Group)
}

@Dao
interface SavingsGoalDao {
    @Query("SELECT * FROM savings_goals")
    fun getAllGoals(): Flow<List<SavingsGoal>>

    @Insert
    suspend fun insertGoal(goal: SavingsGoal)

    @Update
    suspend fun updateGoal(goal: SavingsGoal)
}