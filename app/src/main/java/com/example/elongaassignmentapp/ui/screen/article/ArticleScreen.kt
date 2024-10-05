package com.example.elongaassignmentapp.ui.screen.article

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun ArticleScreen(
    navController: NavController,
    articleId: String,
) {
    val viewModel: ArticleViewModel = koinViewModel(parameters = { parametersOf(articleId) })

    ArticleScreenLayout(
        uiState = viewModel.uiState,
    )
}
