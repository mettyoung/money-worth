package com.mettyoung.habitrabbit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mettyoung.habbit.domain.Habit
import com.mettyoung.habbit.presentation.HabitsViewModel
import com.mettyoung.habbit.screens.elements.ErrorMessage

import org.koin.compose.koinInject

object HomeTab : Tab {
    @Composable
    override fun Content() {
        Column {
            HabitTabContent()
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon
                )
            }
        }
}

@Composable
fun HabitTabContent(viewModel: HabitsViewModel = koinInject()) {
    val state = viewModel.state.collectAsState()

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
            FloatingActionButton(onClick = {}) {
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
    TopAppBar(title = { Text("Habbit") })
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
        Text(text = habit.name)
    }
}
