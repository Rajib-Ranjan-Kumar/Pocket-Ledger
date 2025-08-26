package com.example.pocketledger.viewmodel

//import com.gana.pocketledger.data.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketledger.model.Category
import com.example.pocketledger.model.Expense
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExpenseViewModel : ViewModel() {
    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _totalSpent = MutableStateFlow(0.0)
    val totalSpent: StateFlow<Double> = _totalSpent.asStateFlow()

    private val _monthlyBudget = MutableStateFlow(1000.0)
    val monthlyBudget: StateFlow<Double> = _monthlyBudget.asStateFlow()

    private val _categories = MutableStateFlow(getDefaultCategories())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        // Simulate loading expenses
        val sampleExpenses = listOf(
            Expense(
                amount = 25.50,
                category = "Food",
                description = "Lunch at cafe",
                date = System.currentTimeMillis() - 86400000
            ),
            Expense(
                amount = 60.0,
                category = "Transport",
                description = "Gas",
                date = System.currentTimeMillis() - 172800000
            ),
            Expense(
                amount = 120.0,
                category = "Shopping",
                description = "Groceries",
                date = System.currentTimeMillis() - 259200000
            )
        )
        _expenses.value = sampleExpenses
        _totalSpent.value = sampleExpenses.sumOf { it.amount }
    }

    fun addExpense(expense: Expense) {
        viewModelScope.launch {
            val currentExpenses = _expenses.value.toMutableList()
            currentExpenses.add(0, expense)
            _expenses.value = currentExpenses
            _totalSpent.value = currentExpenses.sumOf { it.amount }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            val currentExpenses = _expenses.value.toMutableList()
            currentExpenses.remove(expense)
            _expenses.value = currentExpenses
            _totalSpent.value = currentExpenses.sumOf { it.amount }
        }
    }

    fun getCategoryTotals(): Map<String, Double> {
        return _expenses.value.groupBy { it.category }
            .mapValues { (_, expenses) -> expenses.sumOf { it.amount } }
    }

    private fun getDefaultCategories(): List<Category> {
        return listOf(
            Category(name = "Food", icon = "🍽️", color = "#FF5722"),
            Category(name = "Transport", icon = "🚗", color = "#2196F3"),
            Category(name = "Shopping", icon = "🛍️", color = "#9C27B0"),
            Category(name = "Bills", icon = "💡", color = "#FF9800"),
            Category(name = "Entertainment", icon = "🎬", color = "#4CAF50"),
            Category(name = "Health", icon = "⚕️", color = "#F44336"),
            Category(name = "Other", icon = "📝", color = "#607D8B")
        )
    }
}