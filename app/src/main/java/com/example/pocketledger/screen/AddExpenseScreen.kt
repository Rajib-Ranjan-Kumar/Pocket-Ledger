package com.example.pocketledger.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pocketledger.model.Expense
import com.example.pocketledger.util.CategoryCard
import com.example.pocketledger.viewmodel.ExpenseViewModel

//import com.gana.pocketledger.data.Category
//import com.gana.pocketledger.data.Expense
//import com.gana.pocketledger.viewmodel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(viewModel: ExpenseViewModel) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Food") }
    val categories by viewModel.categories.collectAsState()

    var showSuccessMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Add New Expense",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Amount Input
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            placeholder = { Text("0.00") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Text("$") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description Input
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            placeholder = { Text("What did you spend on?") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Category",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Category Selection Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    isSelected = selectedCategory == category.name,
                    onClick = { selectedCategory = category.name }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Add Button
        Button(
            onClick = {
                if (amount.isNotBlank() && description.isNotBlank()) {
                    val expense = Expense(
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        description = description,
                        category = selectedCategory
                    )
                    viewModel.addExpense(expense)

                    // Reset form
                    amount = ""
                    description = ""
                    selectedCategory = "Food"
                    showSuccessMessage = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            enabled = amount.isNotBlank() && description.isNotBlank()
        ) {
            Text(
                text = "Add Expense",
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }

    // Success Message
    if (showSuccessMessage) {
        LaunchedEffect(showSuccessMessage) {
            kotlinx.coroutines.delay(2000)
            showSuccessMessage = false
        }

        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = { showSuccessMessage = false }) {
                    Text("OK")
                }
            }
        ) {
            Text("Expense added successfully!")
        }
    }
}
