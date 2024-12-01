package com.mettyoung.expensemanager.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class TransactionScreen: Screen {

    @Composable
    override fun Content() {
        Text(text = "Transaction Screen")
    }
}
