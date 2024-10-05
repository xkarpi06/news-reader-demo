package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewsScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel: NewsViewModel = koinViewModel()

    NewsScreenLayout(
        uiState = viewModel.uiState,
    )
}
