package com.example.pocketledger.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pocketledger.screen.HomeScreen
//import com.gana.pocketledger.ui.screens.*
//import com.gana.pocketledger.viewmodel.ExpenseViewModel
import com.google.android.libraries.intelligence.acceleration.Analytics

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object AddExpense : Screen("add_expense", "Add Expense", Icons.Default.Add)
    object Reports : Screen("reports", "Reports", Icons.Default.Analytics)
    object Groups : Screen("groups", "Groups", Icons.Default.Group)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PocketLedgerApp() {
    val navController = rememberNavController()
    //val expenseViewModel: ExpenseViewModel = viewModel()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listOf(
                    Screen.Home,
                    Screen.AddExpense,
                    Screen.Reports,
                    Screen.Groups,
                    Screen.Profile
                ).forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(
               // expenseViewModel
            ) }
//            composable(Screen.AddExpense.route) { AddExpenseScreen(expenseViewModel) }
//            composable(Screen.Reports.route) { ReportsScreen(expenseViewModel) }
//            composable(Screen.Groups.route) { GroupsScreen() }
//            composable(Screen.Profile.route) { ProfileScreen() }
        }
    }
}
