package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewsScreen(
    navController: NavController,
) {
    val viewModel: NewsViewModel = koinViewModel()

    NewsScreenLayout(
        uiState = viewModel.uiState,
    )
}
