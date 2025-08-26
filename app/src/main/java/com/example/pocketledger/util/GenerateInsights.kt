package com.example.pocketledger.util

fun generateInsight(categoryTotals: Map<String, Double>, totalSpent: Double): String {
    if (categoryTotals.isEmpty()) return "Start tracking expenses to see insights!"

    val topCategory = categoryTotals.maxByOrNull { it.value }
    val topCategoryPercentage = (topCategory?.value ?: 0.0) / totalSpent * 100

    return when {
        topCategoryPercentage > 50 -> "You're spending ${String.format("%.1f", topCategoryPercentage)}% on ${topCategory?.key}. Consider setting a budget for this category."
        totalSpent > 800 -> "You're spending quite a bit this month. Try to cut back on non-essential expenses."
        categoryTotals["Food"]?.let { it > totalSpent * 0.4 } == true -> "Food expenses are high. Consider meal planning to save money."
        else -> "Your spending looks balanced across categories. Keep up the good work!"
    }
}