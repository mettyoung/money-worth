package com.mettyoung.habbit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class HabitsScreen() : Screen {

    @Composable
    override fun Content() {
        Column(modifier = Modifier.fillMaxSize()) {
            Text("This is a habit screen")
        }
    }
}
