package com.example.elongaassignmentapp.ui.screen.article

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.elongaassignmentapp.ui.screen.article.model.ArticleUIEvent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArticleScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    articleId: String,
) {
    val viewModel: ArticleViewModel = koinViewModel(parameters = { parametersOf(articleId) })

    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collect { event ->
            when (event) {
                is ArticleUIEvent.ShowSnackbar -> {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = "Try again",
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> { viewModel.onRefresh() }
                        SnackbarResult.Dismissed -> {}
                    }
                }
            }
        }
    }

    ArticleScreenLayout(
        uiState = viewModel.uiState,
    )
}
