package com.example.newsreaderdemo.ui.screen.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsreaderdemo.ui.screen.article.component.Article
import com.example.newsreaderdemo.ui.component.Error
import com.example.newsreaderdemo.ui.preview.PreviewData
import com.example.newsreaderdemo.ui.screen.article.model.ArticleUIState
import com.example.newsreaderdemo.ui.theme.AppTheme

@Composable
fun ArticleScreenLayout(
    uiState: ArticleUIState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isRefreshing) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        when (uiState) {
            is ArticleUIState.Idle -> {}

            is ArticleUIState.Success -> {
                Article(uiState)
            }

            is ArticleUIState.Error -> {
                Error(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    AppTheme {
        ArticleScreenLayout(
            uiState = ArticleUIState.Idle(true),
        )
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    AppTheme {
        ArticleScreenLayout(
            uiState = ArticleUIState.Error(),
        )
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    AppTheme {
        ArticleScreenLayout(
            uiState = ArticleUIState.Success(PreviewData.News.article),
        )
    }
}
