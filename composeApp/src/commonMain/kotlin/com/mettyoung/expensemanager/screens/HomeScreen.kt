package com.mettyoung.expensemanager.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mettyoung.expensemanager.domain.RequestState

class HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val items: RequestState<List<String>> = RequestState.Success((0..100).map { it.toString() })

        Scaffold(
            topBar = { CenterAlignedTopAppBar(title = { Text(text = "Transactions") }) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navigator.push(TransactionScreen()) },
                    shape = RoundedCornerShape(size = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add transaction"
                    )
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)
                    .padding(
                        top = padding.calculateTopPadding(),
                        bottom = padding.calculateBottomPadding()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items.DisplayResult(
                    onLoading = { LoadingScreen() },
                    onError = { ErrorScreen(message = it) },
                    onSuccess = {
                        if (it.isNotEmpty()) {
                            LazyColumn(modifier = Modifier.padding(horizontal = 24.dp)) {
                                items(
                                    items = it,
                                    key = { item -> item }
                                ) { item ->
                                    ExpenseView(item = item)
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun ExpenseView(item: String) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier.fillMaxSize()
                .padding(top = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center) {
                Text(text = item, fontSize = 16.sp, textAlign = TextAlign.Center)
            }

        }
    }
}

