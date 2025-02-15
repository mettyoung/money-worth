package com.mettyoung.moneyworth.features.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mettyoung.moneyworth.domain.Transaction
import com.mettyoung.moneyworth.presentation.TransactionsViewModel
import org.koin.compose.koinInject

object TransactionDetailScreen : Screen {

    @Composable
    override fun Content() {
        Column {
            TransactionDetailContent()
        }
    }

    @Composable
    private fun TransactionDetailContent(viewModel: TransactionsViewModel = koinInject()) {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = { TopBar() }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                var transactionName by remember { mutableStateOf("") }
                var transactionNumberOfTimes by remember { mutableStateOf("") }

                TextField(
                    value = transactionName,
                    onValueChange = { transactionName = it },
                    label = { Text("Transaction Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = transactionNumberOfTimes,
                    onValueChange = { transactionNumberOfTimes = it },
                    label = { Text("Number of Times") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val numberOfTimes = transactionNumberOfTimes.toIntOrNull() ?: 0
                        viewModel.addTransaction(
                            Transaction(
                                _id = null,
                                name = transactionName,
                                totalCount = numberOfTimes
                            )
                        )
                        navigator.pop()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Confirm")
                }
            }
        }
    }

    @Composable
    private fun TopBar() {
        val navigator = LocalNavigator.currentOrThrow

        return TopAppBar(
            title = {
                Text(
                    text = "Create new Transaction",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
            navigationIcon = {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            backgroundColor = MaterialTheme.colors.primary
        )
    }
}

