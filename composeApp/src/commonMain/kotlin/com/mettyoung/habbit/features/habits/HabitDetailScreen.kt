package com.mettyoung.habbit.features.habits

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
import com.mettyoung.habbit.domain.Habit
import com.mettyoung.habbit.presentation.HabitsViewModel
import org.koin.compose.koinInject

object HabitDetailScreen : Screen {

    @Composable
    override fun Content() {
        Column {
            HabitDetailContent()
        }
    }

    @Composable
    private fun HabitDetailContent(viewModel: HabitsViewModel = koinInject()) {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = { TopBar() }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                var habitName by remember { mutableStateOf("") }
                var habitNumberOfTimes by remember { mutableStateOf("") }

                TextField(
                    value = habitName,
                    onValueChange = { habitName = it },
                    label = { Text("Habit Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = habitNumberOfTimes,
                    onValueChange = { habitNumberOfTimes = it },
                    label = { Text("Number of Times") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val numberOfTimes = habitNumberOfTimes.toIntOrNull() ?: 0
                        viewModel.addHabit(Habit(_id = null, name = habitName, totalCount = numberOfTimes))
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
                    text = "Create new Habit",
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

