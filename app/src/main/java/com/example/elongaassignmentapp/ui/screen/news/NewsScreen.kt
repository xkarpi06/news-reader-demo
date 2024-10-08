package com.example.elongaassignmentapp.ui.screen.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.example.elongaassignmentapp.Route
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(
    navController: NavController,
) {
    val viewModel: NewsViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.onScreenLaunched()
    }

    NewsScreenLayout(
        uiState = viewModel.uiState,
        onArticleClick = { articleId -> navController.navigate(Route.Article(articleId)) },
        onTakeMeBack = { navController.navigate(Route.Login) { popUpTo(Route.News) { inclusive = true } } },
    )
}
