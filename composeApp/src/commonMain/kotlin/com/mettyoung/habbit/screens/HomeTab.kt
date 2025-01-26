package com.mettyoung.habitrabbit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.mettyoung.habbit.domain.Habit
import com.mettyoung.habbit.presentation.HabitsViewModel

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HabitTabContent(viewModel: HabitsViewModel = koinInject()) {
    val state = rememberPullRefreshState(
        refreshing = viewModel.state.value.loading,
        onRefresh = { }
    )
    Box(
        modifier = Modifier.pullRefresh(state = state)
            .fillMaxSize()
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
