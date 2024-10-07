package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.elongaassignmentapp.Route
import com.example.elongaassignmentapp.ui.screen.news.model.NewsUIEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    val viewModel: NewsViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.onScreenLaunched()
        viewModel.oneTimeEvent.collect { event ->
            when (event) {
                is NewsUIEvent.ShowSnackbar -> {
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

    NewsScreenLayout(
        uiState = viewModel.uiState,
        onArticleClick = { articleId -> navController.navigate(Route.Article(articleId)) },
        onTakeMeBack = { navController.navigate(Route.Login) { popUpTo(Route.News) { inclusive = true } } },
    )
}
