package com.mettyoung.habbit.features.habits

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mettyoung.habbit.domain.Habit
import com.mettyoung.habbit.presentation.HabitsViewModel
import com.mettyoung.habbit.screens.elements.ErrorMessage
import org.koin.compose.koinInject

object HabitsListScreen : Screen {

    @Composable
    override fun Content() {
        Column {
            HabitsListScreenContent()
        }
    }

    @Composable
    fun HabitsListScreenContent(viewModel: HabitsViewModel = koinInject()) {
        val state = viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = { AppBar() },
            content = { padding ->
                Column(modifier = Modifier.padding(padding)) {
                    if (state.value.error != null)
                        ErrorMessage(state.value.error!!)
                    if (state.value.data.isNotEmpty())
                        HabitsListView(viewModel)
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {navigator.push(HabitDetailScreen)}) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add Habit"
                    )
                }
            }
        )
    }

    @Composable
    private fun AppBar() {
        TopAppBar(title = { Text("Your Habits") })
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun HabitsListView(viewModel: HabitsViewModel) {
        val state = rememberPullRefreshState(
            refreshing = viewModel.state.value.loading,
            onRefresh = { viewModel.getHabits(true) }
        )
        Box(
            modifier = Modifier.pullRefresh(state = state)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.state.value.data) { habit ->
                    HabitItemView(habit = habit)
                }
            }
            PullRefreshIndicator(
                refreshing = viewModel.state.value.loading,
                state = state,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }

    @Composable
    fun HabitItemView(habit: Habit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = habit.name, style = TextStyle(fontSize = 22.sp))
        }
    }
}