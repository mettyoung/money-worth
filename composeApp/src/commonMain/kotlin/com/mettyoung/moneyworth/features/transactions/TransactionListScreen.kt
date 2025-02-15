package com.mettyoung.moneyworth.features.Transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kevinnzou.swipebox.SwipeBox
import com.kevinnzou.swipebox.SwipeDirection
import com.kevinnzou.swipebox.widget.SwipeIcon
import com.mettyoung.moneyworth.domain.Transaction
import com.mettyoung.moneyworth.features.transactions.TransactionDetailScreen
import com.mettyoung.moneyworth.presentation.TransactionsViewModel
import com.mettyoung.moneyworth.screens.elements.ErrorMessage
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

object TransactionListScreen : Screen {

    @Composable
    override fun Content() {
        Column {
            TransactionListScreenContent()
        }
    }

    @Composable
    fun TransactionListScreenContent(viewModel: TransactionsViewModel = koinInject()) {
        val state = viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { AppBar() },
            content = { padding ->
                Column(modifier = Modifier.padding(padding)) {
                    if (state.value.error != null)
                        ErrorMessage(state.value.error!!)
                    if (state.value.data.isNotEmpty())
                        TransactionListView(viewModel)
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { navigator.push(TransactionDetailScreen) }) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add Transaction"
                    )
                }
            }
        )
    }

    @Composable
    private fun AppBar() {
        TopAppBar(title = { Text("Your Transaction") })
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun TransactionListView(viewModel: TransactionsViewModel) {
        val state = rememberPullRefreshState(
            refreshing = viewModel.state.value.loading,
            onRefresh = { viewModel.getTransactions(true) }
        )
        Box(
            modifier = Modifier.pullRefresh(state = state)
        ) {
            SwipeBoxList(
                data = viewModel.state.value.data,
                onDelete = viewModel::deleteTransaction,
                onCheck = viewModel::checkTransaction
            )

            PullRefreshIndicator(
                refreshing = viewModel.state.value.loading,
                state = state,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun SwipeBoxList(
        data: List<Transaction> = listOf(),
        onDelete: (index: Int) -> Unit = {},
        onCheck: (transaction: Transaction) -> Unit = {}
    ) {
        val coroutineScope = rememberCoroutineScope()

        var currentSwipeState: SwipeableState<Int>? by remember {
            mutableStateOf(null)
        }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                /**
                 * we need to intercept the scroll event and check whether there is an open box
                 * if so ,then we need to swipe that box back and reset the state
                 */
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    if (currentSwipeState != null && currentSwipeState!!.currentValue != 0) {
                        coroutineScope.launch {
                            currentSwipeState!!.animateTo(0)
                            currentSwipeState = null
                        }
                    }
                    return Offset.Zero
                }
            }
        }

        val onSwipeStateChanged = { state: SwipeableState<Int> ->
            /**
             * if it is swiping back and it equals to the current state
             * it means that the current open box is closed, then we set the state to null
             */
            if (state.targetValue == 0 && currentSwipeState == state) {
                currentSwipeState = null
            }
            // if there is no opening box, we set it to this opening one
            else if (currentSwipeState == null) {
                currentSwipeState = state
            } else {
                // there already had one box opening, we need to swipe it back and then update the state to new one
                coroutineScope.launch {
                    currentSwipeState!!.animateTo(0)
                    currentSwipeState = state
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .nestedScroll(nestedScrollConnection)
        ) {
            itemsIndexed(data) { index, item ->
                Spacer(modifier = Modifier.height(8.dp))
                TransactionItemView(
                    item,
                    onDelete = {
                        onDelete(index)
                        currentSwipeState = null
                    },
                    onCheck = {
                        onCheck(it)
                        currentSwipeState = null
                    },
                    index = index
                ) { state, _, _ ->
                    // callback on parent when the state targetValue changes which means it is swiping to another state.
                    LaunchedEffect(state.targetValue) {
                        onSwipeStateChanged(state)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun TransactionItemView(
        transaction: Transaction,
        onCheck: (transaction: Transaction) -> Unit = {},
        onDelete: (index: Int) -> Unit = {},
        index: Int,
        onContent: @Composable BoxScope.(swipeableState: SwipeableState<Int>, startSwipeProgress: Float, endSwipeProgress: Float) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val coroutineScope = rememberCoroutineScope()
            SwipeBox(
                modifier = Modifier.fillMaxWidth(),
                swipeDirection = SwipeDirection.Both,
                startContentWidth = 60.dp,
                startContent = { swipeableState, endSwipeProgress ->
                    SwipeIcon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Check",
                        tint = Color.White,
                        background = Color(0xFFFFB133),
                        weight = 1f,
                        iconSize = 20.dp
                    ) {
                        coroutineScope.launch {
                            swipeableState.animateTo(0)
                            onCheck(transaction)
                        }
                    }
                },
                endContentWidth = 60.dp,
                endContent = { swipeableState, endSwipeProgress ->
                    SwipeIcon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        tint = Color.White,
                        background = Color(0xFFFA1E32),
                        weight = 1f,
                        iconSize = 20.dp
                    ) {
                        coroutineScope.launch {
                            swipeableState.animateTo(0)
                            onDelete(index)
                        }
                    }
                }
            ) { a, b, c ->
                onContent(a, b, c)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(Color(148, 184, 216)),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            text = transaction.name,
                            style = TextStyle(fontSize = 22.sp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${transaction.currentCount}/${transaction.totalCount}",
                            style = TextStyle(fontSize = 16.sp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

