package com.example.pocketledger.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pocketledger.util.ExpenseItem
import com.example.pocketledger.viewmodel.ExpenseViewModel
//import com.gana.pocketledger.data.Expense
//import com.gana.pocketledger.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ExpenseViewModel
) {
   val expenses by viewModel.expenses.collectAsState()
   val totalSpent by viewModel.totalSpent.collectAsState()
   val monthlyBudget by viewModel.monthlyBudget.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Budget Overview Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Monthly Budget",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))

                val budgetPercentage = 0.0 //(totalSpent / monthlyBudget * 100).coerceAtMost(100.0)

                LinearProgressIndicator(
                    progress = (budgetPercentage / 100).toFloat(),
                    modifier = Modifier.fillMaxWidth(),
                    color = if (budgetPercentage > 80) Color.Red else MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$${String.format("%.2f", totalSpent)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "$${String.format("%.2f", monthlyBudget)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }

                Text(
                    text = "${String.format("%.1f", budgetPercentage)}% of budget used",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Recent Expenses",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expenses) { expense ->
                ExpenseItem(
                    expense = expense,
                    onDelete = { viewModel.deleteExpense(expense) }
                )
            }
        }
    }
}
